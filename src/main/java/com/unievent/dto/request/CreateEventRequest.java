package com.unievent.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateEventRequest {

    @NotBlank
    private String title;

    private String description;
    private String venue;
    private String imageUrl;
    private String category;

    @NotNull
    private LocalDateTime eventDate;

    private LocalDateTime registrationDeadline;
    private Integer maxCapacity;
}