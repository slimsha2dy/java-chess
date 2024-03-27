package domain;

public enum PieceMoveResult {
    SUCCESS, FAILURE, CATCH, CATCH_KING;

    public boolean isMoved() {
        return !this.equals(FAILURE);
    }
}
