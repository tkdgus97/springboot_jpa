package com.shjeon.springpj.web.loggame.controller;

import com.shjeon.springpj.web.entity.TempCharactor;
import com.shjeon.springpj.web.loggame.vo.unit.Human;
import com.shjeon.springpj.web.entity.CharacterInfo;
import com.shjeon.springpj.web.loggame.service.LogGameService;
import com.shjeon.springpj.web.user.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/game")
@Controller
public class LogGameController {

    @Autowired
    LogGameService logGameService;

    private final int createLimitCount = 3;

    @Transactional
    @GetMapping("/character")
    public ModelAndView selectCharacter(@AuthenticationPrincipal Account user, ModelAndView mav,HttpServletRequest req ){
        if (user != null){
//            List<CharacterInfo> characterInfoList = logGameService.getCharactorInfo(user.getUser().getId());
            List<CharacterInfo> characterInfoList = logGameService.getUnits(user.getUser().getId());

            mav.addObject("characterList", characterInfoList);
        } else {
            Cookie[] cookies = req.getCookies();
            if (cookies != null){
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("TEMPID")){
                        TempCharactor charactor = logGameService.getTempCharator(cookie.getValue());
                        mav.addObject("tempCharacter", charactor);
                    }
                }
            } else {
                mav.addObject("tempCharacter", null);
            }
        }
        mav.setViewName("loggame/selectCharacter");
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createPage(ModelAndView mav) {
        mav.setViewName("loggame/create");
        return mav;
    }

    @PostMapping("/create")
    public String create(HttpServletResponse resp, @AuthenticationPrincipal Account user, String name, String job) {
        logGameService.createUnit(resp,user, name, job);
        return "redirect:/";
    }

    @PostMapping("/remove")
    public @ResponseBody void remove(@AuthenticationPrincipal Account user, int idx){
        logGameService.deleteUnit(user, idx);
    }

    @GetMapping("/test")
    public @ResponseBody String test(@AuthenticationPrincipal Account user) {
        System.out.println(user.getUser().getCharacterInfoList().size());
        return "test";
    }
}
