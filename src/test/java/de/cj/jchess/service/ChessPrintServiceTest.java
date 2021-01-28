package de.cj.jchess.service;

import de.cj.jchess.entity.*;
import de.cj.jchess.service.impl.ChessConfigurationServiceImpl;
import de.cj.jchess.service.impl.ChessPrintServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.Set;

@SpringJUnitConfig(classes = {ChessPrintServiceImpl.class, ChessConfigurationServiceImpl.class})
class ChessPrintServiceTest {

    @Autowired
    private ChessPrintService chessPrintService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChessPrintServiceTest.class);

    @Test
    void printBoard() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteBishop = ChessPiece.builder()
                .pieceType(ChessPieceType.BISHOP)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.F1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.G1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackQueen = ChessPiece.builder()
                .pieceType(ChessPieceType.QUEEN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.D8)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.H1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.A7)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, whiteBishop, whiteKnight))
                .blackPieces(Set.of(blackQueen, blackRook, blackPawn))
                .build();

        String print = chessPrintService.printBoard(chessConfiguration);
        Assertions.assertThat(print)
                .contains("q", "p", "K", "B", "N", "r");

        LOGGER.info("\n" + print);
    }
}