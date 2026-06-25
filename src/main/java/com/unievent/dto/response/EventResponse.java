package com.unievent.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private String venue;
    private String imageUrl;
    private String category;
    private LocalDateTime eventDate;
    private LocalDateTime registrationDeadline;
    private Integer maxCapacity;
    private Long registeredCount;
    private String status;
    private String collegeName;
    private Long collegeId;
    private String createdByName;
    private LocalDateTime createdAt;
}