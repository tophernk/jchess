package de.cj.jchess.service.impl;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPiece;
import de.cj.jchess.entity.ChessPieceColor;
import de.cj.jchess.entity.ChessPosition;
import de.cj.jchess.service.ChessConfigurationService;
import de.cj.jchess.service.ChessMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        if (pieceToMove == null) {
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
        return true;
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
