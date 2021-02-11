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

@SpringJUnitConfig(classes = { ChessMoveServiceImpl.class, ChessConfigurationServiceImpl.class, ChessConfigurationSupport.class })
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
                .isNotShortCastlesWhite();
    }

    @Test
    void executeMoveWithTakes() {
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.D3)
                .availablePositions(Stream.of(ChessPosition.E2, ChessPosition.D2).collect(Collectors.toSet()))
                .build();
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E2)
                .availablePositions(Stream.of(ChessPosition.E3, ChessPosition.E4, ChessPosition.D3).collect(Collectors.toSet()))
                .build();
        ChessConfiguration configuration = ChessConfiguration.builder()
                .whitePieces(Stream.of(whitePawn).collect(Collectors.toSet()))
                .blackPieces(Stream.of(blackPawn).collect(Collectors.toSet()))
                .longCastlesBlack(false)
                .longCastlesWhite(false)
                .shortCastlesBlack(false)
                .shortCastlesWhite(false)
                .checkBlack(false)
                .checkWhite(false)
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
                .isNotShortCastlesWhite();
        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPosition.D4);
    }

    @Test
    void executeBestMove() {
    }
}