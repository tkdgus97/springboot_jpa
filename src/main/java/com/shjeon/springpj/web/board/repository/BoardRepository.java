package com.shjeon.springpj.web.board.repository;

import com.shjeon.springpj.web.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
