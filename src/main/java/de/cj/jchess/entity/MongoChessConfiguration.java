package de.cj.jchess.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
public class MongoChessConfiguration {

    @Id
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
    private String enPassant = "";

}
