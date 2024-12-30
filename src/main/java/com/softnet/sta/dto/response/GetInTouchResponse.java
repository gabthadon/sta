package com.softnet.sta.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInTouchResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String message;
}
