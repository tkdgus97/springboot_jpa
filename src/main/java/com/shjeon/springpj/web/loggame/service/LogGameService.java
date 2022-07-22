package com.shjeon.springpj.web.loggame.service;

import com.shjeon.springpj.web.entity.CharacterInfo;
import com.shjeon.springpj.web.entity.TempCharactor;
import com.shjeon.springpj.web.entity.TempUser;
import com.shjeon.springpj.web.loggame.repository.LogGameRepository;
import com.shjeon.springpj.web.loggame.repository.TempUserLogGameRepository;
import com.shjeon.springpj.web.loggame.vo.unit.GenericUnit;
import com.shjeon.springpj.web.loggame.vo.unit.Human;
import com.shjeon.springpj.web.user.repository.TempUserRepository;
import com.shjeon.springpj.web.user.vo.Account;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Service
public class LogGameService {
    @Autowired
    LogGameRepository logGameRepository;

    @Autowired
    TempUserLogGameRepository tempUserLogGameRepository;

    @Autowired
    TempUserRepository tempUserRepository;

    @Transactional
    public void createUnit(HttpServletRequest req, HttpServletResponse resp, @AuthenticationPrincipal Account user, String name, String job){
        ModelMapper modelMapper = new ModelMapper();
        GenericUnit unit = null;
        TempCharactor charactor = new TempCharactor();
        if (job.equals("전사")){
            unit = new Human();
        } else if(job.equals("궁수")) {
            System.out.println("궁수");
        } else if(job.equals("마법사")) {
            System.out.println("마법사");
        }

        if (unit != null){
            unit.setName(name);
            unit.setJob("전사");
        }


        if (user == null) {
            TempUser tempUser = tempUserCreate(resp);
            charactor = modelMapper.map(unit, TempCharactor.class);
            System.out.println(charactor.getJob());
        }
        else {
            Cookie[] cookie = req.getCookies();
            for (Cookie cookie1: cookie) {
                System.out.println(cookie1.getName());
                System.out.println(cookie1.getValue());
            };
            System.out.println("회원");
        }
    }

    public TempUser tempUserCreate(HttpServletResponse resp){
        String tempId = UUID.randomUUID().toString();
        tempId = tempId.replace("-","").substring(0,20);

        TempUser tempUser = new TempUser();
        tempUser.setTempId(tempId);

        Cookie cookie = new Cookie("TEMPID", tempId);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(60*60*24);

        resp.addCookie(cookie);
        return tempUser;
    }

}
