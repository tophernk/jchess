package de.cj.jchess;

public interface ChessMoveService {

    public int executeMove(ChessConfiguration configuration, ChessMove move);
    public void executeBestMove(ChessConfiguration configuration);
}
