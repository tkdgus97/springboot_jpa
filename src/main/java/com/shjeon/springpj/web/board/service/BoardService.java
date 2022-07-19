package com.shjeon.springpj.web.board.service;

import com.shjeon.springpj.web.board.repository.BoardRepository;
import com.shjeon.springpj.web.entity.Board;
import com.shjeon.springpj.web.user.vo.Account;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BoardService {

    @Value("${app.upload-path}")
    private String uploadPath;

    @Autowired
    BoardRepository boardRepository;

    public Page<Board> boardListAll(Pageable pageable){
        Page<Board> boardList= boardRepository.findAll(pageable);
        return boardList;
    }

    @Transactional
    public void boardAdd(Board board, @AuthenticationPrincipal Account user){
        board.setUser(user.getUser());
        boardRepository.save(board);
    }

    public String imgUpload(MultipartFile file){
        String orgFileName = file.getOriginalFilename();
        String extenstion = StringUtils.getFilenameExtension(orgFileName);
        String imgName = "img_"+ UUID.randomUUID()+"."+extenstion;

        try {
            file.transferTo(new File(uploadPath+imgName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/img/"+imgName;
    }
}
