package de.cj.jchess.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class JpaChessPiece {

    @Enumerated(EnumType.STRING)
    private ChessPieceType pieceType;

    @Enumerated(EnumType.STRING)
    private ChessPieceColor pieceColor;

    @Id
    @GeneratedValue
    private int id;

}