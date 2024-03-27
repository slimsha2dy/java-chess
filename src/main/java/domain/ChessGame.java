package domain;

import static domain.GameStatus.CONTINUE;
import static domain.GameStatus.END;
import static domain.GameStatus.RETRY;
import static domain.PieceMoveResult.CATCH_KING;
import static domain.PieceMoveResult.FAILURE;

import domain.piece.Piece;
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
}
