package com.chess.Controller;

import com.chess.Pieces.Pawn;
import com.chess.Pieces.Piece;
import com.chess.Utility.Board;
import com.chess.Utility.MoveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173",
                allowCredentials = "true"
        )
@Controller
public class MovesController {

    @Autowired
    private Board board;

    @MessageMapping("/chess")
    @SendTo("/topic/board")
    public Piece[][] movePiece( MoveRequest moveRequest){

        if(moveRequest.getFromRow()==-1){
            return board.getSquare();
        }
        int fromRow=moveRequest.getFromRow();
        int fromColumn= moveRequest.getFromColumn();

        Piece[][] squares = board.getSquare();   // full board
        Piece piece = squares[fromRow][fromColumn];

//        if(piece instanceof Pawn) {
            if(piece.isValidMove(moveRequest,squares)) {
                return board.moveThePiece(moveRequest);
//            }
        }
        return board.getSquare();
    }


    @MessageMapping("/reset")
    @SendTo("/topic/board")
    public Piece[][] resetChessBoard(){
        Board newBoard= new Board();
        board.setSquare(newBoard.getSquare());
        return board.getSquare();
    }
}
