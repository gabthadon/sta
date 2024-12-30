package com.softnet.sta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "App Health Controller",
        description = "Exposes APIs to Check Application Health"
)

@RestController
public class AppHealthController {


    @Operation(
            summary = "Check Application Health REST API",
            description = "This REST API is used to Check Application Health"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Success")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'LEARNER', 'INSTRUCTOR')")
    @GetMapping("/health")
    public String health() {
        return "OK-Up and Running";
    }
}
