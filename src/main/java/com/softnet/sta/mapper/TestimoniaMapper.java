package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.Testimonia;
import com.softnet.sta.dto.response.TestimonialResponse;

public class TestimoniaMapper {

        public static TestimonialResponse toTestimoniaResponse(Testimonia testimonia) {
            return new TestimonialResponse(
                                            testimonia.getId(),
                                            testimonia.getTitle(),
                                            testimonia.getBody()
            );
        }
}
