package com.unievent.controller;

import com.unievent.dto.request.CreateCollegeRequest;
import com.unievent.dto.response.CollegeResponse;
import com.unievent.service.CollegeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colleges")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @PostMapping
    public ResponseEntity<CollegeResponse> createCollege(
            @Valid @RequestBody CreateCollegeRequest request) {
        return ResponseEntity.ok(collegeService.createCollege(request));
    }

    @GetMapping
    public ResponseEntity<List<CollegeResponse>> getAllColleges() {
        return ResponseEntity.ok(collegeService.getAllColleges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeResponse> getCollegeById(@PathVariable Long id) {
        return ResponseEntity.ok(collegeService.getCollegeById(id));
    }
}