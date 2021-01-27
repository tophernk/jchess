package de.cj.jchess.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ChessPiecePositionTest {

    @Test
    void retrievePosition() {
        ChessPiecePosition piecePosition = ChessPiecePosition.retrievePosition('D', 4);
        ChessPiecePositionAssert.assertThat(piecePosition)
                .isEqualByComparingTo(ChessPiecePosition.D4);
    }

    @Test
    void retrieveAllFilePositions() {
        Set<ChessPiecePosition> cFilePositions = ChessPiecePosition.retrieveAllFilePositions('C');
        Assertions.assertThat(cFilePositions)
                .containsExactlyInAnyOrder(ChessPiecePosition.C1, ChessPiecePosition.C2, ChessPiecePosition.C3, ChessPiecePosition.C4, ChessPiecePosition.C5,
                        ChessPiecePosition.C6, ChessPiecePosition.C7, ChessPiecePosition.C8);
    }

    @Test
    void retrieveAllRankPositions() {
        Set<ChessPiecePosition> firstRankPositions = ChessPiecePosition.retrieveAllRankPositions(1);
        Assertions.assertThat(firstRankPositions)
                .containsExactlyInAnyOrder(ChessPiecePosition.A1, ChessPiecePosition.B1, ChessPiecePosition.C1, ChessPiecePosition.D1, ChessPiecePosition.E1,
                        ChessPiecePosition.F1, ChessPiecePosition.G1, ChessPiecePosition.H1);
    }

    @Test
    void retrieveAllDiagonalPositions() {
        Set<ChessPiecePosition> diagonals = ChessPiecePosition.retrieveAllDiagonalPositions(ChessPiecePosition.D4);
        Assertions.assertThat(diagonals)
                .containsExactlyInAnyOrder(ChessPiecePosition.A1, ChessPiecePosition.B2, ChessPiecePosition.C3, ChessPiecePosition.E5, ChessPiecePosition.F6,
                        ChessPiecePosition.G7, ChessPiecePosition.H8, ChessPiecePosition.A7, ChessPiecePosition.B6, ChessPiecePosition.C5,
                        ChessPiecePosition.E3,
                        ChessPiecePosition.F2, ChessPiecePosition.G1);
    }

    @Test
    void retrieveAllPotentialAttackingPositions() {
        Set<ChessPiecePosition> potentialAttackPositions = ChessPiecePosition.retrieveAllPotentialAttackingPositions(ChessPiecePosition.E1);
        Assertions.assertThat(potentialAttackPositions)
                .containsExactlyInAnyOrder(ChessPiecePosition.A1, ChessPiecePosition.B1, ChessPiecePosition.C1, ChessPiecePosition.D1, ChessPiecePosition.F1,
                        ChessPiecePosition.G1, ChessPiecePosition.H1, ChessPiecePosition.E2, ChessPiecePosition.E3, ChessPiecePosition.E4,
                        ChessPiecePosition.E5, ChessPiecePosition.E6, ChessPiecePosition.E7, ChessPiecePosition.E8, ChessPiecePosition.D2,
                        ChessPiecePosition.C3, ChessPiecePosition.B4, ChessPiecePosition.A5, ChessPiecePosition.F2, ChessPiecePosition.G3,
                        ChessPiecePosition.H4, ChessPiecePosition.C2, ChessPiecePosition.D3, ChessPiecePosition.F3, ChessPiecePosition.G2);
    }

    @Test
    void determineRelativePosition() {
        // TODO: 27.01.21  
    }

    @Test
    void determineDistance() {
        // TODO: 27.01.21
    }
}