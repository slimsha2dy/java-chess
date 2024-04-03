package domain.board;

import domain.PieceMoveResult;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessBoard {
    private final List<Piece> piecesOnBoard;
    private Team currentTeam = Team.WHITE;

    public ChessBoard() {
        this(InitialPieces.INITIAL_PIECES);
    }

    ChessBoard(List<Piece> pieces) {
        piecesOnBoard = new ArrayList<>(pieces);
    }

    public PieceMoveResult move(Position from, Position to) {
        if (isEmptyPosition(from) || isOtherTeamTurn(from)) {
            return PieceMoveResult.FAILURE;
        }
        Piece piece = findPiece(from);
        PieceMoveResult moveResult = piece.move(to, new PiecesOnChessBoard(piecesOnBoard));
        removePieceIfCaught(to, moveResult);
        changeCurrentTeamIfNotFail(moveResult);
        return moveResult;
    }

    private boolean isEmptyPosition(Position position) {
        return piecesOnBoard.stream()
                .noneMatch(piece -> piece.isOn(position));
    }

    private boolean isOtherTeamTurn(Position position) {
        Piece piece = findPiece(position);
        Team otherTeam = currentTeam.otherTeam();
        Team pieceTeam = piece.getTeam();
        return pieceTeam.equals(otherTeam);
    }

    private Piece findPiece(Position position) {
        return piecesOnBoard.stream()
                .filter(piece -> piece.isOn(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다"));
    }

    private void removePieceIfCaught(Position position, PieceMoveResult moveResult) {
        if (moveResult.equals(PieceMoveResult.CATCH) || moveResult.equals(PieceMoveResult.CATCH_KING)) {
            removeDeadPiece(position);
        }
    }

    private void removeDeadPiece(Position position) {
        piecesOnBoard.removeIf(
                piece -> piece.isOn(position) && piece.getTeam() == currentTeam.otherTeam()
        );
    }

    private void changeCurrentTeamIfNotFail(PieceMoveResult moveResult) {
        if (!moveResult.equals(PieceMoveResult.FAILURE)) {
            currentTeam = currentTeam.otherTeam();
        }
    }

    public List<Piece> getPiecesOnBoard() {
        return Collections.unmodifiableList(piecesOnBoard);
    }

    public Team getWinner() {
        return this.currentTeam.otherTeam();
    }
}
