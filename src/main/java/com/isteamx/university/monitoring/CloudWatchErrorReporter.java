package com.isteamx.university.monitoring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.List;

@Slf4j
@Component
public class CloudWatchErrorReporter {

    @Value("${monitoring.cloudwatch.enabled:false}")
    private boolean enabled;

    @Value("${monitoring.cloudwatch.region:eu-central-1}")
    private String region;

    @Value("${monitoring.cloudwatch.log-group:/isteamx/backend}")
    private String logGroupName;

    @Value("${monitoring.cloudwatch.log-stream:app}")
    private String logStreamName;

    private CloudWatchLogsClient client;
    private String sequenceToken;

    @PostConstruct
    public void init() {
        if (!enabled) {
            log.info("CloudWatch error reporting is DISABLED (set CLOUDWATCH_ENABLED=true to enable)");
            return;
        }
        try {
            client = CloudWatchLogsClient.builder()
                    .region(Region.of(region))
                    .build();
            ensureLogGroupAndStream();
            log.info("CloudWatch error reporting is ENABLED → log group: {}", logGroupName);
        } catch (Exception e) {
            log.warn("Failed to initialize CloudWatch client, error reporting disabled: {}", e.getMessage());
            enabled = false;
        }
    }

    @PreDestroy
    public void shutdown() {
        if (client != null) {
            client.close();
        }
    }

    public synchronized void reportError(String context, Exception ex) {
        if (!enabled || client == null) {
            return;
        }
        try {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            String message = String.format(
                    "{\"level\":\"ERROR\",\"context\":\"%s\",\"exception\":\"%s\",\"message\":\"%s\",\"stackTrace\":\"%s\",\"timestamp\":\"%s\"}",
                    escapeJson(context),
                    escapeJson(ex.getClass().getSimpleName()),
                    escapeJson(ex.getMessage() != null ? ex.getMessage() : ""),
                    escapeJson(sw.toString().substring(0, Math.min(sw.toString().length(), 2000))),
                    Instant.now().toString()
            );

            InputLogEvent logEvent = InputLogEvent.builder()
                    .timestamp(Instant.now().toEpochMilli())
                    .message(message)
                    .build();

            PutLogEventsRequest.Builder requestBuilder = PutLogEventsRequest.builder()
                    .logGroupName(logGroupName)
                    .logStreamName(logStreamName)
                    .logEvents(List.of(logEvent));

            if (sequenceToken != null) {
                requestBuilder.sequenceToken(sequenceToken);
            }

            PutLogEventsResponse response = client.putLogEvents(requestBuilder.build());
            sequenceToken = response.nextSequenceToken();
        } catch (Exception e) {
            log.warn("Failed to send error to CloudWatch: {}", e.getMessage());
        }
    }

    public synchronized void reportError(String context, String message) {
        if (!enabled || client == null) {
            return;
        }
        try {
            String json = String.format(
                    "{\"level\":\"ERROR\",\"context\":\"%s\",\"message\":\"%s\",\"timestamp\":\"%s\"}",
                    escapeJson(context),
                    escapeJson(message),
                    Instant.now().toString()
            );

            InputLogEvent logEvent = InputLogEvent.builder()
                    .timestamp(Instant.now().toEpochMilli())
                    .message(json)
                    .build();

            PutLogEventsRequest.Builder requestBuilder = PutLogEventsRequest.builder()
                    .logGroupName(logGroupName)
                    .logStreamName(logStreamName)
                    .logEvents(List.of(logEvent));

            if (sequenceToken != null) {
                requestBuilder.sequenceToken(sequenceToken);
            }

            PutLogEventsResponse response = client.putLogEvents(requestBuilder.build());
            sequenceToken = response.nextSequenceToken();
        } catch (Exception e) {
            log.warn("Failed to send error to CloudWatch: {}", e.getMessage());
        }
    }

    private void ensureLogGroupAndStream() {
        try {
            client.createLogGroup(CreateLogGroupRequest.builder()
                    .logGroupName(logGroupName).build());
        } catch (ResourceAlreadyExistsException ignored) {
        }

        try {
            client.createLogStream(CreateLogStreamRequest.builder()
                    .logGroupName(logGroupName)
                    .logStreamName(logStreamName).build());
        } catch (ResourceAlreadyExistsException ignored) {
        }
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

