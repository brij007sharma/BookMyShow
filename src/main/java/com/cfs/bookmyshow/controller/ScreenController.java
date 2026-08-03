package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.ScreenRequest;
import com.cfs.bookmyshow.dto.SeatRequest;
import com.cfs.bookmyshow.entity.Screen;
import com.cfs.bookmyshow.entity.Seat;
import com.cfs.bookmyshow.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping("/add")
    public ResponseEntity<Screen> addScreen(@RequestBody ScreenRequest request){
        Screen screen=screenService.addScreen(request);
        return new ResponseEntity<>(screen, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreens(){
        return ResponseEntity.ok(screenService.getAllScreen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Long id){
        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Screen>> getScreenBYTheaterId(@PathVariable Long theaterId){
        return ResponseEntity.ok(screenService.getScreenByTheater(theaterId));
    }




}
