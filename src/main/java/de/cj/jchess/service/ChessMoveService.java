package de.cj.jchess.service;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPosition;

public interface ChessMoveService {

    public int executeMove(ChessConfiguration configuration, ChessPosition from, ChessPosition to);

    public void executeBestMove(ChessConfiguration configuration);
}
