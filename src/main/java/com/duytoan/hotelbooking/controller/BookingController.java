package com.duytoan.hotelbooking.controller;

import com.duytoan.hotelbooking.common.constant.ApiConstant;
import com.duytoan.hotelbooking.model.dto.request.BookingRequest;
import com.duytoan.hotelbooking.model.dto.request.BookingUpdateRequest;
import com.duytoan.hotelbooking.model.dto.response.BookingResponse;
import com.duytoan.hotelbooking.service.BookingService;
import com.duytoan.hotelbooking.validation.ErrorDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(ApiConstant.version + ApiConstant.BOOKING)
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Search bookings by user
    @Operation(summary = "Search booking by user id", description = "Return list booking of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetail.class))))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable Long userId) throws Exception {
        List<BookingResponse> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    // Create a new booking
    @Operation(summary = "Create booking", description = "Return result booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetail.class))))
    })
    @PostMapping("/create")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) throws Exception {
        BookingResponse bookingResult = bookingService.createBooking(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResult);
    }

    // Update an existing booking
    @Operation(summary = "Update booking", description = "Return result booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetail.class))))
    })
    @PutMapping("/edit/{bookingId}")
    public ResponseEntity<BookingResponse> editBooking(@PathVariable Long bookingId, @RequestBody BookingUpdateRequest bookingDetails) throws Exception {
        BookingResponse bookingResult = bookingService.updateBooking(bookingId, bookingDetails);
        return ResponseEntity.ok(bookingResult);
    }

    // View a specific booking
    @Operation(summary = "View booking", description = "Return result booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetail.class))))
    })
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> viewBooking(@PathVariable Long bookingId) {
        BookingResponse booking = bookingService.viewBooking(bookingId);
        return ResponseEntity.ok(booking);
    }

    // View a specific booking
    @Operation(summary = "Cancel booking", description = "Cancel booking by bookingid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetail.class))))
    })
    @GetMapping("/cancel/{bookingId}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable Long bookingId) {
        boolean result = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(result);
    }
}
