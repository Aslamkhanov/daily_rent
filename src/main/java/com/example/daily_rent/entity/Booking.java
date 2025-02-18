package com.example.daily_rent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(columnDefinition = "timestamp", nullable = false)
    private LocalDate startDate;

    @Column(columnDefinition = "timestamp", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "booking_client_id_fk"))
    private Client client;

    @ManyToOne
    @JoinColumn(name = "advert_id", nullable = false,
            foreignKey = @ForeignKey(name = "booking_advert_id_fk"))
    private Advert advert;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
