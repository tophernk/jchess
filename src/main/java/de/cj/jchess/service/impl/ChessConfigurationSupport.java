package de.cj.jchess.service.impl;

import de.cj.jchess.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
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
        Set<ChessPiece> result = new HashSet<>();
        Set<ChessPiece> whitePieces = configuration.getWhitePieces();
        // determine all king lines: rank, file + 2 diagonals
        // TODO: 25.01.21 continue here

        return result;
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
        if (isFileInBounds(targetFile) && isRankInBounds(targetRank)) {
            ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition(targetFile, targetRank);
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
            if (isFileInBounds(targetFile) && isRankInBounds(targetRank)) {
                ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition(targetFile, targetRank);
                if (isTargetFree(boardPieces, targetPosition) || isTargetOccupiedByOpponent(knight, boardPieces, targetPosition)) {
                    knight.getAvailablePositions()
                            .add(targetPosition);
                }
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
            if (isFileInBounds(takesFile)) {
                ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition((char) takesFile, singleMovePosition.getRank());
                if (isTargetOccupiedByOpponent(pawn, boardPieces, targetPosition)) {
                    pawn.getAvailablePositions()
                            .add(ChessPiecePosition.retrievePosition((char) takesFile, singleMovePosition.getRank()));
                }
            }
            takesFile = position.getFile() - 1;
        }
    }

    private boolean isRankInBounds(int targetRank) {
        return targetRank >= 1 && targetRank <= 8;
    }

    private boolean isFileInBounds(int takesFile) {
        return takesFile >= 'A' && takesFile <= 'H';
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
