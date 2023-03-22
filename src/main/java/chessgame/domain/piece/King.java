package chessgame.domain.piece;

import chessgame.domain.Team;
import chessgame.domain.point.Point;

public class King implements Piece {
    private static final String ORIGINAL_NAME = "k";
    private static final int DISTANCE = 1;

    private final Team team;

    private King(Team team) {
        this.team = team;
    }

    public static King from(Team team) {
        return new King(team);
    }

    private boolean isKingMove(Point source, Point target) {
        if (source.isHorizontal(target) && Math.abs(source.fileDistance(target)) == DISTANCE) {
            return true;
        }
        if (source.isVertical(target) && Math.abs(source.rankDistance(target)) == DISTANCE) {
            return true;
        }
        return source.isDiagonal(target) && Math.abs(source.fileDistance(target)) == DISTANCE
                && Math.abs(source.rankDistance(target)) == DISTANCE;
    }

    @Override
    public boolean isMovable(Point source, Point target, boolean hasTarget) {
        return isKingMove(source, target);
    }

    @Override
    public Team team() {
        return team;
    }

    @Override
    public boolean isKnight() {
        return false;
    }

    @Override
    public String toString() {
        return ORIGINAL_NAME;
    }
}