package com.duytoan.hotelbooking.model.dto.response;

import com.duytoan.hotelbooking.model.dto.UserInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {

    private Long id;
    private UserInfo userInfo;
    private BookingDetailDto bookingDetail;

}
