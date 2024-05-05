package com.duytoan.hotelbooking.service;

import com.duytoan.hotelbooking.model.dto.request.BookingRequest;
import com.duytoan.hotelbooking.model.dto.request.BookingUpdateRequest;
import com.duytoan.hotelbooking.model.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    /**
     * Create booking by user info, room info
     * @param bookingRequest
     * @return
     */
    BookingResponse createBooking(BookingRequest bookingRequest);

    /**
     * Update booking, only update for booking have status pending, confirmed
     * @param bookingId
     * @param bookingUpdateRequest
     * @return
     */

    BookingResponse updateBooking(Long bookingId, BookingUpdateRequest bookingUpdateRequest);

    /**
     * Get list bookings by user
     * @param userId
     * @return
     */
    List<BookingResponse> getBookingsByUserId(Long userId);

    /**
     * View booking detail
     * @param id
     * @return
     */
    BookingResponse viewBooking(Long id);

    /**
     * Cancel booking of customer
     * @param id
     * @return
     */
    boolean cancelBooking(Long id);

}
