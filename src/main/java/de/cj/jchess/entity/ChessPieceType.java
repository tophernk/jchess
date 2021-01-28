package de.cj.jchess.entity;

public enum ChessPieceType {
    PAWN('P'),
    KNIGHT('N'),
    BISHOP('B'),
    ROOK('R'),
    KING('K'),
    QUEEN('Q');

    private final Character print;

    ChessPieceType(Character print) {
        this.print = print;
    }

    public Character getPrint() {
        return print;
    }
}
