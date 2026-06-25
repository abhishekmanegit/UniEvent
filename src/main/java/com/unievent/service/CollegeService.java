package com.unievent.service;

import com.unievent.dto.request.CreateCollegeRequest;
import com.unievent.dto.response.CollegeResponse;
import com.unievent.entity.College;
import com.unievent.repository.CollegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollegeService {

    private final CollegeRepository collegeRepository;

    public CollegeResponse createCollege(CreateCollegeRequest request) {
        if (collegeRepository.existsByName(request.getName())) {
            throw new RuntimeException("College with this name already exists");
        }

        College college = College.builder()
                .name(request.getName())
                .location(request.getLocation())
                .website(request.getWebsite())
                .logoUrl(request.getLogoUrl())
                .isApproved(true)
                .build();

        College saved = collegeRepository.save(college);
        return mapToResponse(saved);
    }

    public List<CollegeResponse> getAllColleges() {
        return collegeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CollegeResponse getCollegeById(Long id) {
        College college = collegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found with id: " + id));
        return mapToResponse(college);
    }

    private CollegeResponse mapToResponse(College college) {
        return CollegeResponse.builder()
                .id(college.getId())
                .name(college.getName())
                .location(college.getLocation())
                .website(college.getWebsite())
                .logoUrl(college.getLogoUrl())
                .isApproved(college.getIsApproved())
                .build();
    }
}