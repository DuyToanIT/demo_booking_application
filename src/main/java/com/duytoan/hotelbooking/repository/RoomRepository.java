package com.duytoan.hotelbooking.repository;


import com.duytoan.hotelbooking.common.enummeration.RoomStatus;
import com.duytoan.hotelbooking.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> getAllByIdInAndStatusEquals(List<Long> roomIds, RoomStatus status);
}
