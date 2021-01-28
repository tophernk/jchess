package de.cj.jchess.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ChessPiecePosition {
    A1('A', 1), A2('A', 2), A3('A', 3), A4('A', 4), A5('A', 5), A6('A', 6), A7('A', 7), A8('A', 8),
    B1('B', 1), B2('B', 2), B3('B', 3), B4('B', 4), B5('B', 5), B6('B', 6), B7('B', 7), B8('B', 8),
    C1('C', 1), C2('C', 2), C3('C', 3), C4('C', 4), C5('C', 5), C6('C', 6), C7('C', 7), C8('C', 8),
    D1('D', 1), D2('D', 2), D3('D', 3), D4('D', 4), D5('D', 5), D6('D', 6), D7('D', 7), D8('D', 8),
    E1('E', 1), E2('E', 2), E3('E', 3), E4('E', 4), E5('E', 5), E6('E', 6), E7('E', 7), E8('E', 8),
    F1('F', 1), F2('F', 2), F3('F', 3), F4('F', 4), F5('F', 5), F6('F', 6), F7('F', 7), F8('F', 8),
    G1('G', 1), G2('G', 2), G3('G', 3), G4('G', 4), G5('G', 5), G6('G', 6), G7('G', 7), G8('G', 8),
    H1('H', 1), H2('H', 2), H3('H', 3), H4('H', 4), H5('H', 5), H6('H', 6), H7('H', 7), H8('H', 8);

    ChessPiecePosition(char file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    char file;
    int rank;

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public static ChessPiecePosition retrievePosition(char file, int rank) {
        return Arrays.stream(ChessPiecePosition.values())
                .filter(p -> p.getFile() == file)
                .filter(p -> p.getRank() == rank)
                .findAny()
                .orElse(null);
    }

    public static Set<ChessPiecePosition> retrieveAllFilePositions(char file) {
        return Arrays.stream(ChessPiecePosition.values())
                .filter(p -> p.getFile() == file)
                .collect(Collectors.toSet());
    }

    public static Set<ChessPiecePosition> retrieveAllRankPositions(int rank) {
        return Arrays.stream(ChessPiecePosition.values())
                .filter(p -> p.getRank() == rank)
                .collect(Collectors.toSet());
    }

    public static Set<ChessPiecePosition> retrieveAllDiagonalPositions(ChessPiecePosition position) {
        return Arrays.stream(ChessPiecePosition.values())
                .filter(p -> Math.abs(p.getFile() - position.getFile()) == Math.abs(p.getRank() - position.getRank()))
                .filter(p -> p != position)
                .collect(Collectors.toSet());
    }

    public static Set<ChessPiecePosition> retrieveAllPotentialAttackingPositions(ChessPiecePosition position) {
        Set<ChessPiecePosition> result = Stream.of(retrieveAllDiagonalPositions(position), retrieveAllFilePositions(position.getFile()),
                retrieveAllRankPositions(position.getRank()))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        // add L shape Knight Moves
        int[] fileOffset = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] rankOffset = {-2, -1, 1, 2, 2, 1, -1, -2};

        for (int i = 0; i < 8; i++) {
            Optional.ofNullable(retrievePosition((char) (position.getFile() + fileOffset[i]), position.getRank() + rankOffset[i]))
                    .ifPresent(result::add);
        }

        // remove requested position
        result.remove(position);

        return result;
    }

    public static ChessDirection determineDirection(ChessPiecePosition fromPosition, ChessPiecePosition toPosition) {
        if (fromPosition.getFile() > toPosition.getFile()) {
            // left cases
            if (fromPosition.getRank() > toPosition.getRank()) {
                return ChessDirection.BOTTOM_LEFT;
            }
            if (fromPosition.getRank() == toPosition.getRank()) {
                return ChessDirection.LEFT;
            }
            if (fromPosition.getRank() < toPosition.getRank()) {
                return ChessDirection.TOP_LEFT;
            }
        }
        if (fromPosition.getFile() == toPosition.getFile()) {
            // top or bottom
            if (fromPosition.getRank() > toPosition.getRank()) {
                return ChessDirection.BOTTOM;
            }
            return ChessDirection.TOP;
        }
        if (fromPosition.getFile() < toPosition.getFile()) {
            // right cases
            if (fromPosition.getRank() > toPosition.getRank()) {
                return ChessDirection.BOTTOM_RIGHT;
            }
            if (fromPosition.getRank() == toPosition.getRank()) {
                return ChessDirection.RIGHT;
            }
            if (fromPosition.getRank() < toPosition.getRank()) {
                return ChessDirection.TOP_RIGHT;
            }
        }
        throw new IllegalStateException();
    }

    public static int determineDistance(ChessPiecePosition from, ChessPiecePosition to) {
        return Math.max(Math.abs(from.getRank() - to.getRank()), Math.abs(from.getFile() - to.getFile()));
    }
}
