package chess.piece;

import chess.piece.coordinate.Coordinate;

public class Queen extends Piece {
    public Queen(Team team, Coordinate coordinate) {
        super(team, coordinate);
    }
    
    @Override
    public SymbolMatcher symbol() {
        return SymbolMatcher.QUEEN;
    }
    
    @Override
    public boolean isMovable(Piece targetPiece) {
        int rowDistance = calculateRowOrColumnDistance(targetPiece, ROW_INDEX);
        int columnDistance = calculateRowOrColumnDistance(targetPiece, COLUMN_INDEX);
    
        return isQueenMovable(targetPiece, rowDistance, columnDistance);
    }
    
    private boolean isQueenMovable(Piece targetPiece, int rowDistance, int columnDistance) {
        if (isOutOfMovementRadius(rowDistance, columnDistance)) {
            return false;
        }
    
        return isDifferentTeam(targetPiece);
    }
    
    private boolean isOutOfMovementRadius(int rowDistance, int columnDistance) {
        return isBothZero(rowDistance, columnDistance) ||
                (isDifferentRowColumn(rowDistance, columnDistance) && (isZeroNotExist(rowDistance, columnDistance)));
    }
    
    private boolean isDifferentRowColumn(int rowDistance, int columnDistance) {
        return rowDistance != columnDistance;
    }
    
    private boolean isZeroNotExist(int rowDistance, int columnDistance) {
        return rowDistance != 0 && columnDistance != 0;
    }
}