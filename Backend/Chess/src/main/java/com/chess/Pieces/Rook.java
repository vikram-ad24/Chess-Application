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

        // ✅ Ensure both positions are inside the board
        if (!inBounds(fromRow, fromColumn) || !inBounds(toRow, toColumn)) {
            return false;
        }

        Piece target=board[toRow][toColumn];

        // Rook can only move straight
        if (fromRow != toRow && fromColumn != toColumn) {
            return false;
        }
        int step;
        if(fromRow==toRow){  // Horizontal move
            step = (toColumn > fromColumn) ? 1 : -1;

            for(int i=fromColumn+step; i!=toColumn; i+=step){
                if(!inBounds(fromRow, i) || board[fromRow][i]!=null){
                    return false;
                }
            }
        }
        else{ // Vertical move
            step = (toRow > fromRow) ? 1 : -1;

            for(int i=fromRow+step; i!=toRow; i+=step){
                if(board[i][fromColumn]!=null){
                    return false;
                }
            }
        }
        return target==null|| !target.color.equals(this.color);
    }
    private boolean inBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
