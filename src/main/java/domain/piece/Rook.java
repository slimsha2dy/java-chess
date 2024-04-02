package domain.piece;

import domain.PieceMoveResult;
import domain.Position;
import domain.Team;
import domain.board.PiecesOnChessBoard;
import java.util.Optional;

public final class Rook extends AbstractStraightMovePiece {
    public Rook(Position position, Team team) {
        super(position, team);
    }

    @Override
    public Optional<PieceMoveResult> tryMoveAssumeAlone(Position targetPosition,
                                                        PiecesOnChessBoard piecesOnChessBoard) {
        Position nowPosition = getPosition();
        if (nowPosition.isOtherColumn(targetPosition) && nowPosition.isOtherRow(targetPosition)) {
            return Optional.of(PieceMoveResult.FAILURE);
        }
        return Optional.empty();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }
}
