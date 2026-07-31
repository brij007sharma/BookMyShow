package com.cfs.bookmyshow.dto;

import com.cfs.bookmyshow.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

    private String seatNumber;
    private String row;
    private String col;
    private SeatType seatType;
    private Long screenId;
}
