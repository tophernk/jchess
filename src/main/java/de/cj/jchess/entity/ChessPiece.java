package de.cj.jchess.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ChessPiece {

    private ChessPieceType pieceType;

    private ChessPieceColor pieceColor;

    private ChessPiecePosition position;

    private Set<ChessPiecePosition> availablePositions;

    private int id;

}