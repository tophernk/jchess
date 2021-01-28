package de.cj.jchess.service.impl;

import de.cj.jchess.entity.ChessConfiguration;
import de.cj.jchess.entity.ChessPiece;
import de.cj.jchess.entity.ChessPieceColor;
import de.cj.jchess.entity.ChessPiecePosition;
import de.cj.jchess.service.ChessConfigurationService;
import de.cj.jchess.service.ChessPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChessPrintServiceImpl implements ChessPrintService {

    private static final String BOTTOM_BORDER = "        A       B       C       D       E       F       G       H    \n";

    @Autowired
    private ChessConfigurationService chessConfigurationService;

    @Override
    public String printBoard(ChessConfiguration configuration) {
        StringBuilder board = new StringBuilder();

        for (int rank = 8; rank > 0; rank--) {
            board.append(printSolidLine());
            board.append(printIntermediateLine());
            board.append(printLeftBorder(rank));
            for (char file = 'A'; file <= 'H'; file++) {
                ChessPiece piece = chessConfigurationService.findPieceAtPosition(configuration, ChessPiecePosition.retrievePosition(file, rank));
                board.append(printPiece(piece));
            }
            board.append("|\n");
            board.append(printIntermediateLine());
        }
        board.append(printSolidLine());
        board.append(BOTTOM_BORDER);

        return board.toString();
    }

    private String printLeftBorder(int rank) {
        return "  " + rank + " ";
    }

    private String printPiece(ChessPiece piece) {
        StringBuilder print = new StringBuilder();
        print.append("|   ");
        print.append(formatPieceCharacter(piece));
        print.append("   ");

        return print.toString();
    }

    private char formatPieceCharacter(ChessPiece piece) {
        if (piece == null) {
            return ' ';
        }

        Character pieceCharacter = piece.getPieceType()
                .getPrint();

        return piece.getPieceColor() == ChessPieceColor.WHITE ? pieceCharacter : Character.toLowerCase(pieceCharacter);
    }

    private String printSolidLine() {
        StringBuilder line = new StringBuilder();
        line.append("    ");
        for (int i = 0; i < 8 * 8; i++) {
            line.append("-");
        }
        line.append("-\n");

        return line.toString();
    }

    private String printIntermediateLine() {
        StringBuilder line = new StringBuilder();
        line.append("    ");
        for (int i = 0; i < 8; i++) {
            line.append("|       ");
        }
        line.append("|\n");

        return line.toString();
    }
}
