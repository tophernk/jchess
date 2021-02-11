package de.cj.jchess.service;

import de.cj.jchess.entity.*;
import de.cj.jchess.service.impl.ChessConfigurationServiceImpl;
import de.cj.jchess.service.impl.ChessConfigurationSupport;
import de.cj.jchess.service.impl.ChessMoveServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.cj.jchess.entity.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {ChessMoveServiceImpl.class, ChessConfigurationServiceImpl.class, ChessConfigurationSupport.class})
class ChessMoveServiceTest {

    @Autowired
    private ChessMoveService chessMoveService;

    @Test
    void executeMove() {
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E2)
                .availablePositions(Stream.of(ChessPosition.E3, ChessPosition.E4)
                        .collect(Collectors.toSet()))
                .build();
        ChessConfiguration configuration = ChessConfiguration.builder()
                .whitePieces(Set.of(whitePawn))
                .blackPieces(Collections.emptySet())
                .longCastlesBlack(false)
                .longCastlesWhite(false)
                .shortCastlesBlack(false)
                .shortCastlesWhite(false)
                .checkBlack(false)
                .checkWhite(false)
                .turnColor(ChessPieceColor.WHITE)
                .build();
        boolean moveExecuted = chessMoveService.executeMove(configuration, ChessPosition.E2, ChessPosition.E4);
        assertTrue(moveExecuted);
        assertThat(configuration).hasOnlyWhitePieces(whitePawn)
                .hasNoBlackPieces()
                .isNotCheckBlack()
                .isNotCheckWhite()
                .isNotLongCastlesBlack()
                .isNotLongCastlesWhite()
                .isNotShortCastlesBlack()
                .isNotShortCastlesWhite()
                .hasTurnColor(ChessPieceColor.BLACK);
    }

    @Test
    void executeMoveWithTakes() {
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.D3)
                .availablePositions(Stream.of(ChessPosition.E2, ChessPosition.D2)
                        .collect(Collectors.toSet()))
                .build();
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E2)
                .availablePositions(Stream.of(ChessPosition.E3, ChessPosition.E4, ChessPosition.D3)
                        .collect(Collectors.toSet()))
                .build();
        ChessConfiguration configuration = ChessConfiguration.builder()
                .whitePieces(Stream.of(whitePawn)
                        .collect(Collectors.toSet()))
                .blackPieces(Stream.of(blackPawn)
                        .collect(Collectors.toSet()))
                .longCastlesBlack(false)
                .longCastlesWhite(false)
                .shortCastlesBlack(false)
                .shortCastlesWhite(false)
                .checkBlack(false)
                .checkWhite(false)
                .turnColor(ChessPieceColor.WHITE)
                .build();
        boolean moveExecuted = chessMoveService.executeMove(configuration, ChessPosition.E2, ChessPosition.D3);
        assertTrue(moveExecuted);
        assertThat(configuration).hasOnlyWhitePieces(whitePawn)
                .hasNoBlackPieces()
                .isNotCheckBlack()
                .isNotCheckWhite()
                .isNotLongCastlesBlack()
                .isNotLongCastlesWhite()
                .isNotShortCastlesBlack()
                .isNotShortCastlesWhite()
                .hasTurnColor(ChessPieceColor.BLACK);
        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPosition.D4);
    }

    @Test
    void executeMoveToCheck() {
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.D3)
                .availablePositions(Stream.of(ChessPosition.E2, ChessPosition.D2)
                        .collect(Collectors.toSet()))
                .build();
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(Stream.of(ChessPosition.D1, ChessPosition.D2, ChessPosition.F2, ChessPosition.F1)
                        .collect(Collectors.toSet()))
                .build();
        ChessConfiguration configuration = ChessConfiguration.builder()
                .whitePieces(Stream.of(whiteKing)
                        .collect(Collectors.toSet()))
                .blackPieces(Stream.of(blackPawn)
                        .collect(Collectors.toSet()))
                .longCastlesBlack(false)
                .longCastlesWhite(false)
                .shortCastlesBlack(false)
                .shortCastlesWhite(false)
                .checkBlack(false)
                .checkWhite(false)
                .turnColor(ChessPieceColor.BLACK)
                .build();
        boolean moveExecuted = chessMoveService.executeMove(configuration, ChessPosition.D3, ChessPosition.D2);
        assertTrue(moveExecuted);
        assertThat(configuration).hasOnlyWhitePieces(whiteKing)
                .hasOnlyBlackPieces(blackPawn)
                .isNotCheckBlack()
                .isCheckWhite()
                .isNotLongCastlesBlack()
                .isNotLongCastlesWhite()
                .isNotShortCastlesBlack()
                .isNotShortCastlesWhite()
                .hasTurnColor(ChessPieceColor.WHITE);
        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPosition.D1);
    }

    @Test
    void executeBestMove() {
    }
}