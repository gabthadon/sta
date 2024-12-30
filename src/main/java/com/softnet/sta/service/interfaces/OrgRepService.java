package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.dto.response.SignUpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrgRepService {
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ORGANIZATION_REP')")
    List<SignUpResponse> uploadGroupLearners(MultipartFile file);

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ORGANIZATION_REP')")
    List<LoginResponse> getUserByOrgRepId(String orgRepId);


}
