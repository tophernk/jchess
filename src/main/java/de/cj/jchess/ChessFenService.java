package de.cj.jchess;

public interface ChessFenService {

    public ChessConfiguration importFen(String fen);
    public String exportFen(ChessConfiguration configuration);
}
