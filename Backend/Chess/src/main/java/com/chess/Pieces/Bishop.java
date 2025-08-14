package com.chess.Pieces;

import com.chess.Utility.MoveRequest;

public class Bishop extends Piece{
    public Bishop(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(MoveRequest moveRequest, Piece[][] board) {
        int fromRow= moveRequest.getFromRow();
        int fromColumn= moveRequest.getFromColumn();
        int toRow= moveRequest.getToRow();
        int toColumn= moveRequest.getToColumn();

        Piece target=board[toRow][toColumn];

        if (Math.abs(toRow - fromRow) != Math.abs(toColumn - fromColumn)) {
            return false;
        }

        int rowDirection = (toRow > fromRow) ? 1 : -1;
        int colDirection = (toColumn > fromColumn) ? 1 : -1;

        int row=fromRow+rowDirection;
        int col=fromColumn+colDirection;
        while(row!=toRow && col!=toColumn){
            if(board[row][col] != null){
                return false;
            }
            row+=rowDirection;
            col+=colDirection;
        }
        return target==null || !target.color.equals(this.color);
    }
}
