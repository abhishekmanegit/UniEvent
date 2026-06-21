package com.unievent.repository;

import com.unievent.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUserId(Long userId);
    List<Registration> findByEventId(Long eventId);
    Boolean existsByUserIdAndEventId(Long userId, Long eventId);
    Long countByEventId(Long eventId);
}