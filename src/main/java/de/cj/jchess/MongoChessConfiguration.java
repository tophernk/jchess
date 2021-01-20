package de.cj.jchess;

import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;

public class MongoChessConfiguration implements ChessConfiguration {

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

    @Override
    public boolean isCheckWhite() {
        return checkWhite;
    }

    @Override
    public void setCheckWhite(boolean checkWhite) {
        this.checkWhite = checkWhite;
    }

    @Override
    public boolean isCheckBlack() {
        return checkBlack;
    }

    @Override
    public void setCheckBlack(boolean checkBlack) {
        this.checkBlack = checkBlack;
    }

    @Override
    public boolean isShortCastlesWhite() {
        return shortCastlesWhite;
    }

    @Override
    public void setShortCastlesWhite(boolean shortCastlesWhite) {
        this.shortCastlesWhite = shortCastlesWhite;
    }

    @Override
    public boolean isShortCastlesBlack() {
        return shortCastlesBlack;
    }

    @Override
    public void setShortCastlesBlack(boolean shortCastlesBlack) {
        this.shortCastlesBlack = shortCastlesBlack;
    }

    @Override
    public boolean isLongCastlesWhite() {
        return longCastlesWhite;
    }

    @Override
    public void setLongCastlesWhite(boolean longCastlesWhite) {
        this.longCastlesWhite = longCastlesWhite;
    }

    @Override
    public boolean isLongCastlesBlack() {
        return longCastlesBlack;
    }

    @Override
    public void setLongCastlesBlack(boolean longCastlesBlack) {
        this.longCastlesBlack = longCastlesBlack;
    }

    @Override
    public ChessPieceColor getTurnColor() {
        return turnColor;
    }

    @Override
    public void setTurnColor(ChessPieceColor turnColor) {
        this.turnColor = turnColor;
    }

    @Override
    public String getEnPassant() {
        return enPassant;
    }

    @Override
    public void setEnPassant(String enPassant) {
        this.enPassant = enPassant;
    }

    @Override
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
