package de.cj.jchess.service.impl;

import de.cj.jchess.entity.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ChessConfigurationSupport {

    private static final EnumSet<ChessPiecePosition> WHITE_SHORT_CASTLE_MOVING_FIELDS = EnumSet.of(ChessPiecePosition.F1, ChessPiecePosition.G1);
    private static final EnumSet<ChessPiecePosition> WHITE_SHORT_CASTLE_CHECK_FIELDS = EnumSet.of(ChessPiecePosition.F1, ChessPiecePosition.G1,
            ChessPiecePosition.H1);
    private static final EnumSet<ChessPiecePosition> WHITE_LONG_CASTLE_MOVING_FIELDS = EnumSet.of(ChessPiecePosition.B1, ChessPiecePosition.C1,
            ChessPiecePosition.D1);
    private static final EnumSet<ChessPiecePosition> WHITE_LONG_CASTLE_CHECK_FIELDS = EnumSet.of(ChessPiecePosition.A1, ChessPiecePosition.B1,
            ChessPiecePosition.C1, ChessPiecePosition.D1);
    private static final EnumSet<ChessPiecePosition> BLACK_SHORT_CASTLE_MOVING_FIELDS = EnumSet.of(ChessPiecePosition.F8, ChessPiecePosition.G8);
    private static final EnumSet<ChessPiecePosition> BLACK_SHORT_CASTLE_CHECK_FIELDS = EnumSet.of(ChessPiecePosition.F8, ChessPiecePosition.G8,
            ChessPiecePosition.H8);
    private static final EnumSet<ChessPiecePosition> BLACK_LONG_CASTLE_MOVING_FIELDS = EnumSet.of(ChessPiecePosition.B8, ChessPiecePosition.C8,
            ChessPiecePosition.D8);
    private static final EnumSet<ChessPiecePosition> BLACK_LONG_CASTLE_CHECK_FIELDS = EnumSet.of(ChessPiecePosition.A8, ChessPiecePosition.B8,
            ChessPiecePosition.C8, ChessPiecePosition.D8);

    void updateAvailablePositions(ChessConfiguration configuration) {
        Set<ChessPiece> piecesToUpdate = Stream.of(configuration.getWhitePieces(), configuration.getBlackPieces())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        Set<ChessPiece> pinnedPieces = determinePinnedPieces(configuration);
        piecesToUpdate.removeAll(pinnedPieces);

        for (ChessPiece pieceToMove : piecesToUpdate) {
            pieceToMove.getAvailablePositions()
                    .clear();

            switch (pieceToMove.getPieceType()) {
                case PAWN:
                    determinePawnMoves(pieceToMove, piecesToUpdate, configuration.getEnPassant());
                    break;
                case KNIGHT:
                    determineKnightMoves(pieceToMove, piecesToUpdate);
                    break;
                case BISHOP:
                    determineBishopMoves(pieceToMove, piecesToUpdate);
                    break;
                case ROOK:
                    determineRookMoves(pieceToMove, piecesToUpdate);
                    break;
                case QUEEN:
                    determineQueenMoves(pieceToMove, piecesToUpdate);
                    break;
                case KING:
                    // kings have to be evaluated after other pieces have been updated for proper castle checks
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        piecesToUpdate.stream()
                .filter(p -> p.getPieceType() == ChessPieceType.KING)
                .forEach(king -> determineKingMoves(king, piecesToUpdate, configuration));
    }

    private Set<ChessPiece> determinePinnedPieces(ChessConfiguration configuration) {
        Set<ChessPiece> whitePieces = configuration.getWhitePieces();

        // determine all king lines: rank, file + 2 diagonals
        ChessPiece whiteKing = whitePieces.stream()
                .filter(p -> p.getPieceType() == ChessPieceType.KING)
                .findAny()
                .orElse(null);

        return whiteKing != null ? retrievePinnedPieces(whiteKing, configuration) : Collections.emptySet();
    }

    Set<ChessPiece> retrievePinnedPieces(ChessPiece king, ChessConfiguration configuration) {
        // is piece protecting diagonal against attacking bishop/queen
        // 1. is bishop or queen on diagonal
        Set<ChessPiece> potentialAttackers = king.getPieceColor() == ChessPieceColor.WHITE ? configuration.getBlackPieces() : configuration.getWhitePieces();
        Set<ChessPiece> potentialProtectors = king.getPieceColor() == ChessPieceColor.WHITE ? configuration.getWhitePieces() : configuration.getBlackPieces();

        Set<ChessPiece> pinnedPieces = determinePinsOnDiagonals(king, potentialAttackers, potentialProtectors);
        pinnedPieces.addAll(determinePinsOnRanksAndFiles(king, potentialAttackers, potentialProtectors));

        return pinnedPieces;
    }

    private Set<ChessPiece> determinePinsOnRanksAndFiles(ChessPiece king, Set<ChessPiece> potentialAttackers, Set<ChessPiece> potentialProtectors) {
        Set<ChessPiecePosition> positionsToCheck = ChessPiecePosition.retrieveAllFilePositions(king.getPosition()
                .getFile());
        positionsToCheck.addAll(ChessPiecePosition.retrieveAllRankPositions(king.getPosition()
                .getRank()));
        Set<ChessPiece> fileRankAttackers = potentialAttackers.stream()
                .filter(piece -> positionsToCheck.contains(piece.getPosition()))
                .filter(piece -> EnumSet.of(ChessPieceType.ROOK, ChessPieceType.QUEEN)
                        .contains(piece.getPieceType()))
                .collect(Collectors.toSet());

        return determinePins(king, fileRankAttackers, potentialProtectors, positionsToCheck);
    }

    private Set<ChessPiece> determinePinsOnDiagonals(ChessPiece king, Set<ChessPiece> potentialAttackers, Set<ChessPiece> potentialProtectors) {
        Set<ChessPiecePosition> diagonalPositions = ChessPiecePosition.retrieveAllDiagonalPositions(king.getPosition());
        Set<ChessPiece> diagonalAttackers = potentialAttackers.stream()
                .filter(piece -> diagonalPositions.contains(piece.getPosition()))
                .filter(piece -> EnumSet.of(ChessPieceType.BISHOP, ChessPieceType.QUEEN)
                        .contains(piece.getPieceType()))
                .collect(Collectors.toSet());

        return determinePins(king, diagonalAttackers, potentialProtectors, diagonalPositions);
    }

    private Set<ChessPiece> determinePins(ChessPiece king, Set<ChessPiece> potentialAttackers, Set<ChessPiece> potentialProtectors,
            Set<ChessPiecePosition> positionsToCheck) {
        Set<ChessPiece> fileRankProtectors = potentialProtectors.stream()
                .filter(piece -> positionsToCheck.contains(piece.getPosition()))
                .collect(Collectors.toSet());

        if (fileRankProtectors.isEmpty()) {
            return new HashSet<>();
        }

        // 3. partition attackers and protectors to same diagonals
        Map<ChessDirection, List<ChessPiece>> partions = partitionPiecesByDirection(king, potentialAttackers, fileRankProtectors);

        // 4. sort attackers and protectors by distance to king
        return sortPartitionedPiecesByDistanceToGivenPieceAndCheckForPins(king, partions);
    }

    private Set<ChessPiece> sortPartitionedPiecesByDistanceToGivenPieceAndCheckForPins(ChessPiece king, Map<ChessDirection, List<ChessPiece>> partions) {
        Set<ChessPiece> pinnedPieces = new HashSet<>();
        for (List<ChessPiece> list : partions.values()) {
            Collections.sort(list, Comparator.comparingInt(p -> ChessPiecePosition.determineDistance(p.getPosition(), king.getPosition())));
            ChessPiece closestAttacker = list.stream()
                    .filter(p -> p.getPieceColor() != king.getPieceColor())
                    .findFirst()
                    .orElse(null);
            if (list.indexOf(closestAttacker) == 1) {
                // 0 = no pinned pieces here, king is in check
                // 1 = piece at 0 is pinned
                // >1 = more than 1 protector
                pinnedPieces.add(list.get(0));
            }
        }
        return pinnedPieces;
    }

    private Map<ChessDirection, List<ChessPiece>> partitionPiecesByDirection(ChessPiece king, Set<ChessPiece> diagonalAttackers,
            Set<ChessPiece> diagonalProtectors) {
        Map<ChessDirection, List<ChessPiece>> partitions = new EnumMap<>(ChessDirection.class);
        for (ChessPiece piece : Stream.of(diagonalAttackers, diagonalProtectors)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())) {
            ChessDirection relativePositionToKing = ChessPiecePosition.determineDirection(king.getPosition(), piece.getPosition());
            if (!partitions.containsKey(relativePositionToKing)) {
                partitions.put(relativePositionToKing, new ArrayList<>());
            }
            partitions.get(relativePositionToKing)
                    .add(piece);
        }
        return partitions;
    }

    private void determineKingMoves(ChessPiece king, Set<ChessPiece> boardPieces, ChessConfiguration configuration) {
        // test all direction
        int[] fileOffset = {1, 1, 1, 0, -1, -1, -1, 0};
        int[] rankOffset = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int i = 0; i < fileOffset.length; i++) {
            char targetFile = king.getPosition()
                    .getFile();
            int targetRank = king.getPosition()
                    .getRank();
            targetFile += fileOffset[i];
            targetRank += rankOffset[i];
            addMoveIfAvailable(boardPieces, king, targetFile, targetRank);
        }

        // test castles when in starting position
        int startingRank = king.getPieceColor() == ChessPieceColor.WHITE ? 1 : 8;
        if (king.getPosition()
                .getFile() == 'E' && king.getPosition()
                .getRank() == startingRank) {
            if (king.getPieceColor() == ChessPieceColor.WHITE) {
                addWhiteCastlesIfAvailable(king, boardPieces, configuration);
            } else {
                addBlackCastlesIfAvailable(king, boardPieces, configuration);
            }
        }
    }

    private void addBlackCastlesIfAvailable(ChessPiece king, Set<ChessPiece> boardPieces, ChessConfiguration configuration) {
        if (canCastle(configuration.isShortCastlesBlack(), ChessPieceColor.WHITE, boardPieces, BLACK_SHORT_CASTLE_MOVING_FIELDS,
                BLACK_SHORT_CASTLE_CHECK_FIELDS)) {
            king.getAvailablePositions()
                    .add(ChessPiecePosition.G8);
        }
        if (canCastle(configuration.isLongCastlesBlack(), ChessPieceColor.WHITE, boardPieces, BLACK_LONG_CASTLE_MOVING_FIELDS,
                BLACK_LONG_CASTLE_CHECK_FIELDS)) {
            king.getAvailablePositions()
                    .add(ChessPiecePosition.C8);
        }
    }

    private void addWhiteCastlesIfAvailable(ChessPiece king, Set<ChessPiece> boardPieces, ChessConfiguration configuration) {
        if (canCastle(configuration.isShortCastlesWhite(), ChessPieceColor.BLACK, boardPieces, WHITE_SHORT_CASTLE_MOVING_FIELDS,
                WHITE_SHORT_CASTLE_CHECK_FIELDS)) {
            king.getAvailablePositions()
                    .add(ChessPiecePosition.G1);
        }
        if (canCastle(configuration.isLongCastlesWhite(), ChessPieceColor.BLACK, boardPieces, WHITE_LONG_CASTLE_MOVING_FIELDS,
                WHITE_LONG_CASTLE_CHECK_FIELDS)) {
            king.getAvailablePositions()
                    .add(ChessPiecePosition.C1);
        }
    }

    private boolean canCastle(boolean castlesToCheck, ChessPieceColor attacker, Set<ChessPiece> boardPieces, EnumSet<ChessPiecePosition> movingFields,
            EnumSet<ChessPiecePosition> checkFreeFields) {
        return castlesToCheck && !isPositionBlocked(boardPieces, movingFields) && !isPositionUnderAttack(boardPieces, attacker, checkFreeFields);
    }

    private boolean isPositionUnderAttack(Set<ChessPiece> boardPieces, ChessPieceColor attackingColor, EnumSet<ChessPiecePosition> positionsToCheck) {
        return boardPieces.stream()
                .filter(p -> p.getPieceColor() == attackingColor)
                .map(ChessPiece::getAvailablePositions)
                .flatMap(Collection::stream)
                .anyMatch(positionsToCheck::contains);
    }

    private boolean isPositionBlocked(Set<ChessPiece> boardPieces, EnumSet<ChessPiecePosition> positionsToCheck) {
        return boardPieces.stream()
                .map(ChessPiece::getPosition)
                .anyMatch(positionsToCheck::contains);
    }

    private void determineQueenMoves(ChessPiece queen, Set<ChessPiece> boardPieces) {
        // test all direction
        int[] fileOffset = {1, 1, 1, 0, -1, -1, -1, 0};
        int[] rankOffset = {-1, 0, 1, 1, 1, 0, -1, -1};

        determineAvailableMovesInGivenOffsetDirection(boardPieces, queen, fileOffset, rankOffset);
    }

    private void determineRookMoves(ChessPiece piece, Set<ChessPiece> pieces) {
        // test the four rook directions clockwise
        int[] fileOffset = {1, 0, -1, 0};
        int[] rankOffset = {0, 1, 0, -1};

        determineAvailableMovesInGivenOffsetDirection(pieces, piece, fileOffset, rankOffset);
    }

    private void determineAvailableMovesInGivenOffsetDirection(Set<ChessPiece> boardPieces, ChessPiece pieceToMove, int[] fileOffset, int[] rankOffset) {
        for (int i = 0; i < fileOffset.length; i++) {
            char targetFile = pieceToMove.getPosition()
                    .getFile();
            int targetRank = pieceToMove.getPosition()
                    .getRank();
            for (int ii = 0; ii < 8; ii++) {
                targetFile += fileOffset[i];
                targetRank += rankOffset[i];
                boolean continueDirection = addMoveIfAvailable(boardPieces, pieceToMove, targetFile, targetRank);
                if (!continueDirection) {
                    break;
                }
            }
        }
    }

    private boolean addMoveIfAvailable(Set<ChessPiece> boardPieces, ChessPiece pieceToMove, char targetFile, int targetRank) {
        boolean continueDirection = false;
        ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition(targetFile, targetRank);
        if (targetPosition != null) {
            if (isTargetFree(boardPieces, targetPosition)) {
                pieceToMove.getAvailablePositions()
                        .add(targetPosition);
                continueDirection = true;
            } else if (isTargetOccupiedByOpponent(pieceToMove, boardPieces, targetPosition)) {
                pieceToMove.getAvailablePositions()
                        .add(targetPosition);
                continueDirection = false;
            } else {
                continueDirection = false;
            }
        }
        return continueDirection;
    }

    private void determineBishopMoves(ChessPiece bishop, Set<ChessPiece> boardPieces) {
        // test the four diagonals clockwise
        int[] fileOffset = {1, 1, -1, -1};
        int[] rankOffset = {-1, 1, 1, -1};

        determineAvailableMovesInGivenOffsetDirection(boardPieces, bishop, fileOffset, rankOffset);
    }

    private void determineKnightMoves(ChessPiece knight, Set<ChessPiece> boardPieces) {
        // test L shape moves clockwise
        int[] fileOffset = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] rankOffset = {-2, -1, 1, 2, 2, 1, -1, -2};

        for (int i = 0; i < 8; i++) {
            char targetFile = (char) (knight.getPosition()
                    .getFile() + fileOffset[i]);
            int targetRank = knight.getPosition()
                    .getRank() + rankOffset[i];
            ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition(targetFile, targetRank);
            if (targetPosition != null && (isTargetFree(boardPieces, targetPosition) || isTargetOccupiedByOpponent(knight, boardPieces, targetPosition))) {
                knight.getAvailablePositions()
                        .add(targetPosition);
            }
        }
    }

    private void determinePawnMoves(ChessPiece pawn, Set<ChessPiece> boardPieces, ChessPiecePosition enPassant) {
        ChessPiecePosition position = pawn.getPosition();
        boolean isWhitePiece = pawn.getPieceColor() == ChessPieceColor.WHITE;
        int direction = isWhitePiece ? 1 : -1;
        int startRank = isWhitePiece ? 2 : 7;
        int pawnRank = position.getRank();

        // double forward move from starting position
        if (pawnRank == startRank) {
            ChessPiecePosition doubleMovePosition = ChessPiecePosition.retrievePosition(position.getFile(), pawnRank + 2 * direction);
            boolean doubleMovePositionFree = boardPieces.stream()
                    .map(ChessPiece::getPosition)
                    .noneMatch(p -> p == doubleMovePosition);
            if (doubleMovePositionFree) {
                pawn.getAvailablePositions()
                        .add(doubleMovePosition);
            }
        }
        // en passant
        if (isEnPassantPossible(enPassant, position, direction)) {
            pawn.getAvailablePositions()
                    .add(enPassant);
        }
        // standard forward move
        ChessPiecePosition singleMovePosition = ChessPiecePosition.retrievePosition(position.getFile(), pawnRank + direction);
        if (boardPieces.stream()
                .map(ChessPiece::getPosition)
                .noneMatch(p -> p == singleMovePosition)) {
            pawn.getAvailablePositions()
                    .add(singleMovePosition);
        }
        // takes left and right
        int takesFile = position.getFile() + 1;
        for (int i = 0; i < 2; i++) {
            ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition((char) takesFile, singleMovePosition.getRank());
            if (targetPosition != null && isTargetOccupiedByOpponent(pawn, boardPieces, targetPosition)) {
                pawn.getAvailablePositions()
                        .add(ChessPiecePosition.retrievePosition((char) takesFile, singleMovePosition.getRank()));
            }
            takesFile = position.getFile() - 1;
        }
    }


    private boolean isTargetOccupiedByOpponent(ChessPiece pieceToMove, Set<ChessPiece> boardPieces, ChessPiecePosition targetPosition) {
        return boardPieces.stream()
                .filter(p -> p.getPosition() == targetPosition)
                .map(ChessPiece::getPieceColor)
                .anyMatch(c -> c != pieceToMove.getPieceColor());
    }

    private boolean isTargetFree(Set<ChessPiece> boardPieces, ChessPiecePosition targetPosition) {
        return boardPieces.stream()
                .map(ChessPiece::getPosition)
                .noneMatch(p -> p == targetPosition);
    }

    private boolean isEnPassantPossible(ChessPiecePosition enPassant, ChessPiecePosition position, int direction) {
        return enPassant != null && Math.abs(enPassant.getFile() - position.getFile()) == 1
                && enPassant.getRank() - position.getRank() == direction;
    }

}
