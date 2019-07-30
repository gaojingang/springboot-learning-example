package com.kk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kk.socket.Server4HeatBeat;

@SpringBootApplication
public class App {

	
	public static void main(String[] args) {

		SpringApplication.run(App.class, args);

		

		Server4HeatBeat server = new Server4HeatBeat();
		server.startServices();
		
	}
	
}
