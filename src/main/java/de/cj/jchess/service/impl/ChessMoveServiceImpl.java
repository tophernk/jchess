package de.cj.jchess.service.impl;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPosition;
import de.cj.jchess.service.ChessMoveService;
import org.springframework.stereotype.Service;

@Service
public class ChessMoveServiceImpl implements ChessMoveService {

    @Override
    public int executeMove(ChessConfiguration configuration, ChessPosition from, ChessPosition to) {
        return 0;
    }

    @Override
    public void executeBestMove(ChessConfiguration configuration) {
        // TODO: 28.01.21
    }
}
