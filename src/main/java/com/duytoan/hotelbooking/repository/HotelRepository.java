package com.duytoan.hotelbooking.repository;


import com.duytoan.hotelbooking.model.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Page<Hotel> findByNameContainsIgnoreCaseOrLocationContainsIgnoreCase(String keyword1, String keyword2, Pageable pageable);
}
