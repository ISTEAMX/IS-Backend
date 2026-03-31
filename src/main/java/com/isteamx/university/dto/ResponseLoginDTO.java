package com.isteamx.university.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLoginDTO {
    private String token;
    UserData userData;
}

