package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.GetInTouch;
import com.softnet.sta.dto.response.GetInTouchResponse;

public class GetInTouchMapper {

    public static GetInTouchResponse toGetInTouchResponse(GetInTouch getInTouch) {
        return new GetInTouchResponse(
                getInTouch.getId(),
                getInTouch.getFirstName(),
                getInTouch.getLastName(),
                getInTouch.getEmail(),
                getInTouch.getPhone(),
                getInTouch.getMessage()
        );
    }
}
