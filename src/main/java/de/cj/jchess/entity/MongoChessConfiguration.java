package de.cj.jchess.entity;

import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;

public class MongoChessConfiguration {

    @Id
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

    public boolean isCheckWhite() {
        return checkWhite;
    }

    public void setCheckWhite(boolean checkWhite) {
        this.checkWhite = checkWhite;
    }

    public boolean isCheckBlack() {
        return checkBlack;
    }

    public void setCheckBlack(boolean checkBlack) {
        this.checkBlack = checkBlack;
    }

    public boolean isShortCastlesWhite() {
        return shortCastlesWhite;
    }

    public void setShortCastlesWhite(boolean shortCastlesWhite) {
        this.shortCastlesWhite = shortCastlesWhite;
    }

    public boolean isShortCastlesBlack() {
        return shortCastlesBlack;
    }

    public void setShortCastlesBlack(boolean shortCastlesBlack) {
        this.shortCastlesBlack = shortCastlesBlack;
    }

    public boolean isLongCastlesWhite() {
        return longCastlesWhite;
    }

    public void setLongCastlesWhite(boolean longCastlesWhite) {
        this.longCastlesWhite = longCastlesWhite;
    }

    public boolean isLongCastlesBlack() {
        return longCastlesBlack;
    }

    public void setLongCastlesBlack(boolean longCastlesBlack) {
        this.longCastlesBlack = longCastlesBlack;
    }

    public ChessPieceColor getTurnColor() {
        return turnColor;
    }

    public void setTurnColor(ChessPieceColor turnColor) {
        this.turnColor = turnColor;
    }

    public String getEnPassant() {
        return enPassant;
    }

    public void setEnPassant(String enPassant) {
        this.enPassant = enPassant;
    }

    public Map<String, ChessPiece> getPieces() {
        return pieces;
    }

    public void setPieces(Map<String, ChessPiece> pieces) {
        this.pieces = pieces;
    }

    public String getId() {
        return id;
    }
}
