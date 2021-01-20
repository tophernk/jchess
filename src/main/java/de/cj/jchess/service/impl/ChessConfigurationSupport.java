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
                    determinePawnMove(piece, pieces);
                    break;
                default:
                    break;
            }
        }
    }

    private void determinePawnMove(ChessPiece pawn, Set<ChessPiece> pieces) {
        ChessPiecePosition position = pawn.getPosition();
        boolean isWhitePiece = pawn.getPieceColor() == ChessPieceColor.WHITE;
        int direction = isWhitePiece ? 1 : -1;
        int startRank = isWhitePiece ? 2 : 7;
        int enpassantRank = isWhitePiece ? 5 : 4;
        ChessPieceColor oppositeColor = isWhitePiece ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;

        int pawnRank = position.getRank();
        ChessPiecePosition singleMovePosition = ChessPiecePosition.retrievePosition(position.getFile(), pawnRank + 1 * direction);

        // double forward move from starting position
        if (pawnRank == startRank) {
            ChessPiecePosition doubleMovePosition = ChessPiecePosition.retrievePosition(position.getFile(), pawnRank + 2 * direction);
            boolean doubleMovePositionFree = pieces.stream().map(ChessPiece::getPosition).noneMatch(p -> p == doubleMovePosition);
            if (doubleMovePositionFree) {
                pawn.getAvailablePositions().add(doubleMovePosition);
            }
        }
        // en passant
        // TODO: 20.01.21

        // standard pawn move
        if (pieces.stream().map(ChessPiece::getPosition).noneMatch(p -> p == singleMovePosition)) {
            pawn.getAvailablePositions().add(singleMovePosition);
        }
        // takes left and right


    }

}
