package com.softnet.sta.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetInTouchRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String message;
}
