package com.shjeon.springpj.web.loggame.service;

import com.shjeon.springpj.entity.CharacterInfo;
import com.shjeon.springpj.entity.TempCharactor;
import com.shjeon.springpj.entity.TempUser;
import com.shjeon.springpj.entity.User;
import com.shjeon.springpj.web.loggame.repository.LogGameRepository;
import com.shjeon.springpj.web.loggame.repository.TempUserLogGameRepository;
import com.shjeon.springpj.web.loggame.vo.unit.GenericUnit;
import com.shjeon.springpj.web.loggame.vo.unit.Human;
import com.shjeon.springpj.web.user.repository.TempUserRepository;
import com.shjeon.springpj.web.user.repository.UserRepository;
import com.shjeon.springpj.web.user.vo.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

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
    UserRepository userRepository;

    @Autowired
    TempUserLogGameRepository tempUserLogGameRepository;

    @Autowired
    TempUserRepository tempUserRepository;

    @Transactional
    public void createUnit(HttpServletResponse resp, @AuthenticationPrincipal Account user, String name, String job){
        ModelMapper modelMapper = new ModelMapper();
        GenericUnit unit = null;

        if (job.equals("전사")){
            unit = new Human();
            unit.setJob("전사");
        } else if(job.equals("궁수")) {
            System.out.println("궁수");
        } else if(job.equals("마법사")) {
            System.out.println("마법사");
        }

        if (unit != null){
            unit.setName(name);
        }

        if (user == null) {
            TempUser tempUser = tempUserCreate(resp);
            TempCharactor charactor = modelMapper.map(unit, TempCharactor.class);

            tempUser.setTempCharactor(charactor);
            charactor.setTempUser(tempUser);

            tempUserRepository.save(tempUser);
        }
        else {
            CharacterInfo characterInfo = modelMapper.map(unit, CharacterInfo.class);

            User userInfo = userRepository.findById(user.getUser().getId()).orElse(null);

            userInfo.addCharacterInfo(characterInfo);
            logGameRepository.save(characterInfo);
        }
    }

    public List<CharacterInfo> getUserUnits(int idx){
        List<CharacterInfo> unitList = logGameRepository.findByUserId(idx);
        return unitList;
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

    public TempCharactor getTempCharator(String tempId){
        return tempUserRepository.getById(tempId).getTempCharactor();
    }

    @Transactional
    public void deleteUnit(@AuthenticationPrincipal Account user, int idx, HttpServletRequest req, HttpServletResponse resp){
        if (user != null){
            User userInfo = userRepository.findById(user.getUser().getId()).orElse(null);

            CharacterInfo characterInfo = logGameRepository.findById(idx).orElseThrow(() -> {
                return new IllegalArgumentException("해당 유닛 없음");
            });

            userInfo.removeCharacterInfo(characterInfo);
            logGameRepository.delete(characterInfo);

        } else {
            TempCharactor tempCharactor = tempUserLogGameRepository.findById(idx).orElse(null);
//            TempUser tempUser = tempUserRepository.findById(tempCharactor.getTempUser().getTempId()).orElse(null);

            String tempId = tempCharactor.getTempUser().getTempId();
            tempUserLogGameRepository.deleteById(idx);
            tempUserRepository.deleteById(tempId);

            Cookie removeCookie = null;

            Cookie[] cookies = req.getCookies();
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("TEMPID")) {
                    if (cookie.getValue().equals(tempId)){
                        removeCookie = cookie;
                        removeCookie.setMaxAge(0);
                    }
                }
            }
            if (removeCookie != null) {
                resp.addCookie(removeCookie);
            }
        }
    }

    public GenericUnit getUnitByIdx(int idx, HttpServletRequest req,  HttpServletResponse resp,@AuthenticationPrincipal Account user ) {
        ModelMapper modelMapper = new ModelMapper();
        GenericUnit unit = null;
        int id = 0;
        if (user == null) {
            TempUser tempUser;
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("TEMPID")){
                    tempUser = getTempUser(cookie.getValue());
                    if (tempUser.getTempCharactor().getIdx() == idx) {
                        TempCharactor tempCharactor = tempUser.getTempCharactor();
                        id = tempCharactor.getIdx();
                        unit = modelMapper.map(tempCharactor, GenericUnit.class);
                    }
                }
            }
        } else {
            User userInfo = user.getUser();
            List<CharacterInfo> characterInfoList = getUserUnits(userInfo.getId());
            for (CharacterInfo character: characterInfoList) {
                if (character.getIdx() == idx) {
                    id = character.getIdx();
                    unit = modelMapper.map(character, GenericUnit.class);
                }
            }
        }

        Cookie cookie = new Cookie("unit", String.valueOf(id));
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*30);

        resp.addCookie(cookie);

        return unit;
    }

    public TempUser getTempUser(String id) {
        TempUser tempUser = tempUserRepository.findById(id).orElse(null);
        return tempUser;
    }
}
