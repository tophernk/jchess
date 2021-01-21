package de.cj.jchess.service.impl;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPiece;
import de.cj.jchess.entity.ChessPieceColor;
import de.cj.jchess.entity.ChessPiecePosition;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ChessConfigurationSupport {

    void updateAvailablePositions(ChessConfiguration configuration) {
        Set<ChessPiece> pieces = Stream.of(configuration.getWhitePieces(), configuration.getBlackPieces())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        for (ChessPiece piece : pieces) {
            piece.getAvailablePositions().clear();

            switch (piece.getPieceType()) {
                case PAWN:
                    determinePawnMoves(piece, pieces, configuration.getEnPassant());
                    break;
                case KNIGHT:
                    determineKnightMoves(piece, pieces);
                    break;
                case BISHOP:
                    determineBishipMoves(piece, pieces);
                    break;
                default:
                    break;
            }
        }
    }

    private void determineBishipMoves(ChessPiece piece, Set<ChessPiece> pieces) {
        // test the four diagonals clockwise
        int[] fileOffset = {1, 1, -1, -1};
        int[] rankOffset = {-1, 1, 1, -1};

        for (int i = 0; i < 4; i++) {
            char targetFile = piece.getPosition().getFile();
            int targetRank = piece.getPosition().getRank();
            for (int ii = 0; ii < 8; ii++) {
                targetFile += fileOffset[i];
                targetRank += rankOffset[i];
                if (isFileInBounds(targetFile) && isRankInBounds(targetRank)) {
                    ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition(targetFile, targetRank);
                    if (isTargetFree(pieces, targetPosition)) {
                        piece.getAvailablePositions().add(targetPosition);
                    } else if (isTargetOccupiedByOpponent(piece, pieces, targetPosition)) {
                        piece.getAvailablePositions().add(targetPosition);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void determineKnightMoves(ChessPiece piece, Set<ChessPiece> pieces) {
        // test L shape moves clockwise
        int[] fileOffset = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] rankOffset = {-2, -1, 1, 2, 2, 1, -1, -2};

        for (int i = 0; i < 8; i++) {
            char targetFile = (char) (piece.getPosition().getFile() + fileOffset[i]);
            int targetRank = piece.getPosition().getRank() + rankOffset[i];
            if (isFileInBounds(targetFile) && isRankInBounds(targetRank)) {
                ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition(targetFile, targetRank);
                if (isTargetFree(pieces, targetPosition) || isTargetOccupiedByOpponent(piece, pieces, targetPosition)) {
                    piece.getAvailablePositions().add(targetPosition);
                }
            }
        }
    }

    private void determinePawnMoves(ChessPiece pawn, Set<ChessPiece> pieces, ChessPiecePosition enPassant) {
        ChessPiecePosition position = pawn.getPosition();
        boolean isWhitePiece = pawn.getPieceColor() == ChessPieceColor.WHITE;
        int direction = isWhitePiece ? 1 : -1;
        int startRank = isWhitePiece ? 2 : 7;
        int pawnRank = position.getRank();

        // double forward move from starting position
        if (pawnRank == startRank) {
            ChessPiecePosition doubleMovePosition = ChessPiecePosition.retrievePosition(position.getFile(), pawnRank + 2 * direction);
            boolean doubleMovePositionFree = pieces.stream().map(ChessPiece::getPosition).noneMatch(p -> p == doubleMovePosition);
            if (doubleMovePositionFree) {
                pawn.getAvailablePositions().add(doubleMovePosition);
            }
        }
        // en passant
        if (isEnPassantPossible(enPassant, position, direction)) {
            pawn.getAvailablePositions().add(enPassant);
        }
        // standard forward move
        ChessPiecePosition singleMovePosition = ChessPiecePosition.retrievePosition(position.getFile(), pawnRank + 1 * direction);
        if (pieces.stream().map(ChessPiece::getPosition).noneMatch(p -> p == singleMovePosition)) {
            pawn.getAvailablePositions().add(singleMovePosition);
        }
        // takes left and right
        int takesFile = position.getFile() + 1;
        for (int i = 0; i < 2; i++) {
            if (isFileInBounds(takesFile)) {
                ChessPiecePosition targetPosition = ChessPiecePosition.retrievePosition((char) takesFile, singleMovePosition.getRank());
                if (isTargetOccupiedByOpponent(pawn, pieces, targetPosition)) {
                    pawn.getAvailablePositions().add(ChessPiecePosition.retrievePosition((char) takesFile, singleMovePosition.getRank()));
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

    private boolean isTargetOccupiedByOpponent(ChessPiece piece, Set<ChessPiece> pieces, ChessPiecePosition targetPosition) {
        return pieces.stream()
                .filter(p -> p.getPosition() == targetPosition)
                .map(ChessPiece::getPieceColor).anyMatch(c -> c != piece.getPieceColor());
    }

    private boolean isTargetFree(Set<ChessPiece> pieces, ChessPiecePosition targetPosition) {
        return pieces.stream()
                .map(ChessPiece::getPosition)
                .noneMatch(p -> p == targetPosition);
    }

    private boolean isEnPassantPossible(ChessPiecePosition enPassant, ChessPiecePosition position, int direction) {
        return enPassant != null && Math.abs(enPassant.getFile() - position.getFile()) == 1
                && enPassant.getRank() - position.getRank() == direction;
    }

}
