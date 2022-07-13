package com.shjeon.springpj.web.home.controller;

import com.shjeon.springpj.web.entity.RoleType;
import com.shjeon.springpj.web.entity.User;
import com.shjeon.springpj.web.home.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Slf4j
@RestController
public class HomeController {

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/user/{id}")
    public User getUserInfo(@PathVariable int id){
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저 없음");
        });
        return user;
    }

    @GetMapping("/user/all")
    public List<User> listUser(){

        List<User> userList = userRepository.findAll();
        return userList;
    }

    @PostMapping("/member/join")
    public String memeberJoin(@RequestBody User user){
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입 완료";
    }

}
