package com.duytoan.hotelbooking.model.entity;

import com.duytoan.hotelbooking.common.constant.DatabaseConstants;
import com.duytoan.hotelbooking.common.enummeration.BookingStatus;
import com.duytoan.hotelbooking.model.dto.BookingRoomInfo;
import com.duytoan.hotelbooking.model.dto.response.BookingDetailDto;
import com.duytoan.hotelbooking.model.dto.response.BookingResponse;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = DatabaseConstants.TABLE_BOOKING)
public class Booking extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<BookingRoom> bookingRooms = new ArrayList<>();

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    public BookingResponse toDto(){
        Hotel hotel = getBookingRooms().get(0).getRoom().getHotel();
        List<BookingRoomInfo> roomInfoDtos = getBookingRooms().stream()
                .filter(r -> !Objects.equals(BookingStatus.DELETED, r.getStatus()))
                .map(BookingRoom::toDto)
                .collect(Collectors.toList());

        BookingDetailDto bookingDetailDto = BookingDetailDto.builder()
                .hotelId(hotel.getId())
                .hotelName(hotel.getName())
                .address(hotel.getLocation())
                .status(this.getBookingStatus().getLabel())
                .totalAmount(this.getTotalAmount())
                .roomInfos(roomInfoDtos)
                .createdDate(this.getCreatedDate())
                .updatedDate(this.getUpdatedDate())
                .build();

        return BookingResponse.builder()
                .id(this.getId())
                .userInfo(this.getUser().toDto())
                .bookingDetail(bookingDetailDto)
                .build();
    }

}
