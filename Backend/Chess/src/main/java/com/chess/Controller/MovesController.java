package com.chess.Controller;

import com.chess.Pieces.Piece;
import com.chess.Utility.Board;
import com.chess.Utility.MoveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

            if(piece.isValidMove(moveRequest,squares)) {
                return board.moveThePiece(moveRequest);
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

    @MessageMapping("/hints")
    @SendTo("/topic/hints")
    public List<int[]> getMoves(MoveRequest request){

        List<int[]> list=new ArrayList<>();
        Piece[][] square=board.getSquare();
        Piece piece=square[request.getFromRow()][request.getFromColumn()];

        for(int i=0; i<square.length; i++){
            for(int j=0; j<square[i].length; j++){
                request.setToRow(i);
                request.setToColumn(j);
                if(piece.isValidMove(request,board.getSquare())){
                    list.add(new int[]{i,j});
                }
            }
        }
        return list;
    }
}
