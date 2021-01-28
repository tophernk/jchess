package de.cj.jchess.service;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPiece;
import de.cj.jchess.entity.ChessPosition;

public interface ChessConfigurationService {

    ChessPiece findPieceAtPosition(ChessConfiguration configuration, ChessPosition position);
}
