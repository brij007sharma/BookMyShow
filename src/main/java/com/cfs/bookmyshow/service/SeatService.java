package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.dto.SeatRequest;
import com.cfs.bookmyshow.entity.Screen;
import com.cfs.bookmyshow.entity.Seat;
import com.cfs.bookmyshow.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenService screenService;

    //add Seat;
    public Seat addSeat(SeatRequest request){
        Screen screen=screenService.getScreenById(request.getScreenId());
        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .seatType(request.getSeatType())
                .col(Integer.valueOf(request.getCol()))
                .row(request.getRow())
                .screen(screen)
                .build();
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByScreenId(Long screenId){
        return seatRepository.findByScreenId(screenId);
    }

    public Seat getSeatById(Long id){
        return seatRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Seat not found with id: "+id));
    }


}
