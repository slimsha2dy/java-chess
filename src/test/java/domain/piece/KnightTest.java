package domain.piece;

import static domain.PieceMoveResult.CATCH;
import static domain.PieceMoveResult.CATCH_KING;
import static domain.PieceMoveResult.FAILURE;
import static domain.PieceMoveResult.SUCCESS;
import static domain.Position.A4;
import static domain.Position.B3;
import static domain.Position.B5;
import static domain.Position.C2;
import static domain.Position.C6;
import static domain.Position.D4;
import static domain.Position.E2;
import static domain.Position.E6;
import static domain.Position.F3;
import static domain.Position.F5;
import static domain.Team.BLACK;
import static domain.Team.WHITE;

import domain.PiecesOnChessBoard;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KnightTest {
    public static Stream<Arguments> moveFailureParameters() {
        Set<Position> successParameters = moveSuccessParameters().map(Arguments::get)
                .map(objects -> (Position) objects[0])
                .collect(Collectors.toSet());
        return Arrays.stream(Position.values())
                .filter(position -> !successParameters.contains(position))
                .map(Arguments::of);
    }

    public static Stream<Arguments> moveSuccessParameters() {
        return Stream.of(
                Arguments.of(C6), Arguments.of(E6),
                Arguments.of(B5), Arguments.of(F5),
                Arguments.of(B3), Arguments.of(F3),
                Arguments.of(C2), Arguments.of(E2)
        );
    }

    @ParameterizedTest
    @MethodSource("moveSuccessParameters")
    @DisplayName("나이트의 이동 규칙대로 이동이 가능한지 검증")
    void moveSuccess(Position targetPosition) {
        Knight knight = new Knight(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of());
        Assertions.assertThat(knight.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(SUCCESS);
    }

    @ParameterizedTest
    @MethodSource("moveFailureParameters")
    @DisplayName("나이트의 이동 규칙을 위반한 이동이 불가능한지 검증")
    void moveFailure(Position targetPosition) {
        Knight knight = new Knight(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of());
        Assertions.assertThat(knight.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(FAILURE);
    }

    @ParameterizedTest
    @MethodSource("moveSuccessParameters")
    @DisplayName("나이트의 목적지에 같은 팀 말이 있는 경우 이동이 불가능한지 검증")
    void moveFailureCauseTargetIsSameTeam(Position targetPosition) {
        Knight knight = new Knight(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new Pawn(targetPosition, WHITE)));
        Assertions.assertThat(knight.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(FAILURE);
    }

    @ParameterizedTest
    @MethodSource("moveSuccessParameters")
    @DisplayName("나이트의 목적지에 다른 팀 말이 있는 경우 이동이 가능한지 검증")
    void moveSuccessWhenTargetIsOtherTeam(Position targetPosition) {
        Knight knight = new Knight(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new Pawn(targetPosition, BLACK)));
        Assertions.assertThat(knight.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(CATCH);
    }

    @Test
    @DisplayName("나이트의 목적지에 다른 팀 킹이 있는 경우 CATCH_KING을 반환하는지 검증")
    void catchKing() {
        Knight knight = new Knight(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new King(C6, BLACK)));
        Assertions.assertThat(knight.move(C6, piecesOnChessBoard))
                .isEqualTo(CATCH_KING);
    }

    @Test
    @DisplayName("나이트의 목적지에 같은 팀 킹이 있는 경우 이동이 불가능한지 검증")
    void moveFailureByOurKing() {
        Knight knight = new Knight(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new King(C6, WHITE)));
        Assertions.assertThat(knight.move(A4, piecesOnChessBoard))
                .isEqualTo(FAILURE);
    }
}
