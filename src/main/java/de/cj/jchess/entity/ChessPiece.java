package de.cj.jchess.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChessPiece {

    private ChessPieceType pieceType;

    private ChessPieceColor pieceColor;

    private int id;

}