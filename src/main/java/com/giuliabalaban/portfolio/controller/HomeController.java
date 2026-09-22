package com.giuliabalaban.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

@GetMapping("/")
public String home(){
return "index";
}

@GetMapping("/projects/itineraria")
public String itineraria(){
return "projects/itineraria";
}

@GetMapping("/privacy")
public String privacy(){
return "privacy";
}

}
