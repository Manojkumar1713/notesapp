package com.notesapp.notesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class NotesAppApplication {
	@RequestMapping("/")
	@ResponseBody
	String home(){
		return "working";
	}
	public static void main(String[] args) {
		SpringApplication.run(NotesAppApplication.class, args);
	}

}
