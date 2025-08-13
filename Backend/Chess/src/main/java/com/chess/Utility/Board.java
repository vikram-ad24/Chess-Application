package com.chess.Utility;

import com.chess.Pieces.King;
import com.chess.Pieces.Pawn;
import com.chess.Pieces.Piece;
import com.chess.Pieces.Rook;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Board {
    private Piece[][] square=new Piece[8][8];

    public Board() {
        square = new Piece[8][8];

        // 1️⃣ Pawns
        for (int i = 0; i < 8; i++) {
            square[1][i] = new Pawn("white");
            square[6][i] = new Pawn("black");
        }

        // 2️⃣ Rooks
        square[0][0] = new Rook("white");
        square[0][7] = new Rook("white");
        square[7][0] = new Rook("black");
        square[7][7] = new Rook("black");
//
//        // 3️⃣ Knights
//        square[0][1] = new Knight("white");
//        square[0][6] = new Knight("white");
//        square[7][1] = new Knight("black");
//        square[7][6] = new Knight("black");
//
//        // 4️⃣ Bishops
//        square[0][2] = new Bishop("white");
//        square[0][5] = new Bishop("white");
//        square[7][2] = new Bishop("black");
//        square[7][5] = new Bishop("black");
//
//        // 5️⃣ Queens
//        square[0][3] = new Queen("white");
//        square[7][3] = new Queen("black");
//
        // 6️⃣ Kings
        square[0][4] = new King("white");
        square[7][4] = new King("black");
    }


    public Piece getPiece(int row,int col){
        Piece piece=square[row][col];
        return piece;
    }
    public Piece[][] moveThePiece(MoveRequest moveRequest){
        Piece piece=square[moveRequest.getFromRow()][moveRequest.getFromColumn()];
        square[moveRequest.getToRow()][moveRequest.getToColumn()]=piece;
        square[moveRequest.getFromRow()][moveRequest.getFromColumn()]=null; //Clearing the old position
        return square;
    }
}
