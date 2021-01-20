package de.cj.jchess;

import javax.persistence.*;

@Entity
public class JpaChessPiece implements ChessPiece {

    @Enumerated(EnumType.STRING)
    private ChessPieceType pieceType;

    @Enumerated(EnumType.STRING)
    private ChessPieceColor pieceColor;

    @Id
    @GeneratedValue
    private int id;

    public JpaChessPiece() {
    }

    public JpaChessPiece(ChessPieceType type, ChessPieceColor color) {
        this.pieceType = type;
        this.pieceColor = color;
    }

    @Override
    public ChessPieceColor getPieceColor() {
        return pieceColor;
    }

    @Override
    public ChessPieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(ChessPieceType pieceType) {
        this.pieceType = pieceType;
    }

    public void setPieceColor(ChessPieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
