package domain.piece;

public enum PieceType {
    BISHOP(3D),
    KING(0D),
    KNIGHT(2.5D),
    PAWN(1D),
    QUEEN(9D),
    ROOK(5D),
    NONE(0D);

    private final double score;

    PieceType(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}
