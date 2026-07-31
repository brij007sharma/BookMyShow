package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.entity.City;
import com.cfs.bookmyshow.repository.CityRepository;
import com.cfs.bookmyshow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private final UserRepository userRepository;
    private CityRepository cityRepository;

    public City addCity(City city){
        return cityRepository.save(city);
    }

    List<City> getAllCities(){
        return cityRepository.findAll();
    }

    public City getCityById(Long id){
        return cityRepository.findById(id)
                .orElseThrow(()->new RuntimeException("City not found with id: "+id));
    }

}
