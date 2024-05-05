package com.duytoan.hotelbooking.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.duytoan.hotelbooking.common.enummeration.BookingStatus;
import com.duytoan.hotelbooking.model.dto.request.BookingRequest;
import com.duytoan.hotelbooking.model.dto.request.BookingUpdateRequest;
import com.duytoan.hotelbooking.model.dto.response.BookingResponse;
import com.duytoan.hotelbooking.model.entity.Booking;
import com.duytoan.hotelbooking.model.entity.BookingRoom;
import com.duytoan.hotelbooking.model.entity.Hotel;
import com.duytoan.hotelbooking.model.entity.Room;
import com.duytoan.hotelbooking.model.entity.User;
import com.duytoan.hotelbooking.repository.BookingRepository;
import com.duytoan.hotelbooking.service.RoomService;
import com.duytoan.hotelbooking.service.UserService;
import com.duytoan.hotelbooking.service.impl.BookingServiceImpl;
import com.duytoan.hotelbooking.validation.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Kích hoạt Mockito trong JUnit

public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private BookingServiceImpl bookingService; // Inject các mock vào dịch vụ

    @Test
    public void testCreateBooking_UserNotFound() {
        // Mock để trả về giá trị khi tìm kiếm User
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Xác định hành vi và kiểm tra ngoại lệ
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setUserId(userId);

        assertThrows(BusinessException.class, () -> {
            bookingService.createBooking(bookingRequest);
        }, "Expected BusinessException due to User not found");
    }

    @Test
    public void testUpdateBooking_BookingNotFound() {
        Long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        BookingUpdateRequest request = new BookingUpdateRequest();
        request.setRoomIds(Arrays.asList(1L, 2L));

        assertThrows(BusinessException.class, () -> {
            bookingService.updateBooking(bookingId, request);
        }, "Expected BusinessException due to Booking not found");
    }

    @Test
    public void testUpdateBooking_BookingNotAllowUpdate() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.COMPLETED); // Trạng thái không cho phép cập nhật

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        BookingUpdateRequest request = new BookingUpdateRequest();
        request.setRoomIds(Arrays.asList(1L, 2L));

        assertThrows(BusinessException.class, () -> {
            bookingService.updateBooking(bookingId, request);
        }, "Expected BusinessException due to Booking not allow update");
    }

    @Test
    public void testGetBookingsByUserId_UserNotFound() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            bookingService.getBookingsByUserId(userId);
        }, "Expected BusinessException due to User not found");
    }

    @Test
    public void testGetBookingsByUserId_Success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Booking booking = new Booking();
        BookingRoom bookingRoom = new BookingRoom();
        Room room = new Room();
        Hotel hotel = new Hotel();
        room.setHotel(hotel);
        bookingRoom.setRoom(room);
        bookingRoom.setStatus(BookingStatus.PENDING);
        booking.setBookingRooms(List.of(bookingRoom));
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setUser(new User());


        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByUser(user)).thenReturn(Arrays.asList(booking));
        List<BookingResponse> responses = bookingService.getBookingsByUserId(userId);

        assertNotNull(responses, "Expected a valid list of BookingResponse");
    }

    @Test
    public void testViewBooking_BookingNotFound() {
        Long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            bookingService.viewBooking(bookingId);
        }, "Expected BusinessException due to Booking not found");
    }

    @Test
    public void testViewBooking_Success() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        BookingRoom bookingRoom = new BookingRoom();
        Room room = new Room();
        Hotel hotel = new Hotel();
        room.setHotel(hotel);
        bookingRoom.setRoom(room);
        bookingRoom.setStatus(BookingStatus.PENDING);
        booking.setBookingRooms(List.of(bookingRoom));
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setUser(new User());

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        BookingResponse response = bookingService.viewBooking(bookingId);

        assertNotNull(response, "Expected a valid BookingResponse");
    }


}
