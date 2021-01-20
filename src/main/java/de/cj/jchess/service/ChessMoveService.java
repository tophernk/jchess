package de.cj.jchess.service;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessMove;

public interface ChessMoveService {

    public int executeMove(ChessConfiguration configuration, ChessMove move);
    public void executeBestMove(ChessConfiguration configuration);
}
