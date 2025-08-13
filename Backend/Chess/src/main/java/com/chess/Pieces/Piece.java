package com.chess.Pieces;


import com.chess.Utility.MoveRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Pawn.class, name = "pawn"),
//        @JsonSubTypes.Type(value = Rook.class, name = "rook"),
//        @JsonSubTypes.Type(value = Bishop.class, name = "bishop"),
//        @JsonSubTypes.Type(value = Knight.class, name = "knight"),
//        @JsonSubTypes.Type(value = Queen.class, name = "queen"),
//        @JsonSubTypes.Type(value = King.class, name = "king")
})

public abstract class Piece {
    String color;

    public Piece(String color)
    {
        this.color=color;
    }


    public String getColor() {   // ✅ Jackson will use this to serialize
        return color;
    }
    public abstract boolean isValidMove(MoveRequest moveRequest, Piece[][] board);
}
