package com.chess.Pieces;

import com.chess.Utility.MoveRequest;

public class Pawn extends Piece{

    public Pawn(String color){
        super(color);
    }

    @Override
    public boolean isValidMove(MoveRequest moveRequest,Piece[][] square) {
        int fromRow= moveRequest.getFromRow();
        int fromColumn= moveRequest.getFromColumn();
        int toRow= moveRequest.getToRow();
        int toColumn= moveRequest.getToColumn();

        Piece targetPiece=square[toRow][toColumn];
        int direction=color.equals("white")? 1 : -1;

        if(toColumn==fromColumn && toRow==fromRow+direction && square[toRow][toColumn]==null)
        {
            return true;
        }
        if(fromRow==1&&toColumn==fromColumn && toRow==fromRow+direction+1 && square[toRow][toColumn]==null)
        {
            return true;
        }
        if (Math.abs(toColumn - fromColumn) == 1 && toRow == fromRow + direction
                && targetPiece != null && !targetPiece.color.equals(this.color)) {
            return true;
        }
        return false;
    }
}
