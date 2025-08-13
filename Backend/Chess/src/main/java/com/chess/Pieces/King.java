package com.chess.Pieces;

import com.chess.Utility.MoveRequest;

public class King extends Piece{
    public King(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(MoveRequest moveRequest, Piece[][] board) {
        int fromRow= moveRequest.getFromRow();
        int fromColumn= moveRequest.getFromColumn();
        int toRow= moveRequest.getToRow();
        int toColumn= moveRequest.getToColumn();

        Piece targetPiece = board[toRow][toColumn];

        int rowDif=Math.abs(toRow-fromRow);
        int colDif=Math.abs(toColumn-fromColumn);

        int direction=1;

        if(rowDif<=1 && colDif<=1)
        {
            return targetPiece == null || !targetPiece.color.equals(this.color);
        }

        return false;
    }
}
