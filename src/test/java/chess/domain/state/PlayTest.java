package chess.domain.state;

import chess.domain.Turn;
import chess.domain.piece.Color;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlayTest {

    @Test
    @DisplayName("play -> finish")
    void playToFinish() {
        Play play = Play.from(new Turn());

        assertThat(play.end()).isInstanceOf(Finish.class);
    }

    @Test
    @DisplayName("end 메서드 실행시 Finish 반환")
    void checkEnd() {
        Play play = Play.from(new Turn());

        assertThat(play.end()).isInstanceOf(Finish.class);
    }

    @Test
    @DisplayName("Finish 상태 확인")
    void checkFinish() {
        Play play = Play.from(new Turn());

        assertThat(play.isFinished()).isFalse();
    }

    @Test
    @DisplayName("move하고 Play를 반환하는지 확인")
    void checkMove() {
        Play play = Play.from(new Turn());

        assertThat(play.move(Position.of(6, 0), Position.of(5, 0)).getClass())
                .isEqualTo(Play.class);
    }

    @Test
    @DisplayName("Play 상태에서 start를 하는 경우")
    void checkStartException() {
        assertThatThrownBy(() -> Play.from(new Turn()).start())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("점수 확인")
    void checkScore() {
        Play play = Play.from(new Turn());

        assertThat(play.computeScore(Color.WHITE).getScore()).isEqualTo(38);
    }

    @Test
    @DisplayName("현재 턴이 맞는지 확인")
    void checkTurn() {
        Play play = Play.from(new Turn());

        assertThat(play.isRightTurn("white")).isTrue();
    }

    @Test
    @DisplayName("현재 턴을 String으로 확인")
    void checkTurnString() {
        Play play = Play.from(new Turn());

        assertThat(play.turn()).isEqualTo("white");
    }

    @Test
    @DisplayName("running 중인지 확인")
    void isRunning() {
        Play play = Play.from(new Turn());

        assertThat(play.isRunning()).isTrue();
    }
}