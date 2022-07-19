package com.shjeon.springpj.web.board.controller;

import com.shjeon.springpj.web.board.service.BoardService;
import com.shjeon.springpj.web.entity.Board;
import com.shjeon.springpj.web.user.vo.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/board")
public class boardController {

    @Autowired
    ServletContext context;

    @Autowired
    BoardService boardService;

    @GetMapping("/list")
    public ModelAndView boardList(ModelAndView mav, @PageableDefault(size = 3, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> boardList = boardService.boardListAll(pageable);
        System.out.println(boardList.getTotalPages());
        mav.addObject("boardList", boardList);
        mav.setViewName("board/list");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addPage(ModelAndView mav) {
        mav.setViewName("board/add");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView boardAdd(Board board, ModelAndView mav, @AuthenticationPrincipal Account user) {
        boardService.boardAdd(board, user);
        return mav;
    }

    @PostMapping(value = "/upload/image")
    @ResponseBody
    public Map<String, Object> ckeditorImgUpload(@RequestParam Map<String, Object> map,
                                                 MultipartHttpServletRequest req,
                                                 @AuthenticationPrincipal Principal Account) throws IOException {
        String imgName = "";

        MultipartFile img = req.getFile("upload");
        if (!img.isEmpty()) {
            imgName = boardService.imgUpload(img);
        }

        map.put("url", imgName);
        return map;
    }

}
