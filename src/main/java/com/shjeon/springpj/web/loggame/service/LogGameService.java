package com.shjeon.springpj.web.loggame.service;

import com.shjeon.springpj.web.entity.CharacterInfo;
import com.shjeon.springpj.web.loggame.repository.LogGameRepository;
import com.shjeon.springpj.web.user.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogGameService {
    @Autowired
    LogGameRepository logGameRepository;

}
