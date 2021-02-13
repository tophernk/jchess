package de.cj.jchess.service.impl;

import de.cj.jchess.entity.*;
import de.cj.jchess.service.ChessConfigurationService;
import de.cj.jchess.service.ChessMoveService;
import de.cj.jchess.service.mapper.ChessConfigurationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChessMoveServiceImpl implements ChessMoveService {

    @Autowired
    private ChessConfigurationService chessConfigurationService;

    @Autowired
    private ChessConfigurationSupport chessConfigurationSupport;

    @Autowired
    private ChessConfigurationMapper chessConfigurationMapper;

    @Override
    public boolean executeMove(ChessConfiguration configuration, ChessPosition from, ChessPosition to) {
        ChessConfiguration backup = configuration.toBuilder()
                .build();
        ChessPiece pieceToMove = chessConfigurationService.findPieceAtPosition(configuration, from);
        ChessPieceColor activeColor = configuration.getTurnColor();

        if (!movePreconditionsFulfilled(to, pieceToMove, activeColor)) {
            return false;
        }

        performMove(configuration, to, pieceToMove);
        chessConfigurationSupport.updateAvailablePositions(configuration);

        Set<ChessPiece> kings = retrieveKings(configuration);
        for (ChessPiece king : kings) {
            boolean kingInCheck = isPieceUnderAttack(configuration, king);
            if (king.getPieceColor() == ChessPieceColor.WHITE) {
                configuration.setCheckWhite(kingInCheck);
            } else {
                configuration.setCheckBlack(kingInCheck);
            }
        }

        if ((activeColor == ChessPieceColor.WHITE && configuration.isCheckWhite()) || (activeColor == ChessPieceColor.BLACK && configuration.isCheckBlack())) {
            chessConfigurationMapper.copy(backup, configuration);
            return false;
        }

        ChessPieceColor oppositeColor = activeColor == ChessPieceColor.BLACK ? ChessPieceColor.WHITE : ChessPieceColor.BLACK;
        configuration.setTurnColor(oppositeColor);

        return true;
    }

    private void performMove(ChessConfiguration configuration, ChessPosition to, ChessPiece pieceToMove) {
        Optional.ofNullable(chessConfigurationService.findPieceAtPosition(configuration, to))
                .ifPresent(removePiece(configuration));
        pieceToMove.setPosition(to);
    }

    private boolean movePreconditionsFulfilled(ChessPosition to, ChessPiece pieceToMove, ChessPieceColor activeColor) {
        if (pieceToMove == null || pieceToMove.getPieceColor() != activeColor) {
            return false;
        }
        boolean toPositionAvailable = pieceToMove.getAvailablePositions()
                .contains(to);
        return toPositionAvailable;
    }

    private Set<ChessPiece> retrieveKings(ChessConfiguration configuration) {
        return Stream.of(configuration.getBlackPieces(), configuration.getWhitePieces())
                .flatMap(Collection::stream)
                .filter(p -> p.getPieceType() == ChessPieceType.KING)
                .collect(Collectors.toSet());
    }

    private boolean isPieceUnderAttack(ChessConfiguration configuration, ChessPiece piece) {
        Set<ChessPiece> attackers = piece.getPieceColor() == ChessPieceColor.WHITE ? configuration.getBlackPieces() : configuration.getWhitePieces();
        return attackers.stream()
                .map(ChessPiece::getAvailablePositions)
                .flatMap(Collection::stream)
                .anyMatch(p -> p == piece.getPosition());
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
