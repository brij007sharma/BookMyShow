package com.cfs.bookmyshow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

        private String title;
        private Integer duration;
        private String language;
        private LocalDate releaseDate;
        private String genre;

}
