package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.GetInTouchRequest;
import com.softnet.sta.dto.response.GetInTouchResponse;

import java.util.List;

public interface GetInTouchService {
    GetInTouchResponse getInTouch(GetInTouchRequest request);

    GetInTouchResponse getInTouchById(Long id);

    GetInTouchResponse updateInTouch(Long id, GetInTouchRequest request);

    void deleteInTouch(Long id);

    List<GetInTouchResponse> getAllInTouch();
}
