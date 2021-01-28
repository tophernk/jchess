package de.cj.jchess.service;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPieceColor;
import de.cj.jchess.entity.ChessPosition;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ChessEvaluationService {

    public int evaluate(ChessConfiguration configuration);

    public int evaluateToDepth(ChessConfiguration configuration, int depth);

    public boolean isMoveAvailable(ChessConfiguration configuration, ChessPieceColor color);

    public ChessPieceColor retrieveTurnColor(ChessConfiguration configuration);

    public Pair<ChessPosition, ChessPosition> determineBestMove(ChessConfiguration configuration, int depth);

    public List<String> determineAvailablePosition(ChessConfiguration configuration);
}
