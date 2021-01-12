package de.cj.jchess;

import java.util.List;

public interface ChessEvaluationService {

    public int evaluate(ChessConfiguration configuration);
    public int evaluateToDepth(ChessConfiguration configuration, int depth);
    public boolean isMoveAvailable(ChessConfiguration configuration, ChessPieceColor color);
    public ChessPieceColor retrieveTurnColor(ChessConfiguration configuration);
    public ChessMove determineBestMove(ChessConfiguration configuration, int depth);
    public List<String> determineAvailablePosition(ChessConfiguration configuration);
}
