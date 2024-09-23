package com.example.rounit.watchlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rounit.watchlist.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	

	
}
