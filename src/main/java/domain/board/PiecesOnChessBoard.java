package domain.board;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.PieceWrapper;
import java.util.List;

public class PiecesOnChessBoard {
    private final List<PieceWrapper> pieces;

    public PiecesOnChessBoard(List<Piece> pieces) {
        this.pieces = pieces.stream().map(PieceWrapper::new).toList();
    }

    public Team whichTeam(Position position) {
        return pieces.stream().filter(piece -> piece.isOn(position))
                .findFirst()
                .map(PieceWrapper::getTeam)
                .orElse(Team.NONE);
    }

    public PieceType whichPieceType(Position position) {
        return pieces.stream().filter(piece -> piece.isOn(position))
                .findFirst()
                .map(PieceWrapper::getPieceType)
                .orElse(PieceType.NONE);
    }
}
