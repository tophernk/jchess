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

    @Test
    void whitePawnEnPassant() {
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.B5).availablePositions(new HashSet<>()).build();
        ChessPiece blackPawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.C5).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whitePawn)).blackPieces(Set.of(blackPawn)).enPassant(ChessPiecePosition.C6).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPiecePosition.B6, ChessPiecePosition.C6);
    }

    @Test
    void blackPawnEnPassant() {
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.C4).availablePositions(new HashSet<>()).build();
        ChessPiece blackPawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.D4).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whitePawn)).blackPieces(Set.of(blackPawn)).enPassant(ChessPiecePosition.C3).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPiecePosition.C3, ChessPiecePosition.D3);
    }

    @Test
    void whitePawnTakes() {
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.F3).availablePositions(new HashSet<>()).build();
        ChessPiece blackPawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.G4).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whitePawn)).blackPieces(Set.of(blackPawn)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whitePawn).hasOnlyAvailablePositions(ChessPiecePosition.F4, ChessPiecePosition.G4);
    }

    @Test
    void blackPawnTakes() {
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.F3).availablePositions(new HashSet<>()).build();
        ChessPiece blackPawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.G4).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whitePawn)).blackPieces(Set.of(blackPawn)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(blackPawn).hasOnlyAvailablePositions(ChessPiecePosition.F3, ChessPiecePosition.G3);
    }

    @Test
    void knightMoves() {
        ChessPiece whiteKnight = ChessPiece.builder().pieceType(ChessPieceType.KNIGHT).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D4).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteKnight)).blackPieces(Collections.emptySet()).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKnight).hasOnlyAvailablePositions(ChessPiecePosition.E6, ChessPiecePosition.F5, ChessPiecePosition.F3, ChessPiecePosition.E2, ChessPiecePosition.C2, ChessPiecePosition.B3, ChessPiecePosition.B5, ChessPiecePosition.C6);
    }

    @Test
    void knightMoveWithinBoardBoundaries() {
        ChessPiece whiteKnight = ChessPiece.builder().pieceType(ChessPieceType.KNIGHT).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D2).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteKnight)).blackPieces(Collections.emptySet()).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKnight).hasOnlyAvailablePositions(ChessPiecePosition.E4, ChessPiecePosition.F3, ChessPiecePosition.F1, ChessPiecePosition.B1, ChessPiecePosition.B3, ChessPiecePosition.C4);
    }

    @Test
    void knightTakes() {
        ChessPiece whiteKnight = ChessPiece.builder().pieceType(ChessPieceType.KNIGHT).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D2).availablePositions(new HashSet<>()).build();
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.F1).availablePositions(new HashSet<>()).build();
        ChessPiece blackKnight = ChessPiece.builder().pieceType(ChessPieceType.KNIGHT).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.B1).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteKnight, whitePawn)).blackPieces(Set.of(blackKnight)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKnight).hasOnlyAvailablePositions(ChessPiecePosition.E4, ChessPiecePosition.F3, ChessPiecePosition.B1, ChessPiecePosition.B3, ChessPiecePosition.C4);
    }

    @Test
    void bishopMoves() {
        ChessPiece whiteBishop = ChessPiece.builder().pieceType(ChessPieceType.BISHOP).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D4).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteBishop)).blackPieces(Collections.emptySet()).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteBishop).hasOnlyAvailablePositions(ChessPiecePosition.A1, ChessPiecePosition.B2, ChessPiecePosition.C3, ChessPiecePosition.E5, ChessPiecePosition.F6, ChessPiecePosition.G7, ChessPiecePosition.H8,
                ChessPiecePosition.A7, ChessPiecePosition.B6, ChessPiecePosition.C5, ChessPiecePosition.E3, ChessPiecePosition.F2, ChessPiecePosition.G1);
    }

    @Test
    void bishopTakes() {
        ChessPiece whiteBishop = ChessPiece.builder().pieceType(ChessPieceType.BISHOP).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D4).availablePositions(new HashSet<>()).build();
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.B2).availablePositions(new HashSet<>()).build();
        ChessPiece blackKnight = ChessPiece.builder().pieceType(ChessPieceType.KNIGHT).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.F6).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteBishop, whitePawn)).blackPieces(Set.of(blackKnight)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteBishop).hasOnlyAvailablePositions(ChessPiecePosition.C3, ChessPiecePosition.E5, ChessPiecePosition.F6, ChessPiecePosition.A7, ChessPiecePosition.B6, ChessPiecePosition.C5, ChessPiecePosition.E3, ChessPiecePosition.F2, ChessPiecePosition.G1);
    }

    @Test
    void rookMoves() {
        ChessPiece whiteRook = ChessPiece.builder().pieceType(ChessPieceType.ROOK).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.E4).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteRook)).blackPieces(Collections.emptySet()).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteRook).hasOnlyAvailablePositions(ChessPiecePosition.E1, ChessPiecePosition.E2, ChessPiecePosition.E3, ChessPiecePosition.E5, ChessPiecePosition.E6, ChessPiecePosition.E7, ChessPiecePosition.E8, ChessPiecePosition.A4, ChessPiecePosition.B4, ChessPiecePosition.C4, ChessPiecePosition.D4, ChessPiecePosition.F4, ChessPiecePosition.G4, ChessPiecePosition.H4);
    }

    @Test
    void rookTakes() {
        ChessPiece whiteRook = ChessPiece.builder().pieceType(ChessPieceType.ROOK).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.E4).availablePositions(new HashSet<>()).build();
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.C4).availablePositions(new HashSet<>()).build();
        ChessPiece blackKnight = ChessPiece.builder().pieceType(ChessPieceType.KNIGHT).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.E6).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteRook, whitePawn)).blackPieces(Set.of(blackKnight)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteRook).hasOnlyAvailablePositions(ChessPiecePosition.E1, ChessPiecePosition.E2, ChessPiecePosition.E3, ChessPiecePosition.E5, ChessPiecePosition.E6, ChessPiecePosition.D4, ChessPiecePosition.F4, ChessPiecePosition.G4, ChessPiecePosition.H4);
    }

    @Test
    void queenMoves() {
        ChessPiece whiteQueen = ChessPiece.builder().pieceType(ChessPieceType.QUEEN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.E4).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteQueen)).blackPieces(Collections.emptySet()).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteQueen).hasOnlyAvailablePositions(ChessPiecePosition.B1, ChessPiecePosition.C2, ChessPiecePosition.D3, ChessPiecePosition.F5, ChessPiecePosition.G6, ChessPiecePosition.H7,
                ChessPiecePosition.A8, ChessPiecePosition.B7, ChessPiecePosition.C6, ChessPiecePosition.D5, ChessPiecePosition.F3, ChessPiecePosition.G2, ChessPiecePosition.H1, ChessPiecePosition.A4,
                ChessPiecePosition.B4, ChessPiecePosition.C4, ChessPiecePosition.D4, ChessPiecePosition.F4, ChessPiecePosition.G4, ChessPiecePosition.H4, ChessPiecePosition.E1, ChessPiecePosition.E2,
                ChessPiecePosition.E3, ChessPiecePosition.E5, ChessPiecePosition.E6, ChessPiecePosition.E7, ChessPiecePosition.E8);
    }

    @Test
    void queenTakes() {
        ChessPiece whiteQueen = ChessPiece.builder().pieceType(ChessPieceType.QUEEN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.E4).availablePositions(new HashSet<>()).build();
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D5).availablePositions(new HashSet<>()).build();
        ChessPiece blackKnight = ChessPiece.builder().pieceType(ChessPieceType.KNIGHT).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.E6).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteQueen, whitePawn)).blackPieces(Set.of(blackKnight)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteQueen).hasOnlyAvailablePositions(ChessPiecePosition.B1, ChessPiecePosition.C2, ChessPiecePosition.D3, ChessPiecePosition.F5, ChessPiecePosition.G6, ChessPiecePosition.H7,
                ChessPiecePosition.F3, ChessPiecePosition.G2, ChessPiecePosition.H1, ChessPiecePosition.A4, ChessPiecePosition.B4, ChessPiecePosition.C4, ChessPiecePosition.D4, ChessPiecePosition.F4,
                ChessPiecePosition.G4, ChessPiecePosition.H4, ChessPiecePosition.E1, ChessPiecePosition.E2, ChessPiecePosition.E3, ChessPiecePosition.E5, ChessPiecePosition.E6);
    }

    @Test
    void kingMoves() {
        ChessPiece whiteKing = ChessPiece.builder().pieceType(ChessPieceType.KING).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D4).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteKing)).blackPieces(Collections.emptySet()).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKing).hasOnlyAvailablePositions(ChessPiecePosition.D5, ChessPiecePosition.E5, ChessPiecePosition.E4, ChessPiecePosition.E3, ChessPiecePosition.D3, ChessPiecePosition.C3, ChessPiecePosition.C4, ChessPiecePosition.C5);
    }

    @Test
    void kingTakes() {
        ChessPiece whiteKing = ChessPiece.builder().pieceType(ChessPieceType.KING).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D4).availablePositions(new HashSet<>()).build();
        ChessPiece whitePawn = ChessPiece.builder().pieceType(ChessPieceType.PAWN).pieceColor(ChessPieceColor.WHITE).position(ChessPiecePosition.D5).availablePositions(new HashSet<>()).build();
        ChessPiece blackKnight = ChessPiece.builder().pieceType(ChessPieceType.KNIGHT).pieceColor(ChessPieceColor.BLACK).position(ChessPiecePosition.E6).availablePositions(new HashSet<>()).build();
        ChessConfiguration chessConfiguration = ChessConfiguration.builder().whitePieces(Set.of(whiteKing, whitePawn)).blackPieces(Set.of(blackKnight)).build();
        chessConfigurationSupport.updateAvailablePositions(chessConfiguration);

        assertThat(whiteKing).hasOnlyAvailablePositions(ChessPiecePosition.D5, ChessPiecePosition.E5, ChessPiecePosition.E4, ChessPiecePosition.E3, ChessPiecePosition.D3, ChessPiecePosition.C3, ChessPiecePosition.C4, ChessPiecePosition.C5);
    }

}