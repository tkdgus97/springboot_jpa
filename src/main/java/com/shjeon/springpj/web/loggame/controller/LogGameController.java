package com.shjeon.springpj.web.loggame.controller;

import com.shjeon.springpj.vo.unit.Human;
import com.shjeon.springpj.web.entity.CharacterInfo;
import com.shjeon.springpj.vo.unit.GenericUnit;
import com.shjeon.springpj.web.loggame.service.LogGameService;
import com.shjeon.springpj.web.user.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequestMapping("/game")
@Controller
public class LogGameController {

    @Autowired
    LogGameService logGameService;

    @Transactional
    @GetMapping("/selectCharacter")
    public ModelAndView selectCharacter(@AuthenticationPrincipal Account user, ModelAndView mav){
        if (user != null){
//            List<CharacterInfo> characterInfoList = logGameService.getCharactorInfo(user.getUser().getId());
            List<CharacterInfo> characterInfoList = user.getUser().getCharacterInfoList();
            mav.addObject("characterList", characterInfoList);
        }
        mav.setViewName("loggame/selectCharator");
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
