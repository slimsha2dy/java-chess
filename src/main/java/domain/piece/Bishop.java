package domain.piece;

import domain.PieceMoveResult;
import domain.Position;
import domain.Team;
import domain.board.PiecesOnChessBoard;
import java.util.Optional;

public final class Bishop extends AbstractStraightMovePiece {
    public Bishop(Position position, Team team) {
        super(position, team);
    }

    @Override
    public Optional<PieceMoveResult> tryMoveAssumeAlone(Position targetPosition,
                                                        PiecesOnChessBoard piecesOnChessBoard) {
        Position nowPosition = getPosition();
        int absRowDistance = Math.abs(nowPosition.rowDistance(targetPosition));
        int absColDistance = Math.abs(nowPosition.columnDistance(targetPosition));
        if (absColDistance != absRowDistance) {
            return Optional.of(PieceMoveResult.FAILURE);
        }
        return Optional.empty();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }
}
