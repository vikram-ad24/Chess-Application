package com.chess.Pieces;

import com.chess.Utility.MoveRequest;

public class Knight extends Piece{
    public Knight(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(MoveRequest moveRequest, Piece[][] board) {

        int fromRow= moveRequest.getFromRow();
        int fromColumn= moveRequest.getFromColumn();
        int toRow= moveRequest.getToRow();
        int toColumn= moveRequest.getToColumn();

        Piece target=board[toRow][toColumn];

        int rowStep=(toRow>fromRow)? 2: -2;
        int colStep=(toColumn>fromColumn)? 2 : -2;

        if(toRow==fromRow+rowStep && (toColumn==fromColumn+1||toColumn==fromColumn-1)){
            return target==null || !target.color.equals(this.color);
        }
        if(toColumn==fromColumn+colStep && (toRow==fromRow+1 || toRow==fromRow-1)){
            return target==null || !target.color.equals(this.color);
        }
        return false;
    }
}
