package chess.domain.piece;

import chess.exception.NoSuchPermittedChessPieceException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PiecesTest {

    @Test
    void findPieceByPosition() {
        Pieces pieces = new Pieces(Arrays.asList(
                Piece.createPawn(Color.BLACK, 0, 0)
        ));

        assertThat(pieces.findPieceByPosition(Color.BLACK, new Position(0, 0)))
                .isEqualTo(Piece.createPawn(Color.BLACK, 0, 0));

        assertThatThrownBy(() -> pieces.findPieceByPosition(Color.WHITE, new Position(0, 0)))
                .isExactlyInstanceOf(NoSuchPermittedChessPieceException.class);

        assertThatThrownBy(() -> pieces.findPieceByPosition(Color.BLACK, new Position(0, 1)))
                .isExactlyInstanceOf(NoSuchPermittedChessPieceException.class);
    }

    @Test
    void catchPiece() {
        Pieces pieces = new Pieces(Arrays.asList(
                Piece.createPawn(Color.WHITE, 0, 0),
                Piece.createPawn(Color.BLACK, 0, 0)
        ));

        pieces.catchPiece(Color.WHITE);
        assertThat(pieces.getPieces()).containsExactly(Piece.createPawn(Color.WHITE, 0, 0));
    }

    @Test
    void getBlackScore() {
        Pieces pieces = new Pieces(Arrays.asList(
                Piece.createPawn(Color.BLACK, 0, 1),
                Piece.createPawn(Color.BLACK, 1, 1),
                Piece.createPawn(Color.BLACK, 0, 2),
                Piece.createQueen(Color.BLACK, 2, 1),
                Piece.createRook(Color.BLACK, 3, 1),
                Piece.createKing(Color.BLACK, 4, 1),
                Piece.createKnight(Color.BLACK, 5, 1),
                Piece.createBishop(Color.BLACK, 6, 1)
        ));

        assertThat(pieces.getBlackScore()).isEqualTo(21.0);
    }

    @Test
    void getWhiteScore() {
        Pieces pieces = new Pieces(Arrays.asList(
                Piece.createPawn(Color.WHITE, 0, 1),
                Piece.createPawn(Color.WHITE, 1, 1),
                Piece.createPawn(Color.WHITE, 0, 2),
                Piece.createQueen(Color.WHITE, 2, 1),
                Piece.createRook(Color.WHITE, 3, 1),
                Piece.createKing(Color.WHITE, 4, 1),
                Piece.createKnight(Color.WHITE, 5, 1),
                Piece.createBishop(Color.WHITE, 6, 1)
        ));

        assertThat(pieces.getWhiteScore()).isEqualTo(21.0);
    }

    @Test
    void isKingExist() {
        Pieces pieces = new Pieces(Arrays.asList(
                Piece.createKing(Color.WHITE, 0, 0),
                Piece.createKing(Color.BLACK, 0, 0)
        ));

        assertThat(pieces.isKingsExist()).isTrue();

        pieces = new Pieces(Collections.singletonList(
                Piece.createKing(Color.WHITE, 0, 0)
        ));

        assertThat(pieces.isKingsExist()).isFalse();
    }
}