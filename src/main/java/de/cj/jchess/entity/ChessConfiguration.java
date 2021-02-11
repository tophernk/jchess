package de.cj.jchess.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ChessConfiguration {

    private String id;

    private Set<ChessPiece> whitePieces;
    private Set<ChessPiece> blackPieces;

    private boolean checkWhite;
    private boolean checkBlack;

    private boolean shortCastlesWhite;
    private boolean shortCastlesBlack;

    private boolean longCastlesWhite;
    private boolean longCastlesBlack;

    private ChessPieceColor turnColor;
    private ChessPosition enPassant;

}
