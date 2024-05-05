package com.duytoan.hotelbooking.model.entity;

import com.duytoan.hotelbooking.common.constant.DatabaseConstants;
import com.duytoan.hotelbooking.common.enummeration.BookingStatus;
import com.duytoan.hotelbooking.model.dto.BookingRoomInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = DatabaseConstants.TABLE_BOOKING_ROOM)
public class BookingRoom extends BaseEntity{

    @JoinColumn(name = "booking_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Booking booking;


    @JoinColumn(name = "room_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Room room;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    public BookingRoomInfo toDto(){
        return BookingRoomInfo.builder()
                .id(this.getId())
                .roomId(this.getRoom().getId())
                .priceBooking(this.getPrice())
                .checkInDate(this.getCheckInDate())
                .checkOutDate(this.checkOutDate)
                .status(this.getStatus().getLabel())
                .build();
    }
}
