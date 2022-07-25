package com.shjeon.springpj.web.home.controller;

import com.shjeon.springpj.entity.RoleType;
import com.shjeon.springpj.entity.User;
import com.shjeon.springpj.web.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RestController
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("hi", "hi thymeleaf");
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mav){
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView singUp(ModelAndView mav){
        mav.setViewName("signUp");
        return mav;
    }


    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void memeberJoin(User user, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        user.setRole(RoleType.USER);
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
        resp.sendRedirect("/");
    }

}
