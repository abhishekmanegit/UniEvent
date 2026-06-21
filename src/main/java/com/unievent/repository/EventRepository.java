package com.unievent.repository;

import com.unievent.entity.Event;
import com.unievent.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatus(EventStatus status);
    List<Event> findByCollegeId(Long collegeId);
    List<Event> findByCollegeIdAndStatus(Long collegeId, EventStatus status);
}