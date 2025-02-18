package com.example.daily_rent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_date", columnDefinition = "timestamp", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "timestamp", nullable = false)
    private LocalDate endDate;

    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @Column(name = "advert_id", nullable = false)
    private Integer advertId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
