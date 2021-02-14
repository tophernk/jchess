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
import java.util.stream.Stream;

@Service
public class ChessMoveServiceImpl implements ChessMoveService {

    @Autowired
    private ChessConfigurationService chessConfigurationService;

    @Autowired
    private ChessConfigurationSupport chessConfigurationSupport;

    @Override
    public boolean executeMove(ChessConfiguration configuration, ChessPosition from, ChessPosition to) {
        ChessPiece pieceToMove = chessConfigurationService.findPieceAtPosition(configuration, from);
        ChessPieceColor activeColor = configuration.getTurnColor();

        if (!movePreconditionsFulfilled(to, pieceToMove, activeColor)) {
            return false;
        }

        chessConfigurationSupport.movePiece(configuration, to, pieceToMove);
        chessConfigurationSupport.updateAvailablePositions(configuration);

        ChessPieceColor oppositeColor = activeColor == ChessPieceColor.BLACK ? ChessPieceColor.WHITE : ChessPieceColor.BLACK;
        configuration.setTurnColor(oppositeColor);

        return true;
    }

    private boolean movePreconditionsFulfilled(ChessPosition to, ChessPiece pieceToMove, ChessPieceColor activeColor) {
        if (pieceToMove == null || pieceToMove.getPieceColor() != activeColor) {
            return false;
        }
        return pieceToMove.getAvailablePositions()
                .contains(to);
    }

    @Override
    public void executeBestMove(ChessConfiguration configuration) {
        // TODO: 28.01.21
    }
}
