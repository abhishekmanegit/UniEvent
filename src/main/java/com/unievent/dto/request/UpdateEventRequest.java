package com.unievent.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateEventRequest {
    private String title;
    private String description;
    private String venue;
    private String imageUrl;
    private String category;
    private LocalDateTime eventDate;
    private LocalDateTime registrationDeadline;
    private Integer maxCapacity;
}