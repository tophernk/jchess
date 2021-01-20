package de.cj.jchess.service.impl;

import de.cj.jchess.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static de.cj.jchess.entity.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ChessConfigurationSupportTest {

    @Autowired
    private ChessConfigurationSupport chessConfigurationSupport;

    @Test
    void whitePawnForwardMove() {
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.A2).availablePositions(new HashSet<>()).build();
        ChessPiece blackKing = ChessPiece.builder().pieceType(ChessPieceType.KING).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.E8).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whitePawn)).blackPieces(Set.of(blackKing)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPiecePosition.A3, ChessPiecePosition.A4);

        whitePawn.setPosition(ChessPiecePosition.A3);
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPiecePosition.A4);
    }

    @Test
    void blackPawnForwardMove() {
        ChessPiece blackPawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.A7).availablePositions(new HashSet<>()).build();
        ChessPiece whiteKing = ChessPiece.builder().pieceType(ChessPieceType.KING).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.E1).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteKing)).blackPieces(Set.of(blackPawn)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPiecePosition.A6, ChessPiecePosition.A5);

        blackPawn.setPosition(ChessPiecePosition.A6);
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPiecePosition.A5);
    }

}