package com.chess.Pieces;

import com.chess.Utility.MoveRequest;

public class Rook extends Piece{
    public Rook(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(MoveRequest moveRequest, Piece[][] board) {
        int fromRow= moveRequest.getFromRow();
        int fromColumn= moveRequest.getFromColumn();
        int toRow= moveRequest.getToRow();
        int toColumn= moveRequest.getToColumn();

        if (fromRow != toRow && fromColumn != toColumn) {
            return false;
        }
        if(toColumn-fromColumn==0 || toRow-fromRow==0){
            return true;
        }
        return false;
    }
}
