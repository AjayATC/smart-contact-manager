package com.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home-Smart Contact Manager");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-Smart Contact Manager");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register-Smart Contact Manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	
	//handler for registering user
//	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	@PostMapping(path = "/do_register")
	public String registerUser(@ModelAttribute("user") User user,@RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model){
		
		if(!agreement) {
			System.out.println("You have not agreed the terms and conditions.");
		}
		
		user.setRole("ROLE_USER");
		user.setEnabled(true);
				
		System.out.println("Agreement " + agreement);
		System.out.println("USER " + user);
		
		model.addAttribute("user",user);
		
		return "signup";
		
	}
}
