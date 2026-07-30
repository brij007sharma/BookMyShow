package com.cfs.bookmyshow.repository;

import com.cfs.bookmyshow.entity.Seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> findByScreenId(Long screenId);
}
