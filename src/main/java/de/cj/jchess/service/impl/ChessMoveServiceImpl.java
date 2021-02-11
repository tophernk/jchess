package de.cj.jchess.service.impl;

import de.cj.jchess.entity.*;
import de.cj.jchess.service.ChessConfigurationService;
import de.cj.jchess.service.ChessMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class ChessMoveServiceImpl implements ChessMoveService {

    @Autowired
    private ChessConfigurationService chessConfigurationService;

    @Autowired
    private ChessConfigurationSupport chessConfigurationSupport;

    @Override
    public boolean executeMove(ChessConfiguration configuration, ChessPosition from, ChessPosition to) {
        ChessPiece pieceToMove = chessConfigurationService.findPieceAtPosition(configuration, from);
        if (pieceToMove == null || pieceToMove.getPieceColor() != configuration.getTurnColor()) {
            return false;
        }
        boolean toPositionAvailable = pieceToMove.getAvailablePositions()
                .contains(to);
        if (!toPositionAvailable) {
            return false;
        }
        Optional.ofNullable(chessConfigurationService.findPieceAtPosition(configuration, to))
                .ifPresent(removePiece(configuration));
        pieceToMove.setPosition(to);
        chessConfigurationSupport.updateAvailablePositions(configuration);

        ChessPiece kingToCheck = retrieveOpponentPieces(configuration).stream()
                .filter(p -> p.getPieceType() == ChessPieceType.KING)
                .findAny()
                .orElse(null);

        if (kingToCheck != null) {
            boolean kingUnderAttack = isPositionUnderAttack(configuration, kingToCheck.getPosition(), configuration.getTurnColor());

            if (configuration.getTurnColor() == ChessPieceColor.WHITE) {
                configuration.setCheckBlack(kingUnderAttack);
            } else {
                configuration.setCheckWhite(kingUnderAttack);
            }
        }

        ChessPieceColor oppositeColor = configuration.getTurnColor() == ChessPieceColor.BLACK ? ChessPieceColor.WHITE : ChessPieceColor.BLACK;
        configuration.setTurnColor(oppositeColor);

        return true;
    }

    private Set<ChessPiece> retrieveOpponentPieces(ChessConfiguration configuration) {
        return configuration.getTurnColor() == ChessPieceColor.WHITE ? configuration.getBlackPieces() : configuration.getWhitePieces();
    }

    private boolean isPositionUnderAttack(ChessConfiguration configuration, ChessPosition position, ChessPieceColor attackingColor) {
        Set<ChessPiece> attackers = attackingColor == ChessPieceColor.WHITE ? configuration.getWhitePieces() : configuration.getBlackPieces();
        return attackers.stream()
                .map(ChessPiece::getAvailablePositions)
                .flatMap(Collection::stream)
                .anyMatch(p -> p == position);
    }

    private Consumer<ChessPiece> removePiece(ChessConfiguration configuration) {
        return p -> {
            if (p.getPieceColor() == ChessPieceColor.BLACK) {
                configuration.getBlackPieces()
                        .remove(p);
            } else {
                configuration.getWhitePieces()
                        .remove(p);
            }
        };
    }

    @Override
    public void executeBestMove(ChessConfiguration configuration) {
        // TODO: 28.01.21
    }
}
