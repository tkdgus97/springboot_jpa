package com.shjeon.springpj.web.loggame.controller;

import com.shjeon.springpj.web.loggame.vo.unit.Human;
import com.shjeon.springpj.web.entity.CharacterInfo;
import com.shjeon.springpj.web.loggame.service.LogGameService;
import com.shjeon.springpj.web.user.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

@RequestMapping("/game")
@Controller
public class LogGameController {

    @Autowired
    LogGameService logGameService;

    @Transactional
    @GetMapping("/character")
    public ModelAndView selectCharacter(@AuthenticationPrincipal Account user, ModelAndView mav){
        if (user != null){
//            List<CharacterInfo> characterInfoList = logGameService.getCharactorInfo(user.getUser().getId());
            List<CharacterInfo> characterInfoList = user.getUser().getCharacterInfoList();
            int listSize = characterInfoList.size();
            if (listSize <3) {
                for (int i=listSize; i <3; i++) {
                    CharacterInfo unit = null;
                    characterInfoList.add(unit);
                }
            }
            mav.addObject("characterList", characterInfoList);
        }
        mav.setViewName("loggame/selectCharator");
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createPage(ModelAndView mav) {

        mav.setViewName("loggame/create");
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView create(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp, @AuthenticationPrincipal Account user, String name, String job) {
        logGameService.createUnit(req,resp,user, name, job);
        mav.setViewName("/index");
        return mav;
    }

    @GetMapping("/test")
    public @ResponseBody String test(){
        Human unit = Human.builder()
                .defend(40).build();

        unit.upGuard();
        return "test";
    }
}
