package com.example.rounit.watchlist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rounit.watchlist.entity.Movie;
import com.example.rounit.watchlist.repository.MovieRepository;

@Service
public class DatabaseService {

	@Autowired
	MovieRepository movieRepo;
	
	@Autowired
	RatingService ratingService;
	
	public void create(Movie movie) {
		
		String rating =ratingService.getMovieRating(movie.getTitle());
		
		if( rating != null)
		{
			movie.setRating(Float.parseFloat(rating));
		}
		movieRepo.save(movie);

	}
	
	public List<Movie> getAllMovies() {
		
		return movieRepo.findAll();
		

	}
	
	public Movie getMovieById(Integer id)
	{
		return movieRepo.findById(id).get();
		
	}

	public void update(Movie movie, Integer id) {
		
		Movie tobeupdated= getMovieById(id);
		tobeupdated.setTitle(movie.getTitle());
		tobeupdated.setComment(movie.getComment());
		tobeupdated.setRating(movie.getRating());
		tobeupdated.setPriority(movie.getPriority());
		
		movieRepo.save(tobeupdated);
	}
}

