package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.SeatRequest;
import com.cfs.bookmyshow.dto.ShowRequest;
import com.cfs.bookmyshow.entity.Seat;
import com.cfs.bookmyshow.entity.Show;
import com.cfs.bookmyshow.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/add")
    public ResponseEntity<Seat> addSeat(@RequestBody SeatRequest request){
        Seat seat=seatService.addSeat(request);
        return new ResponseEntity<>(seat, HttpStatus.CREATED);
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Seat>> getSeatByScreen(@PathVariable Long screenId){
        return ResponseEntity.ok(seatService.getSeatsByScreenId(screenId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id){
        return ResponseEntity.ok(seatService.getSeatById(id));
    }





}
