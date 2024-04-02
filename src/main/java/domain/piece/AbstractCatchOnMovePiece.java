package domain.piece;

import domain.PieceMoveResult;
import domain.Position;
import domain.Team;
import domain.board.PiecesOnChessBoard;
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
            return PieceMoveResult.FAILURE;
        }
        if (isOtherTeam(targetPosition, piecesOnChessBoard)) {
            return getCatchKingOrOther(targetPosition, piecesOnChessBoard);
        }
        return PieceMoveResult.SUCCESS;
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
            return PieceMoveResult.CATCH_KING;
        }
        return PieceMoveResult.CATCH;
    }

    private boolean isKing(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        PieceType pieceType = piecesOnChessBoard.whichPieceType(targetPosition);
        return pieceType.equals(PieceType.KING);
    }
}
