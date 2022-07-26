package com.shjeon.springpj.web.board.service;

import com.shjeon.springpj.web.board.repository.BoardRepository;
import com.shjeon.springpj.entity.Board;
import com.shjeon.springpj.web.user.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.Optional;
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

    public Board getBoard(int idx) {
        return boardRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 게시물 없음"));
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

    @Transactional
    public void boardRemove(int idx) {
        boardRepository.deleteById(idx);
    }

    @Transactional
    public void boardUpdate(Board editBoard){
        Optional<Board> board = boardRepository.findById(editBoard.getBoardId());
        board.ifPresent((bo) -> {
            bo.setContent(editBoard.getContent());
            bo.setTitle(editBoard.getTitle());
            boardRepository.save(bo);
        });
    }
}
