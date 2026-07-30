package com.cfs.bookmyshow.repository;

import com.cfs.bookmyshow.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater,Long> {

    List<Theater> findCityId(Long cityId);
}
