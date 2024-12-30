package com.softnet.sta.controller;

import com.softnet.sta.dto.request.GetInTouchRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.GetInTouchResponse;
import com.softnet.sta.service.interfaces.GetInTouchService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("get-in-touch")
@Slf4j
public class GetInTouchController {
    private final GetInTouchService getInTouchService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<GetInTouchResponse>> getInTouch(@RequestBody GetInTouchRequest request) {
        GetInTouchResponse response = getInTouchService.getInTouch(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Successful", response));

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<GetInTouchResponse>> getInTouchById(@PathVariable Long id) {
        GetInTouchResponse response = getInTouchService.getInTouchById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Successful", response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<GetInTouchResponse>> updateInTouch(@PathVariable Long id, @RequestBody GetInTouchRequest request) {
        GetInTouchResponse response = getInTouchService.updateInTouch(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Updated Successfully", response));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteInTouch(@PathVariable Long id) {
        getInTouchService.deleteInTouch(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Get in touch deleted successfully", "Get in touch deleted successfully"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<GetInTouchResponse>>> getAllInTouch() {
        List<GetInTouchResponse> responseList = getInTouchService.getAllInTouch();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Successful", responseList));
    }
}
