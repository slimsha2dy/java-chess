package domain;

import static domain.PieceMoveResult.CATCH;
import static domain.PieceMoveResult.FAILURE;
import static domain.PieceMoveResult.SUCCESS;

import java.util.Optional;

abstract class AbstractCatchOnMovePiece extends Piece {
    AbstractCatchOnMovePiece(Position position, Team team) {
        super(position, team);
    }

    @Override
    public final PieceMoveResult tryMove(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        Optional<PieceMoveResult> pieceMoveResult = tryMoveAssumeAloneAndCheckRoute(targetPosition, piecesOnChessBoard);
        if (pieceMoveResult.isPresent()) {
            return pieceMoveResult.get();
        }
        if (isMyTeam(targetPosition, piecesOnChessBoard)) {
            return FAILURE;
        }
        if (isOtherTeam(targetPosition, piecesOnChessBoard)) {
            return CATCH;
        }
        return SUCCESS;
    }

    protected abstract Optional<PieceMoveResult> tryMoveAssumeAloneAndCheckRoute(Position targetPosition,
                                                                                 PiecesOnChessBoard piecesOnChessBoard);

    private boolean isMyTeam(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        Optional<Team> targetTeam = piecesOnChessBoard.whichTeam(targetPosition);
        return targetTeam.isPresent() && targetTeam.get().equals(getTeam());
    }

    private boolean isOtherTeam(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        Optional<Team> targetTeam = piecesOnChessBoard.whichTeam(targetPosition);
        return targetTeam.isPresent() && targetTeam.get().equals(getTeam().otherTeam());
    }
}
