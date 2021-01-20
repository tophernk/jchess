package de.cj.jchess;

import java.util.Map;

public interface ChessConfiguration {
    boolean isCheckWhite();

    void setCheckWhite(boolean checkWhite);

    boolean isCheckBlack();

    void setCheckBlack(boolean checkBlack);

    boolean isShortCastlesWhite();

    void setShortCastlesWhite(boolean shortCastlesWhite);

    boolean isShortCastlesBlack();

    void setShortCastlesBlack(boolean shortCastlesBlack);

    boolean isLongCastlesWhite();

    void setLongCastlesWhite(boolean longCastlesWhite);

    boolean isLongCastlesBlack();

    void setLongCastlesBlack(boolean longCastlesBlack);

    ChessPieceColor getTurnColor();

    void setTurnColor(ChessPieceColor turnColor);

    String getEnPassant();

    void setEnPassant(String enPassant);

    Map<String, ChessPiece> getPieces();
}
