package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.entity.Movie;
import com.cfs.bookmyshow.entity.Screen;
import com.cfs.bookmyshow.entity.Theater;
import com.cfs.bookmyshow.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheaterService theaterService;

    //addscreen
    public Screen addMovie(Screen screen){
        return screenRepository.save(screen);
    }

    public List<Screen> getAllScreen(){
        return screenRepository.findAll();
    }

    public Screen getScreenById(Long id){
        return screenRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Screen not found with id: "+id));
    }

    public List<Screen> getScreenByTheater(Long theaterId){
        return screenRepository.findByTheaterId(theaterId);
    }
}
