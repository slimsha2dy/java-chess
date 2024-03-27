package domain.piece;

import static domain.PieceMoveResult.CATCH;
import static domain.PieceMoveResult.CATCH_KING;
import static domain.PieceMoveResult.FAILURE;
import static domain.PieceMoveResult.SUCCESS;
import static domain.piece.PieceType.KING;

import domain.PieceMoveResult;
import domain.PiecesOnChessBoard;
import domain.Position;
import domain.Team;
import java.util.Optional;

abstract class AbstractCatchOnMovePiece extends Piece {
    AbstractCatchOnMovePiece(Position position, Team team) {
        super(position, team);
    }

    protected abstract Optional<PieceMoveResult> tryMoveAssumeAloneAndCheckRoute(Position targetPosition,
                                                                                 PiecesOnChessBoard piecesOnChessBoard);

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
            return getCatchKingOrOther(targetPosition, piecesOnChessBoard);
        }
        return SUCCESS;
    }

    private boolean isMyTeam(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        Team targetTeam = piecesOnChessBoard.whichTeam(targetPosition);
        return targetTeam.equals(getTeam());
    }

    private boolean isOtherTeam(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        Team targetTeam = piecesOnChessBoard.whichTeam(targetPosition);
        return targetTeam.equals(getTeam().otherTeam());
    }

    private PieceMoveResult getCatchKingOrOther(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        if (isKing(targetPosition, piecesOnChessBoard)) {
            return CATCH_KING;
        }
        return CATCH;
    }

    private boolean isKing(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        PieceType pieceType = piecesOnChessBoard.whichPieceType(targetPosition);
        return pieceType.equals(KING);
    }
}
