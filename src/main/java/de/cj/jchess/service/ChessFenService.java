package de.cj.jchess.service;

import de.cj.jchess.entity.ChessConfiguration;

public interface ChessFenService {

    public ChessConfiguration importFen(String fen);

    public String exportFen(ChessConfiguration configuration);

    public ChessConfiguration findConfigurationById(String id);
}
