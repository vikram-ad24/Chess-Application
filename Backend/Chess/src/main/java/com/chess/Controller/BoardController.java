package com.chess.Controller;

import com.chess.Pieces.Piece;
import com.chess.Utility.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private Board board;

    @GetMapping("/board")
    public Piece[][] getChessBoard(){
        return board.getSquare();
    }
}
