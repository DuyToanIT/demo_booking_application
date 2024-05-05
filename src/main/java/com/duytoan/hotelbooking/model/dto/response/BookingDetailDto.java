package com.duytoan.hotelbooking.model.dto.response;

import com.duytoan.hotelbooking.model.dto.BookingRoomInfo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class BookingDetailDto {

    private Long hotelId;

    private String hotelName;

    private String address;

    private List<BookingRoomInfo> roomInfos;

    private String status;

    private BigDecimal totalAmount;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
