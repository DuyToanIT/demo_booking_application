package com.duytoan.hotelbooking.service;

import com.duytoan.hotelbooking.common.enummeration.RoomStatus;
import com.duytoan.hotelbooking.model.entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRoomsAvailable(List<Long> roomIds);

    void updateRoomStatus(List<Long> roomIds, RoomStatus roomStatus);
}
