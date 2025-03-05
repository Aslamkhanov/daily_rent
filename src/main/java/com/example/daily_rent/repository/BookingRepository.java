package com.example.daily_rent.repository;

import com.example.daily_rent.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByAdvertId(Integer advertId);

    Page<Booking> findAllByClient_Email(String email, Pageable pageable);

    void deleteAllByClientId(Integer clientId);
}
