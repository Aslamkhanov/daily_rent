package com.example.daily_rent.repository;

import com.example.daily_rent.entity.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Integer> {
    Page<Advert> findByApartment_City(String city, Pageable pageable);
}
