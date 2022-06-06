package com.shjeon.springpj.web.home.controller;

import com.shjeon.springpj.web.home.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;


}
