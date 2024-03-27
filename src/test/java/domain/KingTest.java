package domain;

import static domain.PieceMoveResult.CATCH;
import static domain.PieceMoveResult.CATCH_KING;
import static domain.PieceMoveResult.FAILURE;
import static domain.PieceMoveResult.SUCCESS;
import static domain.Position.A4;
import static domain.Position.C3;
import static domain.Position.C4;
import static domain.Position.C5;
import static domain.Position.D3;
import static domain.Position.D4;
import static domain.Position.D5;
import static domain.Position.E3;
import static domain.Position.E4;
import static domain.Position.E5;
import static domain.Team.BLACK;
import static domain.Team.WHITE;

import domain.piece.King;
import domain.piece.Pawn;
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

class KingTest {
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
                Arguments.of(C5), Arguments.of(D5), Arguments.of(E5),
                Arguments.of(C4), Arguments.of(E4),
                Arguments.of(C3), Arguments.of(D3), Arguments.of(E3)
        );
    }

    @ParameterizedTest
    @MethodSource("moveSuccessParameters")
    @DisplayName("킹의 이동 규칙대로 이동이 가능한지 검증")
    void moveSuccess(Position targetPosition) {
        King king = new King(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of());
        Assertions.assertThat(king.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(SUCCESS);
    }

    @ParameterizedTest
    @MethodSource("moveFailureParameters")
    @DisplayName("킹의 이동 규칙을 위반한 이동이 불가능한지 검증")
    void moveFailure(Position targetPosition) {
        King king = new King(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of());
        Assertions.assertThat(king.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(FAILURE);
    }

    @ParameterizedTest
    @MethodSource("moveSuccessParameters")
    @DisplayName("킹의 목적지에 같은 팀 말이 있는 경우 이동이 불가능한지 검증")
    void moveFailureCauseTargetIsSameTeam(Position targetPosition) {
        King king = new King(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new Pawn(targetPosition, WHITE)));
        Assertions.assertThat(king.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(FAILURE);
    }

    @ParameterizedTest
    @MethodSource("moveSuccessParameters")
    @DisplayName("킹의 목적지에 다른 팀 말이 있는 경우 이동이 가능한지 검증")
    void moveSuccessWhenTargetIsOtherTeam(Position targetPosition) {
        King king = new King(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new Pawn(targetPosition, BLACK)));
        Assertions.assertThat(king.move(targetPosition, piecesOnChessBoard))
                .isEqualTo(CATCH);
    }

    @Test
    @DisplayName("킹의 목적지에 다른 팀 킹이 있는 경우 CATCH_KING을 반환하는지 검증")
    void catchKing() {
        King king = new King(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new King(D5, BLACK)));
        Assertions.assertThat(king.move(D5, piecesOnChessBoard))
                .isEqualTo(CATCH_KING);
    }

    @Test
    @DisplayName("킹의 목적지에 같은 팀 킹이 있는 경우 이동이 불가능한지 검증")
    void moveFailureByOurKing() {
        King king = new King(D4, WHITE);
        PiecesOnChessBoard piecesOnChessBoard = new PiecesOnChessBoard(List.of(new King(D5, WHITE)));
        Assertions.assertThat(king.move(A4, piecesOnChessBoard))
                .isEqualTo(FAILURE);
    }
}
