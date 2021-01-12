package de.cj.jchess;

public class ChessPiece {

    private ChessPieceType pieceType;
    private ChessPieceColor pieceColor;

    public ChessPiece(ChessPieceType type, ChessPieceColor color) {
        this.pieceType = type;
        this.pieceColor = color;
    }

    public ChessPieceColor getPieceColor() {
        return pieceColor;
    }

    public ChessPieceType getPieceType() {
        return pieceType;
    }
}
