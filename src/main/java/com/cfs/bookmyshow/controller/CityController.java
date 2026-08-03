package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.CityRequest;
import com.cfs.bookmyshow.dto.MovieRequest;
import com.cfs.bookmyshow.entity.City;
import com.cfs.bookmyshow.entity.Movie;
import com.cfs.bookmyshow.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping("/add")
    public ResponseEntity<City> addCity(@RequestBody CityRequest city) {
        City savedCity = cityService.addCity(city);
        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities(){
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id){
        return ResponseEntity.ok(cityService.getCityById(id));
    }

}
