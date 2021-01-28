package de.cj.jchess.service.impl;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPiece;
import de.cj.jchess.entity.ChessPiecePosition;
import de.cj.jchess.service.ChessConfigurationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Stream;

@Service
public class ChessConfigurationServiceImpl implements ChessConfigurationService {

    @Override
    public ChessPiece findPieceAtPosition(ChessConfiguration configuration, ChessPiecePosition position) {
        return Stream.of(configuration.getWhitePieces(), configuration.getBlackPieces())
                .flatMap(Collection::stream)
                .filter(piece -> piece.getPosition() == position)
                .findAny()
                .orElse(null);
    }
}
