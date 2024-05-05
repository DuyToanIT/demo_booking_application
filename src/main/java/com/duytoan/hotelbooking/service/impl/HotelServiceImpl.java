package com.duytoan.hotelbooking.service.impl;

import com.duytoan.hotelbooking.model.dto.response.HotelResponse;
import com.duytoan.hotelbooking.model.entity.Hotel;
import com.duytoan.hotelbooking.repository.HotelRepository;
import com.duytoan.hotelbooking.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**Function search hotel base on keyword like name, location
     *
     * @param keyword
     * @param pageable
     * @return
     */
    @Override
    public Page<HotelResponse> searchHotel(String keyword, Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findByNameContainsIgnoreCaseOrLocationContainsIgnoreCase(keyword, keyword, pageable);
        return new PageImpl<>(
                hotels.getContent().stream()
                        .map(h -> objectMapper.convertValue(h, HotelResponse.class))
                        .collect(Collectors.toList()),
                hotels.getPageable(),
                hotels.getTotalElements()
        );
    }
}
