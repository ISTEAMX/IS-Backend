package com.isteamx.university.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
        private Long id;
        private String firstName;
        private String lastName;
        private String role;
}
