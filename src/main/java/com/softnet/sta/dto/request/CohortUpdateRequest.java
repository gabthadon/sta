package com.softnet.sta.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CohortUpdateRequest {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private Boolean isActive;
}
