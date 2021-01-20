package de.cj.jchess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChessFenServiceImpl implements ChessFenService {

    private static final Logger logger = LoggerFactory.getLogger(ChessFenServiceImpl.class);
    private static final char FEN_SEPARATOR = ' ';
    private static final char FEN_SEPARATOR_RANK = '/';

    @Autowired
    private MongoChessConfigurationRepository repository;

    @Override
    public ChessConfiguration importFen(String fen) {
        int x = 0;
        int rank = 0;
        int separatorIndex = 0;

        MongoChessConfiguration result = new MongoChessConfiguration();

        for (int i = 0; i < fen.length(); i++) {
            char value = fen.charAt(i);
            if (value == FEN_SEPARATOR_RANK) {
                x = 0;
                rank++;
                continue;
            }
            if (value == FEN_SEPARATOR) {
                separatorIndex++;
                continue;
            }
            if (isPiece(value, x, rank, separatorIndex)) {
                ChessPiece piece = createPiece(value);
                result.getPieces().put(determinePiecePositionBasedOnFenPosition(rank, x), piece);
                x++;
                continue;
            }
            if (separatorIndex == 1) {
                // turn color
                result.setTurnColor(parseTurnColor(value));
            }
            if (separatorIndex == 2) {
                // castles
                parseCastles(value, result);
            }
            if (separatorIndex == 3) {
                // en passant
                result.setEnPassant(result.getEnPassant() + value);
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
        return repository.insert(result);
    }

    @Override
    public String exportFen(ChessConfiguration configuration) {
        return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 0";
    }

    @Override
    public ChessConfiguration findConfigurationById(String id) {
        return repository.findChessConfigurationById(id);
    }

    private ChessPiece createPiece(char value) {
        ChessPieceType pieceType = determinePieceType(value);
        ChessPieceColor pieceColor = Character.isUpperCase(value) ? ChessPieceColor.WHITE : ChessPieceColor.BLACK;
        return new JpaChessPiece(pieceType, pieceColor);
    }

    private boolean isPiece(char fenValue, int x, int rank, int separatorIndex) {
        return !Character.isDigit(fenValue) && separatorIndex == 0 && rank < 8 && x < 8;
    }

    private ChessPieceColor parseTurnColor(char value) {
        return value == 'b' ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;
    }

    private void parseCastles(char value, ChessConfiguration result) {
        switch (value) {
            case 'K':
                result.setShortCastlesWhite(true);
                break;
            case 'Q':
                result.setLongCastlesWhite(true);
                break;
            case 'k':
                result.setShortCastlesBlack(true);
                break;
            case 'q':
                result.setLongCastlesBlack(true);
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

    public String determinePiecePositionBasedOnFenPosition(int rank, int x) {
        // TODO: 12.01.21
        return "" + rank + x;
    }
}
