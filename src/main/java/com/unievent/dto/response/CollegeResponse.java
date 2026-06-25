package com.unievent.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollegeResponse {
    private Long id;
    private String name;
    private String location;
    private String website;
    private String logoUrl;
    private Boolean isApproved;
}