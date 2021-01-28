package de.cj.jchess.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ChessPositionTest {

    @Test
    void retrievePosition() {
        ChessPosition piecePosition = ChessPosition.retrievePosition('D', 4);
        ChessPositionAssert.assertThat(piecePosition)
                .isEqualByComparingTo(ChessPosition.D4);
    }

    @Test
    void retrieveAllFilePositions() {
        Set<ChessPosition> cFilePositions = ChessPosition.retrieveAllFilePositions('C');
        Assertions.assertThat(cFilePositions)
                .containsExactlyInAnyOrder(ChessPosition.C1, ChessPosition.C2, ChessPosition.C3, ChessPosition.C4, ChessPosition.C5,
                        ChessPosition.C6, ChessPosition.C7, ChessPosition.C8);
    }

    @Test
    void retrieveAllRankPositions() {
        Set<ChessPosition> firstRankPositions = ChessPosition.retrieveAllRankPositions(1);
        Assertions.assertThat(firstRankPositions)
                .containsExactlyInAnyOrder(ChessPosition.A1, ChessPosition.B1, ChessPosition.C1, ChessPosition.D1, ChessPosition.E1,
                        ChessPosition.F1, ChessPosition.G1, ChessPosition.H1);
    }

    @Test
    void retrieveAllDiagonalPositions() {
        Set<ChessPosition> diagonals = ChessPosition.retrieveAllDiagonalPositions(ChessPosition.D4);
        Assertions.assertThat(diagonals)
                .containsExactlyInAnyOrder(ChessPosition.A1, ChessPosition.B2, ChessPosition.C3, ChessPosition.E5, ChessPosition.F6,
                        ChessPosition.G7, ChessPosition.H8, ChessPosition.A7, ChessPosition.B6, ChessPosition.C5,
                        ChessPosition.E3,
                        ChessPosition.F2, ChessPosition.G1);
    }

    @Test
    void retrieveAllPotentialAttackingPositions() {
        Set<ChessPosition> potentialAttackPositions = ChessPosition.retrieveAllPotentialAttackingPositions(ChessPosition.E1);
        Assertions.assertThat(potentialAttackPositions)
                .containsExactlyInAnyOrder(ChessPosition.A1, ChessPosition.B1, ChessPosition.C1, ChessPosition.D1, ChessPosition.F1,
                        ChessPosition.G1, ChessPosition.H1, ChessPosition.E2, ChessPosition.E3, ChessPosition.E4,
                        ChessPosition.E5, ChessPosition.E6, ChessPosition.E7, ChessPosition.E8, ChessPosition.D2,
                        ChessPosition.C3, ChessPosition.B4, ChessPosition.A5, ChessPosition.F2, ChessPosition.G3,
                        ChessPosition.H4, ChessPosition.C2, ChessPosition.D3, ChessPosition.F3, ChessPosition.G2);
    }

    @Test
    void determineRelativePosition() {
        Assertions.assertThat(ChessPosition.determineDirection(ChessPosition.E4, ChessPosition.E8)).isSameAs(ChessDirection.TOP);
        Assertions.assertThat(ChessPosition.determineDirection(ChessPosition.E4, ChessPosition.G8)).isSameAs(ChessDirection.TOP_RIGHT);
        Assertions.assertThat(ChessPosition.determineDirection(ChessPosition.E4, ChessPosition.H4)).isSameAs(ChessDirection.RIGHT);
        Assertions.assertThat(ChessPosition.determineDirection(ChessPosition.E4, ChessPosition.H1)).isSameAs(ChessDirection.BOTTOM_RIGHT);
        Assertions.assertThat(ChessPosition.determineDirection(ChessPosition.E4, ChessPosition.E1)).isSameAs(ChessDirection.BOTTOM);
        Assertions.assertThat(ChessPosition.determineDirection(ChessPosition.E4, ChessPosition.A1)).isSameAs(ChessDirection.BOTTOM_LEFT);
        Assertions.assertThat(ChessPosition.determineDirection(ChessPosition.E4, ChessPosition.B4)).isSameAs(ChessDirection.LEFT);
        Assertions.assertThat(ChessPosition.determineDirection(ChessPosition.E4, ChessPosition.A8)).isSameAs(ChessDirection.TOP_LEFT);
    }

    @Test
    void determineDistance() {
        Assertions.assertThat(ChessPosition.determineDistance(ChessPosition.E4, ChessPosition.E8)).isEqualTo(4);
        Assertions.assertThat(ChessPosition.determineDistance(ChessPosition.E4, ChessPosition.G8)).isEqualTo(4);
        Assertions.assertThat(ChessPosition.determineDistance(ChessPosition.E4, ChessPosition.H7)).isEqualTo(3);
        Assertions.assertThat(ChessPosition.determineDistance(ChessPosition.E4, ChessPosition.E1)).isEqualTo(3);
    }
}