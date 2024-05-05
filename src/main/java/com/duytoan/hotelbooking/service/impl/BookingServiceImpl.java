package com.duytoan.hotelbooking.service.impl;

import com.duytoan.hotelbooking.common.enummeration.BookingStatus;
import com.duytoan.hotelbooking.common.enummeration.RoomStatus;
import com.duytoan.hotelbooking.model.dto.request.BookingRequest;
import com.duytoan.hotelbooking.model.dto.request.BookingUpdateRequest;
import com.duytoan.hotelbooking.model.dto.response.BookingResponse;
import com.duytoan.hotelbooking.model.entity.Booking;
import com.duytoan.hotelbooking.model.entity.BookingRoom;
import com.duytoan.hotelbooking.model.entity.Room;
import com.duytoan.hotelbooking.model.entity.User;
import com.duytoan.hotelbooking.repository.BookingRepository;
import com.duytoan.hotelbooking.service.BookingService;
import com.duytoan.hotelbooking.service.RoomService;
import com.duytoan.hotelbooking.service.UserService;
import com.duytoan.hotelbooking.validation.BusinessErrorCode;
import com.duytoan.hotelbooking.validation.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserService userService;

    private final RoomService roomService;

    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.roomService = roomService;
    }

    /**
     * Create booking by user info, room info
     * @param bookingRequest
     * @return
     */
    @Transactional
    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        log.info("Start create booking for userid: {}", bookingRequest.getUserId());
        //validate data
        User user = userService.getUserById(bookingRequest.getUserId())
                .orElseThrow(() -> BusinessException.createException(BusinessErrorCode.NOT_FOUND_USER));

        List<Room> roomsAvailable = validateRoomAvailable(bookingRequest.getRoomIds());

        //Create data
        BigDecimal totalAmount = roomsAvailable.stream()
                .map(Room::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Booking booking = Booking.builder()
                .user(user)
                .bookingStatus(BookingStatus.PENDING)
                .totalAmount(totalAmount)
                .build();

        List<BookingRoom> bookingRooms = roomsAvailable.stream()
                .map(r -> BookingRoom.builder()
                        .room(r)
                        .booking(booking)
                        .price(r.getPrice())
                        .status(BookingStatus.PENDING)
                        .checkInDate(bookingRequest.getCheckInDate())
                        .checkOutDate(bookingRequest.getCheckOutDate())
                        .build())
                .collect(Collectors.toList());

        booking.setBookingRooms(bookingRooms);
        Booking savedBooking = bookingRepository.save(booking);
        roomService.updateRoomStatus(bookingRequest.getRoomIds(), RoomStatus.BOOKED);
        log.info("End create booking for userid: {}", bookingRequest.getUserId());
        return savedBooking.toDto();
    }

    /**
     * Update booking, only update for booking have status pending, confirmed
     * @param bookingId
     * @param request
     * @return
     */
    @Override
    @Transactional
    public BookingResponse updateBooking(Long bookingId, BookingUpdateRequest request) {
        log.info("Start update booking id: {}", bookingId);
        //check booking is exits
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> BusinessException.createException(BusinessErrorCode.NOT_FOUND_BOOKING));

        //check status book allow update
        validateBookingStatus(booking);

        //check status of room
        List<BookingRoom> oldBookingRoom = booking.getBookingRooms();
        List<Long> oldRoomIds = oldBookingRoom.stream()
                .filter(b -> BookingStatus.getListStatusInProgress().contains(b.getStatus()))
                .map(b -> b.getRoom().getId()).collect(Collectors.toList());
        List<Long> newRoomIds = request.getRoomIds();
        List<Long> remainRoomIds = newRoomIds.stream()
                .filter(r -> !oldRoomIds.contains(r) )
                .collect(Collectors.toList());
        validateRoomAvailable(remainRoomIds);

        //create data
        oldBookingRoom.forEach(br -> br.setStatus(BookingStatus.DELETED));
        roomService.updateRoomStatus(oldRoomIds, RoomStatus.AVAILABLE);

        List<Room> newRooms = roomService.getRoomsAvailable(newRoomIds);
        BigDecimal totalAmount = newRooms.stream()
                .map(Room::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<BookingRoom> newBookingRooms = newRooms.stream()
                .map(r -> BookingRoom.builder()
                        .room(r)
                        .booking(booking)
                        .price(r.getPrice())
                        .status(BookingStatus.PENDING)
                        .checkInDate(request.getCheckInDate())
                        .checkOutDate(request.getCheckOutDate())
                        .build())
                .collect(Collectors.toList());

        oldBookingRoom.addAll(newBookingRooms);

        booking.setBookingRooms(oldBookingRoom);
        booking.setTotalAmount(totalAmount);

        Booking savedBooking = bookingRepository.save(booking);
        roomService.updateRoomStatus(newRoomIds, RoomStatus.BOOKED);
        log.info("End update booking id: {}", bookingId);
        return savedBooking.toDto();
    }

    private void validateBookingStatus(Booking booking) {
        if (!BookingStatus.getListStatusInProgress().contains(booking.getBookingStatus())) {
            throw BusinessException.createException(BusinessErrorCode.BOOKING_NOT_ALLOW_UPDATE);
        }
    }

    private List<Room> validateRoomAvailable(List<Long> roomIds) {
        List<Room> roomsAvailable = roomService.getRoomsAvailable(roomIds);
        if (roomsAvailable.size() != roomIds.size()) {
            throw BusinessException.createException(BusinessErrorCode.ROOM_INVALID);
        }
        return roomsAvailable;
    }


    /**
     * Get list bookings by user
     * @param userId
     * @return
     */
    @Override
    public List<BookingResponse> getBookingsByUserId(Long userId) {
        log.info("Start get booking detail of user id: {}", userId);
        User user = userService.getUserById(userId)
                .orElseThrow(() -> BusinessException.createException(BusinessErrorCode.NOT_FOUND_USER));

        List<Booking> bookings = bookingRepository.findAllByUser(user);
        log.info("End get booking detail of user id: {}", userId);
        return bookings.stream()
                .map(Booking::toDto)
                .collect(Collectors.toList());
    }


    /**
     * View booking detail
     * @param bookingId
     * @return
     */
    @Override
    public BookingResponse viewBooking(Long bookingId) {
        log.info("Start get booking detail book id: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> BusinessException.createException(BusinessErrorCode.NOT_FOUND_BOOKING));
        log.info("End get booking detail book id: {}", bookingId);
        return booking.toDto();
    }

    /**
     * Cancel booking of customer
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean cancelBooking(Long id) {
        log.info("Start cancel booking detail book id: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> BusinessException.createException(BusinessErrorCode.NOT_FOUND_BOOKING));

        if(!BookingStatus.getListStatusInProgress().contains(booking.getBookingStatus())){
            BusinessException.createException(BusinessErrorCode.BOOKING_NOT_ALLOW_CANCEL);
        }

        List<BookingRoom> bookingRooms = booking.getBookingRooms();
        bookingRooms.forEach(br -> br.setStatus(BookingStatus.CANCELLED));
        roomService.updateRoomStatus(bookingRooms.stream().map(b -> b.getRoom().getId()).collect(Collectors.toList()), RoomStatus.AVAILABLE);

        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        log.info("End cancel booking detail book id: {}", id);
        return true;
    }

}
