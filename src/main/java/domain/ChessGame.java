package domain;

import static domain.GameStatus.CONTINUE;
import static domain.GameStatus.END;
import static domain.GameStatus.RETRY;
import static domain.PieceMoveResult.CATCH_KING;
import static domain.PieceMoveResult.FAILURE;
import static domain.Team.BLACK;
import static domain.Team.NONE;
import static domain.Team.WHITE;

import domain.piece.Piece;
import domain.piece.PieceWrapper;
import java.util.List;

public class ChessGame {
    private final ChessBoard chessBoard;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
    }

    public GameStatus move(Position from, Position to) {
        PieceMoveResult moveResult = chessBoard.move(from, to);
        if (moveResult.equals(FAILURE)) {
            return RETRY;
        }
        if (moveResult.equals(CATCH_KING)) {
            return END;
        }
        return CONTINUE;
    }

    public List<Piece> getPiecesOnBoard() {
        return chessBoard.getPiecesOnBoard();
    }

    public Team getWinner() {
        return chessBoard.getWinner();
    }

    public double getTeamScore(Team team) {
        List<PieceWrapper> pieceWrappers = getPiecesOnBoard().stream()
                .map(PieceWrapper::new)
                .toList();
        ScoreCalculator scoreCalculator = new ScoreCalculator(pieceWrappers);
        return scoreCalculator.getScore(team);
    }

    public Team getHigher() {
        double whiteScore = getTeamScore(WHITE);
        double blackScore = getTeamScore(BLACK);
        if (whiteScore > blackScore) {
            return WHITE;
        }
        if (whiteScore < blackScore) {
            return BLACK;
        }
        return NONE;
    }
}
