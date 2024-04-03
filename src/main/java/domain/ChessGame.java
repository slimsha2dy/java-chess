package domain;

import dao.MoveDao;
import domain.board.ChessBoard;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.PieceWrapper;
import java.util.List;

public class ChessGame {
    private final ChessBoard chessBoard;
    private final MoveDao moveDao = new MoveDao();

    public ChessGame() {
        this.chessBoard = new ChessBoard();
    }

    public GameStatus move(Position from, Position to) {
        PieceMoveResult moveResult = chessBoard.move(from, to);
        if (moveResult.equals(PieceMoveResult.FAILURE)) {
            return GameStatus.RETRY;
        }
        if (moveResult.equals(PieceMoveResult.CATCH_KING)) {
            moveDao.deleteAll();
            return GameStatus.END;
        }
        moveDao.add(from, to);
        return GameStatus.PROGRESS;
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
        double whiteScore = getTeamScore(Team.WHITE);
        double blackScore = getTeamScore(Team.BLACK);
        if (whiteScore > blackScore) {
            return Team.WHITE;
        }
        if (whiteScore < blackScore) {
            return Team.BLACK;
        }
        return Team.NONE;
    }

    public void loadMoves() {
        moveDao.findAll()
                .forEach(moveCommand -> move(moveCommand.getFrom(), moveCommand.getTo()));
    }

    public void initBoard() {
        moveDao.deleteAll();
    }

    public boolean isGameOver() {
        List<Piece> piecesOnBoard = chessBoard.getPiecesOnBoard();
        long kingNumber = piecesOnBoard.stream()
                .filter(piece -> piece.getPieceType() == PieceType.KING)
                .count();
        return kingNumber == 1;
    }
}
