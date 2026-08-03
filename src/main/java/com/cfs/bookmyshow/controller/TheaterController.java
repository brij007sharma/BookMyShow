package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.TheaterRequest;
import com.cfs.bookmyshow.entity.Theater;
import com.cfs.bookmyshow.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    //post add
    @PostMapping("/add")
    public ResponseEntity<Theater> addTheater(@RequestBody TheaterRequest theater){
        Theater savedTheater=theaterService.addTheater(theater);
        return new ResponseEntity<>(savedTheater, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Theater>> getAllTheaters(){
        return ResponseEntity.ok(theaterService.getAllTheaters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Long id){
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Theater>> getTheaterByCity(@PathVariable Long cityId){
        return ResponseEntity.ok(theaterService.getTheaterByCity(cityId));
    }

}
