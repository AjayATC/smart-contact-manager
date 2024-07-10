package com.smart.controller;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    //method for adding common data to response
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {

        String userName = principal.getName();
        System.out.println("USERNAME" + userName);

        //get user using username(email)

        User user = userRepository.getUserByUserName(userName);
        System.out.println("USER" + user);

        model.addAttribute("user", user);

    }

    //dashboard home
    @GetMapping("/index")
    public String dashboard(Model model, Principal principal) {

        model.addAttribute("title", "User Dashboard");

        return "normal/user_dashboard";
    }

    //open add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());

        return "normal/add_contact_form";
    }

    //processing add contact form
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact,
                                 @RequestParam("profileImage") MultipartFile file,
                                 Principal principal,
                                 HttpSession session) {

        try {

            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);

            //processing and uploading file
            if (file.isEmpty()) {
                System.out.println("The file is empty!");
            } else {
                //save file to folder and update name to contact
                contact.setImage(file.getOriginalFilename());

                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Image is uploaded");

            }

            contact.setUser(user);
            user.getContacts().add(contact);

            this.userRepository.save(user);

            System.out.println("DATA" + contact);

            System.out.println("Added to database");

            //success message

            session.setAttribute("message", new Message("Your contact is added!! Add more..", "success"));


        } catch (Exception e) {

            System.out.println("ERROR" + e.getMessage());
            e.printStackTrace();

            //error message

            session.setAttribute("message", new Message("Something went wrong!! Try again..", "danger"));

        }

        return "normal/add_contact_form";
    }

}

