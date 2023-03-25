package chess.piece;

import chess.piece.coordinate.Coordinate;

import java.util.List;

public class Pawn extends Piece {
    
    public Pawn(Team team, Coordinate coordinate) {
        super(team, coordinate);
    }
    
    @Override
    public SymbolMatcher symbol() {
        return SymbolMatcher.PAWN;
    }
    
    @Override
    public boolean isMovable(Piece targetPiece) {
        List<Integer> subtractCoordinate = subtractCoordinate(targetPiece);
        int subtractedColumn = subtractCoordinate.get(COLUMN_INDEX);
        int subtractedRow = subtractCoordinate.get(ROW_INDEX);
        return isPawnMovable(targetPiece, subtractedRow, subtractedColumn);
    }
    
    private boolean isPawnMovable(Piece targetPiece, int subtractedRow, int subtractedColumn) {
        if (isOutOfMovementRadius(targetPiece,subtractedRow, subtractedColumn)) {
            return false;
        }
        
        return isDifferentTeam(targetPiece);
    }
    
    private boolean isOutOfMovementRadius(Piece targetPiece, int subtractedRow, int subtractedColumn) {
        if (isSameTeam(Team.WHITE)) {
            return isWhitePawnImmovable(targetPiece, subtractedRow, subtractedColumn);
        }
        
        if (isSameTeam(Team.BLACK)) {
            return isBlackPawnMovable(targetPiece, subtractedRow, subtractedColumn);
        }
        return true;
    }
    
    private boolean isWhitePawnImmovable(Piece targetPiece, int subtractedRow, int subtractedColumn) {
        if (isWhitePawnMoveForwardTwoSpace(subtractedRow, subtractedColumn)){
            return isPawnImmovableForwardTwoSpace(targetPiece);
        }
        if (isWhitePawnMoveDiagonalOneSpace(subtractedRow, subtractedColumn)) {
            return isPawnImmovableDiagonalOneSpace(targetPiece, Team.BLACK);
        }
        if (isWhitePawnMoveForwardOneSpace(subtractedRow, subtractedColumn)) {
            return isPawnImmovableForwardOneSpace(targetPiece);
        }
        return true;
    }
    
    private boolean isWhitePawnMoveForwardTwoSpace(int subtractedRow, int subtractedColumn) {
        return subtractedRow == 2 && subtractedColumn == 0;
    }
    
    private boolean isWhitePawnMoveDiagonalOneSpace(int subtractedRow, int subtractedColumn) {
        return subtractedRow == 1 && Math.abs(subtractedColumn) == 1;
    }
    
    private boolean isWhitePawnMoveForwardOneSpace(int subtractedRow, int subtractedColumn) {
        return subtractedRow == 1 && subtractedColumn == 0;
    }
    
    private boolean isPawnImmovableForwardTwoSpace(Piece targetPiece) {
        return !coordinate().isPawnStartRow() || !targetPiece.isSameTeam(Team.EMPTY);
    }
    
    private boolean isPawnImmovableDiagonalOneSpace(Piece targetPiece, Team team) {
        return !targetPiece.isSameTeam(team);
    }
    
    private boolean isPawnImmovableForwardOneSpace(Piece targetPiece) {
        return !targetPiece.isSameTeam(Team.EMPTY);
    }
    
    private boolean isBlackPawnMovable(Piece targetPiece, int subtractedRow, int subtractedColumn) {
        if (isBlackPawnMoveForwardTwoSpace(subtractedRow, subtractedColumn)) {
            return isPawnImmovableForwardTwoSpace(targetPiece);
        }
        if (isBlackPawnMoveDiagonalOneSpace(subtractedRow, subtractedColumn)) {
            return isPawnImmovableDiagonalOneSpace(targetPiece, Team.WHITE);
        }
        if (isBlackPawnMoveForwardOneSpace(subtractedRow, subtractedColumn)) {
            return isPawnImmovableForwardOneSpace(targetPiece);
        }
        return true;
    }
    
    private boolean isBlackPawnMoveForwardTwoSpace(int subtractedRow, int subtractedColumn) {
        return subtractedRow == -2 && subtractedColumn == 0;
    }
    
    private boolean isBlackPawnMoveDiagonalOneSpace(int subtractedRow, int subtractedColumn) {
        return subtractedRow == -1 && Math.abs(subtractedColumn) == 1;
    }
    
    private boolean isBlackPawnMoveForwardOneSpace(int subtractedRow, int subtractedColumn) {
        return subtractedRow == -1 && subtractedColumn == 0;
    }
}