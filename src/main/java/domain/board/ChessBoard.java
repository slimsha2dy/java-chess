package domain.board;

import static domain.PieceMoveResult.CATCH;
import static domain.PieceMoveResult.CATCH_KING;
import static domain.PieceMoveResult.FAILURE;
import static domain.Team.WHITE;
import static domain.board.InitialPieces.INITIAL_PIECES;

import domain.PieceMoveResult;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessBoard {
    private final List<Piece> piecesOnBoard;
    private Team currentTeam = WHITE;

    public ChessBoard() {
        this(INITIAL_PIECES);
    }

    ChessBoard(List<Piece> pieces) {
        piecesOnBoard = new ArrayList<>(pieces);
    }

    public PieceMoveResult move(Position from, Position to) {
        if (isEmptyPosition(from) || isOtherTeamTurn(from)) {
            return FAILURE;
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
                .findFirst().orElseThrow();
    }

    private void removePieceIfCaught(Position position, PieceMoveResult moveResult) {
        if (moveResult.equals(CATCH) || moveResult.equals(CATCH_KING)) {
            removeDeadPiece(position);
        }
    }

    private void removeDeadPiece(Position position) {
        Piece needToRemovePiece = piecesOnBoard.stream()
                .filter(piece -> piece.isOn(position))
                .filter(piece -> {
                    Team pieceTeam = piece.getTeam();
                    Team otherTeam = currentTeam.otherTeam();
                    return pieceTeam.equals(otherTeam);
                })
                .findFirst().orElseThrow();
        piecesOnBoard.remove(needToRemovePiece);
    }

    private void changeCurrentTeamIfNotFail(PieceMoveResult moveResult) {
        if (!moveResult.equals(FAILURE)) {
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