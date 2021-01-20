package de.cj.jchess.service.impl;

import de.cj.jchess.dao.MongoChessConfigurationRepository;
import de.cj.jchess.entity.*;
import de.cj.jchess.service.ChessFenService;
import de.cj.jchess.service.mapper.ChessConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChessFenServiceImpl implements ChessFenService {

    private static final Logger logger = LoggerFactory.getLogger(ChessFenServiceImpl.class);
    private static final char FEN_SEPARATOR = ' ';
    private static final char FEN_SEPARATOR_RANK = '/';

    @Autowired
    private ChessConfigurationMapper chessConfigurationMapper;

    @Autowired
    private MongoChessConfigurationRepository repository;

    @Override
    public ChessConfiguration importFen(String fen) {
        int x = 0;
        int rank = 8;
        int separatorIndex = 0;

        ChessConfiguration.ChessConfigurationBuilder configurationBuilder = ChessConfiguration.builder();
        Set<ChessPiece> whitePieces = new HashSet<>();
        Set<ChessPiece> blackPieces = new HashSet<>();
        StringBuilder enPassant = new StringBuilder();

        for (int i = 0; i < fen.length(); i++) {
            char value = fen.charAt(i);
            if (value == FEN_SEPARATOR_RANK) {
                x = 0;
                rank--;
                continue;
            }
            if (value == FEN_SEPARATOR) {
                separatorIndex++;
                continue;
            }
            if (isPiece(value, x, rank, separatorIndex)) {
                ChessPiece piece = createPiece(value, rank, x);
                if (piece.getPieceColor() == ChessPieceColor.BLACK) {
                    blackPieces.add(piece);
                } else {
                    whitePieces.add(piece);
                }
                x++;
                continue;
            }
            if (separatorIndex == 1) {
                // turn color
                configurationBuilder.turnColor(parseTurnColor(value));
            }
            if (separatorIndex == 2) {
                // castles
                parseCastles(value, configurationBuilder);
            }
            if (separatorIndex == 3) {
                // en passant
                enPassant.append(value);
                continue;
            }
            if (separatorIndex == 4) {
                // meta: half move #
                continue;
            }
            if (separatorIndex == 5) {
                // meta: full move #
                continue;
            }
            logger.error("FEN import: undefined value");
        }
        configurationBuilder.whitePieces(whitePieces);
        configurationBuilder.blackPieces(blackPieces);
        if (!enPassant.toString().equals("-")) {
            configurationBuilder.enPassant(ChessPiecePosition.valueOf(enPassant.toString().toUpperCase()));
        }

        ChessConfiguration result = configurationBuilder.build();

        MongoChessConfiguration mongoChessConfiguration = chessConfigurationMapper.chessConfigurationToMongo(result);
        MongoChessConfiguration insert = repository.insert(mongoChessConfiguration);
        result.setId(insert.getId());

        return result;
    }

    @Override
    public String exportFen(ChessConfiguration configuration) {
        return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 0";
    }

    @Override
    public ChessConfiguration findConfigurationById(String id) {
        MongoChessConfiguration chessConfigurationById = repository.findChessConfigurationById(id);
        if (chessConfigurationById.getEnPassant().isEmpty()) {
            chessConfigurationById.setEnPassant(null);
        }
        return chessConfigurationMapper.mongoToChessConfiguration(chessConfigurationById);
    }

    private ChessPiece createPiece(char value, int rank, int x) {
        return ChessPiece.builder()
                .pieceType(determinePieceType(value))
                .pieceColor(Character.isUpperCase(value) ? ChessPieceColor.WHITE : ChessPieceColor.BLACK)
                .position(determinePiecePositionBasedOnFenPosition(rank, x))
                .build();
    }

    private boolean isPiece(char fenValue, int x, int rank, int separatorIndex) {
        return !Character.isDigit(fenValue) && separatorIndex == 0 && rank > 0 && x < 8;
    }

    private ChessPieceColor parseTurnColor(char value) {
        return value == 'b' ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;
    }

    private void parseCastles(char value, ChessConfiguration.ChessConfigurationBuilder builder) {
        switch (value) {
            case 'K':
                builder.shortCastlesWhite(true);
                break;
            case 'Q':
                builder.longCastlesWhite(true);
                break;
            case 'k':
                builder.shortCastlesBlack(true);
                break;
            case 'q':
                builder.longCastlesBlack(true);
                break;
            default:
                break;
        }
    }

    public ChessPieceType determinePieceType(char fenValue) {
        String stringValue = String.valueOf(fenValue);
        if ("p".equalsIgnoreCase(stringValue)) {
            return ChessPieceType.PAWN;
        } else if ("n".equalsIgnoreCase(stringValue)) {
            return ChessPieceType.KNIGHT;
        } else if ("b".equalsIgnoreCase(stringValue)) {
            return ChessPieceType.BISHOP;
        } else if ("r".equalsIgnoreCase(stringValue)) {
            return ChessPieceType.ROOK;
        } else if ("k".equalsIgnoreCase(stringValue)) {
            return ChessPieceType.KING;
        } else if ("q".equalsIgnoreCase(stringValue)) {
            return ChessPieceType.QUEEN;
        }
        throw new IllegalArgumentException("ChessPieceType: invalid FEN value");
    }

    public ChessPiecePosition determinePiecePositionBasedOnFenPosition(int rank, int x) {
        return ChessPiecePosition.retrievePosition((char) (x + 65), rank);
    }
}
