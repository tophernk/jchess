package de.cj.jchess.service;

import de.cj.jchess.entity.*;
import de.cj.jchess.service.impl.ChessMoveServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = ChessMoveServiceImpl.class)
class ChessMoveServiceTest {

    @Autowired
    private ChessMoveService chessMoveService;

    @Test
    void executeMove() {
        ChessConfiguration configuration = ChessConfiguration.builder()
                .whitePieces(Set.of(ChessPiece.builder()
                        .pieceType(ChessPieceType.PAWN)
                        .pieceColor(ChessPieceColor.WHITE)
                        .position(ChessPosition.E2)
                        .availablePositions(Set.of(ChessPosition.E3, ChessPosition.E4))
                        .build()))
                .build();
        boolean moveExecuted = chessMoveService.executeMove(configuration, ChessPosition.E2, ChessPosition.E4);
        assertTrue(moveExecuted);
    }

    @Test
    void executeBestMove() {
    }
}