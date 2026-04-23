package com.isteamx.university.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
public class ErrorReportController {

    private final CloudWatchErrorReporter errorReporter;

    @PostMapping("/error")
    public ResponseEntity<Void> reportFrontendError(@RequestBody Map<String, String> errorPayload) {
        String message = errorPayload.getOrDefault("message", "Unknown frontend error");
        String stack = errorPayload.getOrDefault("stack", "");
        String source = errorPayload.getOrDefault("source", "frontend");
        String url = errorPayload.getOrDefault("url", "");

        log.warn("Frontend error from [{}] at {}: {}", source, url, message);
        errorReporter.reportError("Frontend:" + source,
                String.format("message=%s | url=%s | stack=%s", message, url, stack));

        return ResponseEntity.ok().build();
    }
}

