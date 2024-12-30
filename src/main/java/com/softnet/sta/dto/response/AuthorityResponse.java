package com.softnet.sta.dto.response;


import com.softnet.sta.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityResponse {
    private UserRole authority;

}
