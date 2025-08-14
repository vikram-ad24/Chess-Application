package com.chess.Pieces;

import com.chess.Utility.MoveRequest;

public class Queen extends Piece{
    public Queen(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(MoveRequest moveRequest, Piece[][] board) {
        int fromRow= moveRequest.getFromRow();
        int fromColumn= moveRequest.getFromColumn();
        int toRow= moveRequest.getToRow();
        int toColumn= moveRequest.getToColumn();

        Piece target=board[toRow][toColumn];

        if(toRow!=fromRow && toColumn!=fromColumn && Math.abs(toRow - fromRow) != Math.abs(toColumn - fromColumn)){
            return false;
        }
        else if(Math.abs(toRow - fromRow) == Math.abs(toColumn - fromColumn)){
            if(!isPathClear(fromRow,fromColumn,toRow,toColumn, board)){
                return false;
            }
        }

        if(toColumn==fromColumn)
        {
            int step=(toRow>fromRow)? 1: -1;
            for(int i=fromRow+step; i!=toRow; i+=step){
                if(board[i][fromColumn]!=null){
                    return false;
                }
            }
        }
        if(toRow==toColumn)
        {
            int step=(toColumn>fromColumn)? 1: -1;
            for(int i=fromColumn+step; i!=toColumn; i+=step){
                if(board[fromRow][i]!=null){
                    return false;
                }
            }
        }


        return target==null || !target.color.equals(this.color);
    }
    private static boolean isPathClear(int fromRow,int fromCol,int toRow,int toCol ,Piece[][] board){
        int rowDir=(toRow>fromRow)? 1: -1;
        int colDir=(toCol>fromCol)? 1: -1;

        int row=fromRow+rowDir;
        int col=fromCol+colDir;

        while(row!=toRow && col!=toCol){
            if(board[row][col]!=null){
                return false;
            }
            row+=rowDir;
            col+=colDir;
        }
        return true;
    }
}
