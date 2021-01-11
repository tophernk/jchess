package de.cj.jchess;

import org.springframework.stereotype.Service;

@Service
public class ChessConfigurationServiceImpl implements ChessConfigurationService {
    @Override
    public ChessConfiguration createConfiguration(String fen) {
        return new ChessConfiguration(fen);
    }

    @Override
    public String exportFen(ChessConfiguration configuration) {
        return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 0";
    }
}
