package com.shjeon.springpj.web.loggame.service;

import com.shjeon.springpj.web.entity.CharacterInfo;
import com.shjeon.springpj.web.entity.TempCharactor;
import com.shjeon.springpj.web.entity.TempUser;
import com.shjeon.springpj.web.entity.User;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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

    public List<CharacterInfo> getUnits(int idx){
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
    public void deleteUnit(@AuthenticationPrincipal Account user, int idx){
        if (user != null){
            User userInfo = userRepository.findById(user.getUser().getId()).orElse(null);

            CharacterInfo characterInfo = logGameRepository.findById(idx).orElseThrow(() -> {
                return new IllegalArgumentException("해당 유닛 없음");
            });

            userInfo.removeCharacterInfo(characterInfo);
            logGameRepository.delete(characterInfo);

//            while (itrList.hasNext()){
//                if (itrList.next().getIdx() == idx){
//                    user.getUser().removeCharacterInfo(itrList.next());
//                }
//            }
        } else {

            tempUserLogGameRepository.deleteById(idx);
        }
    }

}
