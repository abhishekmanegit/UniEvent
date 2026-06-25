package com.unievent.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCollegeRequest {

    @NotBlank
    private String name;

    private String location;
    private String website;
    private String logoUrl;
}