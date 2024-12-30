package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.Learner;
import com.softnet.sta.database.entity.Users;
import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.dto.response.SignUpResponse;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.mapper.UserProfileResponseMapper;
import com.softnet.sta.repository.LearnerRepository;
import com.softnet.sta.service.interfaces.LearnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearnerServiceImpl implements LearnerService {
    private final LearnerRepository learnerRepository;
    private final UserProfileResponseMapper userProfileResponseMapper;


}
