package de.cj.jchess.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ChessPiece {

    private ChessPieceType pieceType;

    private ChessPieceColor pieceColor;

    private ChessPosition position;

    private Set<ChessPosition> availablePositions;

    private int id;

}