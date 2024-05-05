package com.duytoan.hotelbooking.service.impl;

import com.duytoan.hotelbooking.common.enummeration.RoomStatus;
import com.duytoan.hotelbooking.model.entity.Room;
import com.duytoan.hotelbooking.model.entity.User;
import com.duytoan.hotelbooking.repository.RoomRepository;
import com.duytoan.hotelbooking.repository.UserRepository;
import com.duytoan.hotelbooking.service.RoomService;
import com.duytoan.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Override
    public List<Room> getRoomsAvailable(List<Long> roomIds) {
        return roomRepository.getAllByIdInAndStatusEquals(roomIds, RoomStatus.AVAILABLE);
    }

    @Override
    public void updateRoomStatus(List<Long> roomIds, RoomStatus roomStatus) {
        List<Room> rooms = roomRepository.findAllById(roomIds);
        rooms = rooms.stream().peek(r -> r.setStatus(roomStatus)).collect(Collectors.toList());
        roomRepository.saveAll(rooms);
    }
}
