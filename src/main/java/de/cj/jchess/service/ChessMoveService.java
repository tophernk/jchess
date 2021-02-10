package de.cj.jchess.service;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPosition;

public interface ChessMoveService {

    public boolean executeMove(ChessConfiguration configuration, ChessPosition from, ChessPosition to);

    public void executeBestMove(ChessConfiguration configuration);
}
