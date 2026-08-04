package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.dto.MovieRequest;
import com.cfs.bookmyshow.entity.Movie;
import com.cfs.bookmyshow.entity.Theater;
import com.cfs.bookmyshow.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie addMovie(MovieRequest request){
        Movie movie = new Movie();

        movie.setTitle(request.getTitle());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        movie.setDurationMinutes(request.getDuration());
        movie.setReleaseDate(request.getReleaseDate());


        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id){
        return movieRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Movie not found with id: "+id));
    }

    public List<Movie> searchByTitle(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> searchByGenre(String genre){
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> searchByLanguage(String language){
        return movieRepository.findByLanguage(language);
    }

    public Movie updateMovie(Long id, MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        movie.setTitle(request.getTitle());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        movie.setDurationMinutes(request.getDuration());
        movie.setReleaseDate(request.getReleaseDate());

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id){
        Movie movie=movieRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Movie not foudn with id: "+id));

        movieRepository.delete(movie);
    }
}
