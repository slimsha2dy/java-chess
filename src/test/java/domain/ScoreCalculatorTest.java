package domain;

import static domain.Position.A1;
import static domain.Position.A7;
import static domain.Position.B1;
import static domain.Position.B6;
import static domain.Position.B8;
import static domain.Position.C1;
import static domain.Position.C7;
import static domain.Position.C8;
import static domain.Position.D1;
import static domain.Position.D7;
import static domain.Position.E1;
import static domain.Position.E2;
import static domain.Position.E3;
import static domain.Position.E4;
import static domain.Position.E6;
import static domain.Position.E8;
import static domain.Position.F1;
import static domain.Position.F2;
import static domain.Position.F3;
import static domain.Position.F4;
import static domain.Position.F8;
import static domain.Position.G1;
import static domain.Position.G2;
import static domain.Position.G4;
import static domain.Position.G8;
import static domain.Position.H3;
import static domain.Team.BLACK;
import static domain.Team.WHITE;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceWrapper;
import domain.piece.Queen;
import domain.piece.Rook;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ScoreCalculatorTest {
    public static Stream<Arguments> onePieceScoreParameters() {
        return Stream.of(
                Arguments.of(new Queen(E4, WHITE), 9D),
                Arguments.of(new Rook(E4, WHITE), 5D),
                Arguments.of(new Bishop(E4, WHITE), 3D),
                Arguments.of(new Knight(E4, WHITE), 2.5D),
                Arguments.of(new Pawn(E4, WHITE), 1D),
                Arguments.of(new King(E4, WHITE), 0D)
        );
    }

    @ParameterizedTest
    @MethodSource("onePieceScoreParameters")
    @DisplayName("하나의 기물이 있을 경우 검증")
    void onePieceScore(Piece piece, double expectedScore) {
        List<PieceWrapper> pieceWrappers = List.of(new PieceWrapper(piece));
        ScoreCalculator scoreCalculator = new ScoreCalculator(pieceWrappers);
        Assertions.assertThat(scoreCalculator.getScore(WHITE))
                .isEqualTo(expectedScore);
    }

    @Test
    @DisplayName("한 팀의 여러 기물이 있을 경우 검증")
    void manyPieceScore() {
        List<Piece> pieces = List.of(
                new Queen(A1, WHITE),
                new Rook(B1, WHITE),
                new Bishop(C1, WHITE),
                new Knight(D1, WHITE),
                new Pawn(E1, WHITE),
                new King(F1, WHITE)
        );
        ScoreCalculator scoreCalculator = new ScoreCalculator(pieces.stream()
                .map(PieceWrapper::new)
                .toList());
        Assertions.assertThat(scoreCalculator.getScore(WHITE))
                .isEqualTo(20.5D);
    }

    @Test
    @DisplayName("여러 폰이 같은 세로줄에 있는 경우 검증")
    void manyPawnsOnSameRow() {
        List<Piece> pieces = List.of(
                new Pawn(E1, WHITE),
                new Pawn(E2, WHITE),
                new Pawn(E3, WHITE)
        );
        ScoreCalculator scoreCalculator = new ScoreCalculator(pieces.stream()
                .map(PieceWrapper::new)
                .toList());
        Assertions.assertThat(scoreCalculator.getScore(WHITE))
                .isEqualTo(1.5D);
    }

    @Test
    @DisplayName("여러 폰이 같은 세로줄이 아닌 경우 검증")
    void manyPawnsOnOtherRows() {
        List<Piece> pieces = List.of(
                new Pawn(E1, WHITE),
                new Pawn(F1, WHITE),
                new Pawn(G1, WHITE)
        );
        ScoreCalculator scoreCalculator = new ScoreCalculator(pieces.stream()
                .map(PieceWrapper::new)
                .toList());
        Assertions.assertThat(scoreCalculator.getScore(WHITE))
                .isEqualTo(3D);
    }

    @Test
    @DisplayName("두 팀의 여러 폰 중 같은 세로줄이 포함된 경우 검증")
    void somePawnsOnOtherRows() {
        List<Piece> pieces = List.of(
                new Pawn(F1, WHITE),
                new Pawn(G1, WHITE),
                new Pawn(G2, WHITE),
                new Pawn(E8, BLACK),
                new Pawn(F8, BLACK),
                new Pawn(G8, BLACK)
        );
        ScoreCalculator scoreCalculator = new ScoreCalculator(pieces.stream()
                .map(PieceWrapper::new)
                .toList());
        assertAll(
                () -> Assertions.assertThat(scoreCalculator.getScore(BLACK))
                        .isEqualTo(3D),
                () -> Assertions.assertThat(scoreCalculator.getScore(WHITE))
                        .isEqualTo(2D)
        );
    }

    @Test
    @DisplayName("두 팀의 여러 기물이 있는 경우")
    void manyPieceOfTwoTeams() {
        List<Piece> pieces = List.of(
                new King(B8, BLACK), new Rook(C8, BLACK),
                new Pawn(A7, BLACK), new Pawn(C7, BLACK),
                new Bishop(D7, BLACK), new Pawn(B6, BLACK),
                new Queen(E6, BLACK),
                new Knight(F4, WHITE), new Queen(G4, WHITE),
                new Pawn(F3, WHITE), new Pawn(H3, WHITE),
                new Pawn(F2, WHITE), new Pawn(G2, WHITE),
                new Rook(E1, WHITE), new King(F1, WHITE)
        );
        ScoreCalculator scoreCalculator = new ScoreCalculator(pieces.stream()
                .map(PieceWrapper::new)
                .toList());
        assertAll(
                () -> Assertions.assertThat(scoreCalculator.getScore(BLACK))
                        .isEqualTo(20D),
                () -> Assertions.assertThat(scoreCalculator.getScore(WHITE))
                        .isEqualTo(19.5D)
        );
    }
}
