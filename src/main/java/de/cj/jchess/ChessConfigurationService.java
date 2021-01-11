package de.cj.jchess;

public interface ChessConfigurationService {

    public ChessConfiguration createConfiguration(String fen);
    public String exportFen(ChessConfiguration configuration);
}
