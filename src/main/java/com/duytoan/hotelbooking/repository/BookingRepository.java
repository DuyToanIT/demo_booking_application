package com.duytoan.hotelbooking.repository;


import com.duytoan.hotelbooking.model.entity.Booking;
import com.duytoan.hotelbooking.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByUser(User user);
}
