package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.ShowRequest;
import com.cfs.bookmyshow.dto.TheaterRequest;
import com.cfs.bookmyshow.entity.Show;
import com.cfs.bookmyshow.entity.Theater;
import com.cfs.bookmyshow.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;

    @PostMapping("/add")
    public ResponseEntity<Show> addShow(@RequestBody ShowRequest request){
        Show show=showService.addShow(request);
        return new ResponseEntity<>(show, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShow());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id){
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowByMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(showService.getShowByMovie(movieId));
    }

    @GetMapping("/movie/{movieId}/date")
    public ResponseEntity<List<Show>>
    getShowByMovieAndDate(@PathVariable Long movieId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity.ok(showService.getShowByMovieAndDate(movieId,date));
    }

}
