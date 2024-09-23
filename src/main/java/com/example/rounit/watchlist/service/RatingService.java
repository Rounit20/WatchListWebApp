package com.example.rounit.watchlist.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class RatingService {
	
	String apiurl="http://www.omdbapi.com/?apikey=970b7f9c&t=";
	
	public String getMovieRating(String title)
	{
		try 
		{
			
			RestTemplate template = new RestTemplate();	
			//apiurl+title ko call
			
			ResponseEntity<ObjectNode> response = template.getForEntity(apiurl + title, ObjectNode.class);
			
			ObjectNode jsonObject = response.getBody();
			
			return jsonObject.path("imdbRating").asText();
		}
		catch(Exception e)
		{
			System.out.println("Either Movie Name not available or api is down" + e.getMessage());
			return null;
			
		}
	}

}
