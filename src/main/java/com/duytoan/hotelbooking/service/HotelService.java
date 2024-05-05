package com.duytoan.hotelbooking.service;

import com.duytoan.hotelbooking.model.dto.response.HotelResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {

    Page<HotelResponse> searchHotel(String keyword, Pageable pageable);

}
