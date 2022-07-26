package com.shjeon.springpj.web.board.controller;

import com.shjeon.springpj.web.board.service.BoardService;
import com.shjeon.springpj.entity.Board;
import com.shjeon.springpj.web.user.vo.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    private final int pageSize = 10;

    @GetMapping("/list")
    public ModelAndView pagenationBoardList(ModelAndView mav, @PageableDefault(page = 0, size = 3, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println(pageable.getPageNumber());

        int pageNum = pageable.getPageNumber() + 1;
        int startPage = (int) Math.ceil((double)pageNum / pageSize);
        int endPage = (int) (Math.ceil((double)pageNum / pageSize)*pageSize);



        Page<Board> boardList = boardService.boardListAll(pageable);

        mav.addObject("boardList", boardList);
        mav.addObject("startPage", startPage);
        mav.addObject("endPage", endPage);

        mav.setViewName("board/list");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addPage(ModelAndView mav) {
        mav.setViewName("board/add");
        return mav;
    }

    @Transactional
    @PostMapping("/add")
    public String boardAdd(Board board, ModelAndView mav, @AuthenticationPrincipal Account user) {
        boardService.boardAdd(board, user);
        return "redirect:/board/list";
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

    @GetMapping("/detail/{idx}")
    public ModelAndView boardDetail(@PathVariable("idx") int idx, ModelAndView mav, HttpServletRequest req,  @AuthenticationPrincipal Account user) {
        Board board = boardService.getBoard(idx);

        HttpSession session = req.getSession();
        session.setAttribute("boardIdx", board.getBoardId());

        mav.addObject("user", user.getUser().getId());
        mav.addObject("board", board);
        mav.setViewName("board/detail");
        return mav;
    }

    @GetMapping("/remove")
    public String boardRemove(HttpServletRequest req){
        HttpSession session = req.getSession();
        int boardIdx = (int) session.getAttribute("boardIdx");

        boardService.boardRemove(boardIdx);

        return "redirect:/board/list";
    }

    @GetMapping("/edit")
    public ModelAndView boardEditPage(HttpServletRequest req, ModelAndView mav){
        HttpSession session = req.getSession();
        int boardIdx = (int) session.getAttribute("boardIdx");

        session.invalidate();

        Board board = boardService.getBoard(boardIdx);

        mav.addObject("board", board);
        mav.setViewName("board/edit");
        return mav;
    }

    @PostMapping("/edit")
    public String boardUpdate(Board board) {
        boardService.boardUpdate(board);
        return "redirect:/board/list";
    }
}
