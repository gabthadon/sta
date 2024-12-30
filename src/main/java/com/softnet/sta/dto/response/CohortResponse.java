package com.softnet.sta.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CohortResponse {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private Boolean isActive;
}
