package com.unievent.service;

import com.unievent.dto.request.CreateEventRequest;
import com.unievent.dto.request.UpdateEventRequest;
import com.unievent.dto.response.EventResponse;
import com.unievent.entity.College;
import com.unievent.entity.Event;
import com.unievent.entity.User;
import com.unievent.enums.EventStatus;
import com.unievent.repository.CollegeRepository;
import com.unievent.repository.EventRepository;
import com.unievent.repository.RegistrationRepository;
import com.unievent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final CollegeRepository collegeRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;

    public EventResponse createEvent(CreateEventRequest request, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        College college = creator.getCollege();
        if (college == null) {
            throw new RuntimeException("You are not associated with any college");
        }

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .venue(request.getVenue())
                .imageUrl(request.getImageUrl())
                .category(request.getCategory())
                .eventDate(request.getEventDate())
                .registrationDeadline(request.getRegistrationDeadline())
                .maxCapacity(request.getMaxCapacity())
                .status(EventStatus.DRAFT)
                .college(college)
                .createdBy(creator)
                .build();

        Event saved = eventRepository.save(event);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAllPublishedEvents() {
        return eventRepository.findByStatus(EventStatus.PUBLISHED)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getEventsByCollege(Long collegeId) {
        return eventRepository.findByCollegeId(collegeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        return mapToResponse(event);
    }

    public EventResponse updateEvent(Long id, UpdateEventRequest request, String editorEmail) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        User editor = userRepository.findByEmail(editorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!event.getCreatedBy().getId().equals(editor.getId())) {
            throw new RuntimeException("You are not authorized to edit this event");
        }

        if (request.getTitle() != null) event.setTitle(request.getTitle());
        if (request.getDescription() != null) event.setDescription(request.getDescription());
        if (request.getVenue() != null) event.setVenue(request.getVenue());
        if (request.getImageUrl() != null) event.setImageUrl(request.getImageUrl());
        if (request.getCategory() != null) event.setCategory(request.getCategory());
        if (request.getEventDate() != null) event.setEventDate(request.getEventDate());
        if (request.getRegistrationDeadline() != null) event.setRegistrationDeadline(request.getRegistrationDeadline());
        if (request.getMaxCapacity() != null) event.setMaxCapacity(request.getMaxCapacity());

        Event updated = eventRepository.save(event);
        return mapToResponse(updated);
    }

    public EventResponse publishEvent(Long id, String editorEmail) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        User editor = userRepository.findByEmail(editorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!event.getCreatedBy().getId().equals(editor.getId())) {
            throw new RuntimeException("You are not authorized to publish this event");
        }

        if (event.getStatus() != EventStatus.DRAFT) {
            throw new RuntimeException("Only DRAFT events can be published");
        }

        event.setStatus(EventStatus.PUBLISHED);
        Event updated = eventRepository.save(event);
        return mapToResponse(updated);
    }

    private EventResponse mapToResponse(Event event) {
        Long registeredCount = registrationRepository.countByEventId(event.getId());

        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .venue(event.getVenue())
                .imageUrl(event.getImageUrl())
                .category(event.getCategory())
                .eventDate(event.getEventDate())
                .registrationDeadline(event.getRegistrationDeadline())
                .maxCapacity(event.getMaxCapacity())
                .registeredCount(registeredCount)
                .status(event.getStatus().name())
                .collegeName(event.getCollege().getName())
                .collegeId(event.getCollege().getId())
                .createdByName(event.getCreatedBy().getName())
                .createdAt(event.getCreatedAt())
                .build();
    }
}