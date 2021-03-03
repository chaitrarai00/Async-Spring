package com.example.demo;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



/*
 * CommandLineRunner ensures that the application runs right after all beans are initialized
 */
@Component
public class ApplicationRunner implements CommandLineRunner{
	private static final Logger Log=LoggerFactory.getLogger(ApplicationRunner.class);
	private final MovieService movieservice;
	public ApplicationRunner(MovieService movieService) {
		this.movieservice=movieService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		// start the clock
		long start=System.currentTimeMillis();
		// start multiple lookups which are asynchronous
		CompletableFuture<MovieModel> page1=movieservice.lookforMovie("12cfb892-aac0-4c5b-94af-521852e46d6a");
		CompletableFuture<MovieModel> page2=movieservice.lookforMovie("1b67aa9a-2e4a-45af-ac98-64d6ad15b16c");
		CompletableFuture<MovieModel> page3=movieservice.lookforMovie("ff24da26-a969-4f0e-ba1e-a122ead6c6e3");
		// lets join all the thread for us to wait till 
		//all of the threads complete its execution
		// all of sends a collated result of all the mentioned threads
		CompletableFuture.allOf(page1,page2,page3).join();
		Log.info("Elapsed Time: "+(System.currentTimeMillis()-start));
		Log.info("-->"+page1.get());
		Log.info("-->"+page2.get());
		Log.info("-->"+page3.get());
	}

}
