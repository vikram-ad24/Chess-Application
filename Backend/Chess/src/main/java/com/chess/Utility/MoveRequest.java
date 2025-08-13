package com.chess.Utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveRequest {

    private String color;
    private int fromRow;
    private int fromColumn;
    private int toRow;
    private int toColumn;

}
