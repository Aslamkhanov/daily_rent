package com.example.daily_rent.repository;

import com.example.daily_rent.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(
            nativeQuery = true,
            value = """
                    select count(id) > 0
                    from booking
                    where advert_id = :advertId
                    AND (
                        (:startDate < end_date AND :endDate > start_date)
                        OR (:startDate BETWEEN start_date AND end_date)
                        OR (:endDate BETWEEN start_date AND end_date)
                        OR (start_date BETWEEN :startDate AND :endDate)
                        OR (end_date BETWEEN :startDate AND :endDate)
                    )
                    """
    )
    boolean existsOverLappingBooking(@Param("advertId") Integer advertId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    Page<Booking> findAllByClient_Email(String email, Pageable pageable);

    void deleteAllByClientId(Integer clientId);
}
