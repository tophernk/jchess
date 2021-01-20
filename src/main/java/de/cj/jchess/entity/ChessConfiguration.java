package de.cj.jchess.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ChessConfiguration {

    private String id;

    private Map<String, ChessPiece> pieces = new HashMap<>();

    private boolean checkWhite;
    private boolean checkBlack;

    private boolean shortCastlesWhite;
    private boolean shortCastlesBlack;

    private boolean longCastlesWhite;
    private boolean longCastlesBlack;

    private ChessPieceColor turnColor;
    private String enPassant = "";

}
