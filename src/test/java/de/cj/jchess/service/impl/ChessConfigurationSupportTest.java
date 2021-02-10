package de.cj.jchess.service.impl;

import de.cj.jchess.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static de.cj.jchess.entity.Assertions.assertThat;

@SpringJUnitConfig(classes = ChessConfigurationSupport.class)
class ChessConfigurationSupportTest {

    @Autowired
    private ChessConfigurationSupport chessConfigurationSupport;

    @Test
    void whitePawnForwardMove() {
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.A2)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.E8)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whitePawn))
                .blackPieces(Set.of(blackKing))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPosition.A3, ChessPosition.A4);

        whitePawn.setPosition(ChessPosition.A3);
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPosition.A4);
    }

    @Test
    void blackPawnForwardMove() {
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.A7)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing))
                .blackPieces(Set.of(blackPawn))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPosition.A6, ChessPosition.A5);

        blackPawn.setPosition(ChessPosition.A6);
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPosition.A5);
    }

    @Test
    void whitePawnEnPassant() {
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.B5)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.C5)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whitePawn))
                .blackPieces(Set.of(blackPawn))
                .enPassant(ChessPosition.C6)
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPosition.B6, ChessPosition.C6);
    }

    @Test
    void blackPawnEnPassant() {
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.C4)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.D4)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whitePawn))
                .blackPieces(Set.of(blackPawn))
                .enPassant(ChessPosition.C3)
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPosition.C3, ChessPosition.D3);
    }

    @Test
    void whitePawnTakes() {
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.F3)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.G4)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whitePawn))
                .blackPieces(Set.of(blackPawn))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPosition.F4, ChessPosition.G4);
    }

    @Test
    void blackPawnTakes() {
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.F3)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.G4)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whitePawn))
                .blackPieces(Set.of(blackPawn))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPosition.F3, ChessPosition.G3);
    }

    @Test
    void knightMoves() {
        ChessPiece whiteKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D4)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKnight))
                .blackPieces(Collections.emptySet())
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKnight).hasOnlyAvailablePositions(ChessPosition.E6, ChessPosition.F5, ChessPosition.F3, ChessPosition.E2, ChessPosition.C2,
                ChessPosition.B3, ChessPosition.B5, ChessPosition.C6);
    }

    @Test
    void knightMoveWithinBoardBoundaries() {
        ChessPiece whiteKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D2)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKnight))
                .blackPieces(Collections.emptySet())
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKnight).hasOnlyAvailablePositions(ChessPosition.E4, ChessPosition.F3, ChessPosition.F1, ChessPosition.B1, ChessPosition.B3,
                ChessPosition.C4);
    }

    @Test
    void knightTakes() {
        ChessPiece whiteKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D2)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.F1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.B1)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKnight, whitePawn))
                .blackPieces(Set.of(blackKnight))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKnight).hasOnlyAvailablePositions(ChessPosition.E4, ChessPosition.F3, ChessPosition.B1, ChessPosition.B3, ChessPosition.C4);
    }

    @Test
    void bishopMoves() {
        ChessPiece whiteBishop = ChessPiece.builder()
                .pieceType(ChessPieceType.BISHOP)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D4)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteBishop))
                .blackPieces(Collections.emptySet())
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteBishop).hasOnlyAvailablePositions(ChessPosition.A1, ChessPosition.B2, ChessPosition.C3, ChessPosition.E5, ChessPosition.F6,
                ChessPosition.G7, ChessPosition.H8, ChessPosition.A7, ChessPosition.B6, ChessPosition.C5, ChessPosition.E3, ChessPosition.F2, ChessPosition.G1);
    }

    @Test
    void bishopTakes() {
        ChessPiece whiteBishop = ChessPiece.builder()
                .pieceType(ChessPieceType.BISHOP)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D4)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.B2)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.F6)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteBishop, whitePawn))
                .blackPieces(Set.of(blackKnight))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteBishop).hasOnlyAvailablePositions(ChessPosition.C3, ChessPosition.E5, ChessPosition.F6, ChessPosition.A7, ChessPosition.B6,
                ChessPosition.C5, ChessPosition.E3, ChessPosition.F2, ChessPosition.G1);
    }

    @Test
    void rookMoves() {
        ChessPiece whiteRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E4)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteRook))
                .blackPieces(Collections.emptySet())
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteRook).hasOnlyAvailablePositions(ChessPosition.E1, ChessPosition.E2, ChessPosition.E3, ChessPosition.E5, ChessPosition.E6,
                ChessPosition.E7, ChessPosition.E8, ChessPosition.A4, ChessPosition.B4, ChessPosition.C4, ChessPosition.D4, ChessPosition.F4, ChessPosition.G4,
                ChessPosition.H4);
    }

    @Test
    void rookTakes() {
        ChessPiece whiteRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E4)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.C4)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.E6)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteRook, whitePawn))
                .blackPieces(Set.of(blackKnight))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteRook).hasOnlyAvailablePositions(ChessPosition.E1, ChessPosition.E2, ChessPosition.E3, ChessPosition.E5, ChessPosition.E6,
                ChessPosition.D4, ChessPosition.F4, ChessPosition.G4, ChessPosition.H4);
    }

    @Test
    void queenMoves() {
        ChessPiece whiteQueen = ChessPiece.builder()
                .pieceType(ChessPieceType.QUEEN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E4)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteQueen))
                .blackPieces(Collections.emptySet())
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteQueen).hasOnlyAvailablePositions(ChessPosition.B1, ChessPosition.C2, ChessPosition.D3, ChessPosition.F5, ChessPosition.G6,
                ChessPosition.H7, ChessPosition.A8, ChessPosition.B7, ChessPosition.C6, ChessPosition.D5, ChessPosition.F3, ChessPosition.G2, ChessPosition.H1,
                ChessPosition.A4, ChessPosition.B4, ChessPosition.C4, ChessPosition.D4, ChessPosition.F4, ChessPosition.G4, ChessPosition.H4, ChessPosition.E1,
                ChessPosition.E2, ChessPosition.E3, ChessPosition.E5, ChessPosition.E6, ChessPosition.E7, ChessPosition.E8);
    }

    @Test
    void queenTakes() {
        ChessPiece whiteQueen = ChessPiece.builder()
                .pieceType(ChessPieceType.QUEEN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E4)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D5)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.E6)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteQueen, whitePawn))
                .blackPieces(Set.of(blackKnight))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteQueen).hasOnlyAvailablePositions(ChessPosition.B1, ChessPosition.C2, ChessPosition.D3, ChessPosition.F5, ChessPosition.G6,
                ChessPosition.H7, ChessPosition.F3, ChessPosition.G2, ChessPosition.H1, ChessPosition.A4, ChessPosition.B4, ChessPosition.C4, ChessPosition.D4,
                ChessPosition.F4, ChessPosition.G4, ChessPosition.H4, ChessPosition.E1, ChessPosition.E2, ChessPosition.E3, ChessPosition.E5, ChessPosition.E6);
    }

    @Test
    void kingMoves() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D4)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing))
                .blackPieces(Collections.emptySet())
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKing).hasOnlyAvailablePositions(ChessPosition.D5, ChessPosition.E5, ChessPosition.E4, ChessPosition.E3, ChessPosition.D3,
                ChessPosition.C3, ChessPosition.C4, ChessPosition.C5);
    }

    @Test
    void kingTakes() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D4)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whitePawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.C5)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.D3)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, whitePawn))
                .blackPieces(Set.of(blackKnight))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKing).hasOnlyAvailablePositions(ChessPosition.D5, ChessPosition.E5, ChessPosition.E4, ChessPosition.E3, ChessPosition.D3,
                ChessPosition.C3, ChessPosition.C4);
    }

    @Test
    void whiteKingShortCastle() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.H1)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, whiteRook))
                .blackPieces(Collections.emptySet())
                .shortCastlesWhite(true)
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKing).hasOnlyAvailablePositions(ChessPosition.D1, ChessPosition.D2, ChessPosition.E2, ChessPosition.F2, ChessPosition.F1,
                ChessPosition.G1);
    }

    @Test
    void whiteKingLongCastle() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.A1)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, whiteRook))
                .blackPieces(Collections.emptySet())
                .longCastlesWhite(true)
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKing).hasOnlyAvailablePositions(ChessPosition.D1, ChessPosition.D2, ChessPosition.E2, ChessPosition.F2, ChessPosition.F1,
                ChessPosition.C1);
    }

    @Test
    void blackKingshortCastle() {
        ChessPiece blackKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.E8)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.A8)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Collections.emptySet())
                .blackPieces(Set.of(blackKing, blackRook))
                .shortCastlesBlack(true)
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackKing).hasOnlyAvailablePositions(ChessPosition.D8, ChessPosition.D7, ChessPosition.E7, ChessPosition.F7, ChessPosition.F8,
                ChessPosition.G8);
    }

    @Test
    void blackKingLongCastle() {
        ChessPiece blackKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.E8)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.A8)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Collections.emptySet())
                .blackPieces(Set.of(blackKing, blackRook))
                .longCastlesBlack(true)
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackKing).hasOnlyAvailablePositions(ChessPosition.D8, ChessPosition.D7, ChessPosition.E7, ChessPosition.F7, ChessPosition.F8,
                ChessPosition.C8);
    }

    @Test
    void whiteKingBlockedCastle() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.H1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.G1)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, whiteRook, whiteKnight))
                .blackPieces(Collections.emptySet())
                .shortCastlesWhite(true)
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKing).hasOnlyAvailablePositions(ChessPosition.D1, ChessPosition.D2, ChessPosition.E2, ChessPosition.F2, ChessPosition.F1);
    }

    @Test
    void whiteKingCheckedCastle() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whiteRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.H1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackKnight = ChessPiece.builder()
                .pieceType(ChessPieceType.KNIGHT)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.G3)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, whiteRook))
                .blackPieces(Set.of(blackKnight))
                .shortCastlesWhite(true)
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKing).hasOnlyAvailablePositions(ChessPosition.D1, ChessPosition.D2, ChessPosition.E2, ChessPosition.F2, ChessPosition.F1);
    }

    @Test
    void noAvailablePositionsForPinnedPieceOnDiagonal() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece pinnedPawn = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D2)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackBishop = ChessPiece.builder()
                .pieceType(ChessPieceType.BISHOP)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.A5)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, pinnedPawn))
                .blackPieces(Set.of(blackBishop))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(pinnedPawn).hasNoAvailablePositions();
    }

    @Test
    void noPinIfAnotherPieceOnDiagonal() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whitePawn2 = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.D2)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece whitePawn1 = ChessPiece.builder()
                .pieceType(ChessPieceType.PAWN)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.C3)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackBishop = ChessPiece.builder()
                .pieceType(ChessPieceType.BISHOP)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.A5)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, whitePawn1, whitePawn2))
                .blackPieces(Set.of(blackBishop))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn1).hasOnlyAvailablePositions(ChessPosition.C4);
        assertThat(whitePawn2).hasOnlyAvailablePositions(ChessPosition.D3, ChessPosition.D4);
    }

    @Test
    void noAvailablePositionsForPinnedPieceOnSameRank() {
        ChessPiece whiteKing = ChessPiece.builder()
                .pieceType(ChessPieceType.KING)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.E1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece pinnedBishop = ChessPiece.builder()
                .pieceType(ChessPieceType.BISHOP)
                .pieceColor(ChessPieceColor.WHITE)
                .position(ChessPosition.F1)
                .availablePositions(new HashSet<>())
                .build();
        ChessPiece blackRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.H1)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, pinnedBishop))
                .blackPieces(Set.of(blackRook))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(pinnedBishop).hasNoAvailablePositions();
    }

    @Test
    void noPinIfAnotherPieceOnSameRank() {
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
        ChessPiece blackRook = ChessPiece.builder()
                .pieceType(ChessPieceType.ROOK)
                .pieceColor(ChessPieceColor.BLACK)
                .position(ChessPosition.H1)
                .availablePositions(new HashSet<>())
                .build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder()
                .whitePieces(Set.of(whiteKing, whiteBishop, whiteKnight))
                .blackPieces(Set.of(blackRook))
                .build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteBishop).hasOnlyAvailablePositions(ChessPosition.E2, ChessPosition.D3, ChessPosition.C4, ChessPosition.B5, ChessPosition.A6,
                ChessPosition.G2, ChessPosition.H3);
    }

}