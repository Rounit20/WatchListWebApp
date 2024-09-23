package com.example.rounit.watchlist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.rounit.watchlist.entity.Movie;
import com.example.rounit.watchlist.service.DatabaseService;

import jakarta.validation.Valid;


@RestController
public class MovieController 
{
	@Autowired
	DatabaseService databaseService;
	
	@GetMapping("/watchlistItemForm")
	public ModelAndView showWatchlistForm(@RequestParam(required = false) Integer id)
	{
		
		
		String viewName ="watchlistItemForm";
		
		Map<String , Object> model = new HashMap<>();
		
		if(id==null)
		{
			model.put("watchlistItem", new Movie());
		}
		else
		{
			model.put("watchlistItem",databaseService.getMovieById(id));
		}
		
		return new ModelAndView(viewName,model);
	}
	
	@PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchListForm(@Valid @ModelAttribute("watchlistItem") Movie movie, BindingResult bindingResult) {

	    // Check for validation errors
	    if (bindingResult.hasErrors()) {
	        return new ModelAndView("watchlistItemForm");
	    }

	    // Validate and process the movie object
	    try {
	        // Check if ID is null or invalid
	        Integer id = movie.getId();
	        if (id == null) {
	            databaseService.create(movie);
	        } else {
	            // Ensure that the movie with the given ID exists
	            if (databaseService.getMovieById(id) == null) {
	                bindingResult.rejectValue("id", "error.movie", "Invalid movie ID");
	                return new ModelAndView("watchlistItemForm");
	            }
	            databaseService.update(movie, id);
	        }
	    } catch (Exception e) {
	        // Handle exceptions and return an error view if necessary
	        bindingResult.reject("error.movie", "An error occurred while processing the movie");
	        return new ModelAndView("watchlistItemForm");
	    }

	    // Redirect to watchlist after successful submission
	    RedirectView rd = new RedirectView();
	    rd.setUrl("/watchlist");
	    return new ModelAndView(rd);
	}

	
	@GetMapping("/watchlist")
	public ModelAndView getWatchlist() {
		
		String viewName ="watchlist";
		Map<String , Object> model = new HashMap<>();	
		List<Movie> movieList = databaseService.getAllMovies();
		model.put("watchlistrows", movieList);
		model.put("noofmovies",movieList.size());
		return new ModelAndView(viewName,model);
		
	}

}
