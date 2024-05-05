package com.duytoan.hotelbooking.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class BookingRoomInfo {

    private Long id;

    private Long roomId;

    private BigDecimal priceBooking;

    private String status;
    private LocalDate checkInDate;

    private LocalDate checkOutDate;
}
