package chess.view;

import chess.controller.mapper.PieceMapper;
import chess.controller.mapper.TeamMapper;
import chess.domain.Result;
import chess.domain.board.FileCoordinate;
import chess.domain.board.Position;
import chess.domain.board.RankCoordinate;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;

import java.util.Map;

public class OutputView {

    private static final String ERROR_START_MESSAGE = "[ERROR]: ";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void printStart() {
        String startMessage = "> 체스 게임을 시작합니다." + LINE_SEPARATOR
                + "> 게임 시작 : start" + LINE_SEPARATOR
                + "> 게임 종료 : end" + LINE_SEPARATOR
                + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3";

        System.out.println(startMessage);
    }

    public void printBoard(Map<Position, Piece> boards) {
        for (RankCoordinate rankCoordinate : RankCoordinate.getSortedRankCoordinates()) {
            printRank(boards, rankCoordinate);
            System.out.println();
        }
    }

    private void printRank(Map<Position, Piece> boards, RankCoordinate rankCoordinate) {
        for (FileCoordinate fileCoordinate : FileCoordinate.getSortedFileCoordinates()) {
            Position position = new Position(fileCoordinate, rankCoordinate);
            Piece piece = boards.get(position);
            String message = PieceMapper.from(piece.getPieceType()).getPieceViewBy(piece.getTeam());
            System.out.print(message);
        }
    }

    public void printError(String message) {
        System.out.println(ERROR_START_MESSAGE + message);
    }

    public void printResult(Result result) {
        Map<Team, Double> scores = result.getScore();
        for (Team team : scores.keySet()) {
            System.out.println(TeamMapper.from(team).getTeamView() + ": " + scores.get(team) + "점");
        }
        System.out.println("승리: " + TeamMapper.from(result.getWinner()).getTeamView());
    }

    public void printFinish() {
        System.out.println("게임이 종료되었습니다.");
    }
}
