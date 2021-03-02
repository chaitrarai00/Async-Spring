package com.example.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.model.MovieModel;

@Service
public class MovieService {
	//use sl4j api for logging options
	private static final Logger Log=LoggerFactory.getLogger(MovieService.class);
	//use RestTemplate for getting a template for Http scene
	private final RestTemplate restTemplate;
	
	public MovieService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate=restTemplateBuilder.build();
		// get or build a sample rest template
	}
	//completable Future used when there are dependent completion actions
	public CompletableFuture lookforMovie(String movieId) throws InterruptedException{
		Log.info("Looking for movie with id ",movieId);
		String url= String.format("https://ghibliapi.herokuapp.com/films/%s", movieId);
		//consuming external ghibli api for getting varios movie info
		MovieModel results=restTemplate.getForObject(url, MovieModel.class);
		Thread.sleep(1000L);
		return CompletableFuture.completedFuture(results);
		//return CompletableFuture which has completed its actions already
	}
}
