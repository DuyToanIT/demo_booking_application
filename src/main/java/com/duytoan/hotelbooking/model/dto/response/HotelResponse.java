package com.duytoan.hotelbooking.model.dto.response;

import com.duytoan.hotelbooking.model.dto.AddressInfo;
import com.duytoan.hotelbooking.model.dto.RoomInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {

    private String name;

    private String phone;

    private String status;
    private List<RoomInfo> rooms;

    private String location;

    private AddressInfo locationDetail;
}
