package de.cj.jchess.service;

import de.cj.jchess.entity.*;
import de.cj.jchess.service.impl.ChessConfigurationServiceImpl;
import de.cj.jchess.service.impl.ChessConfigurationSupport;
import de.cj.jchess.service.impl.ChessMoveServiceImpl;
import de.cj.jchess.service.mapper.ChessConfigurationMapperImpl;
import de.cj.jchess.service.mapper.ChessPieceMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.cj.jchess.entity.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {ChessMoveServiceImpl.class, ChessConfigurationServiceImpl.class, ChessConfigurationSupport.class, ChessConfigurationMapperImpl.class, ChessPieceMapperImpl.class})
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
    void executeCheck() {
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
    void executeCheckMate() {
        // assert game end (no available moves for acting side)
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.H7)
                .availablePositions(Stream.of(ChessPosition.H6, ChessPosition.H5)
                        .collect(Collectors.toSet()))
                .build();
        ChessPiece blackPawn2 = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.G7)
                .availablePositions(Stream.of(ChessPosition.G6, ChessPosition.G5)
                        .collect(Collectors.toSet()))
                .build();
        ChessPiece blackKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.G8)
                .availablePositions(Stream.of(ChessPosition.E7, ChessPosition.F6, ChessPosition.H6)
                        .collect(Collectors.toSet()))
                .build();
        ChessPiece blackKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.H8)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.H6)
                .availablePositions(Stream.of(ChessPosition.F7, ChessPosition.F5, ChessPosition.G4)
                        .collect(Collectors.toSet()))
                .build();
        ChessConfiguration configuration = ChessConfiguration.builder()
                .whitePieces(Stream.of(whiteKnight)
                        .collect(Collectors.toSet()))
                .blackPieces(Stream.of(blackPawn, blackPawn2, blackKnight, blackKing)
                        .collect(Collectors.toSet()))
                .longCastlesBlack(false)
                .longCastlesWhite(false)
                .shortCastlesBlack(false)
                .shortCastlesWhite(false)
                .checkBlack(false)
                .checkWhite(false)
                .turnColor(ChessPieceColor.WHITE)
                .build();
        boolean moveExecuted = chessMoveService.executeMove(configuration, ChessPosition.H6, ChessPosition.F7);
        assertTrue(moveExecuted);
        assertThat(configuration).isCheckBlack();
        assertThat(blackKing).hasNoAvailablePositions();
        assertThat(blackPawn).hasNoAvailablePositions();
        assertThat(blackPawn2).hasNoAvailablePositions();
        assertThat(blackKnight).hasNoAvailablePositions();
    }

    @Test
    void shortCastle() {
        // TODO: 11.02.2021 assert short castle execution and short castle flag after execution
    }

    @Test
    void longCastle() {
        // TODO: 11.02.2021 assert long castle execution and long castle flag after execution
    }

    @Test
    void enPassant() {
        // TODO: 11.02.2021 assert en passant exection and flag after execution
    }

    @Test
    void executeBestMove() {
    }
}