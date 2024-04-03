package controller;

import domain.ChessGame;
import domain.GameStatus;
import domain.Position;
import domain.Team;
import domain.command.Command;
import domain.command.ContinueCommand;
import domain.command.EndCommand;
import domain.command.MoveCommand;
import domain.command.StartCommand;
import domain.command.StatusCommand;
import domain.piece.Piece;
import domain.piece.PieceWrapper;
import java.util.List;
import java.util.stream.Collectors;
import view.InputView;
import view.OutputView;

public class ChessController {
    public void run() {
        OutputView.printGuide();
        Command firstCommand = InputView.readStartPhase();
        if (isEndCommand(firstCommand)) {
            return;
        }
        ChessGame chessGame = initChessGame(firstCommand);
        List<PieceWrapper> piecesOnBoard = wrapPieces(chessGame.getPiecesOnBoard());
        OutputView.printChessBoard(piecesOnBoard);

        Command command = InputView.readWhilePlaying();
        while (isGamePlayable(chessGame, command)) {
            command = playTurnAndGetCommand(chessGame, command);
        }
    }

    private Command playTurnAndGetCommand(ChessGame chessGame, Command command) {
        if (command.equals(StatusCommand.STATUS_COMMAND)) {
            OutputView.printScore(
                    chessGame.getTeamScore(Team.WHITE),
                    chessGame.getTeamScore(Team.BLACK),
                    chessGame.getHigher()
            );
            return InputView.readWhilePlaying();
        }
        GameStatus gameStatus = playMove(command, chessGame);
        if (gameStatus.equals(GameStatus.RETRY)) {
            OutputView.printReInputGuide();
        }
        if (!gameStatus.equals(GameStatus.END)) {
            return InputView.readWhilePlaying();
        }
        return command;
    }

    private boolean isGamePlayable(ChessGame chessGame, Command command) {
        if (command == EndCommand.END_COMMAND) {
            return false;
        }
        if (chessGame.isGameOver()) {
            OutputView.printEndGame(chessGame.getWinner());
            return false;
        }
        return true;
    }

    private boolean isEndCommand(Command command) {
        return command.equals(EndCommand.END_COMMAND);
    }

    private ChessGame initChessGame(Command command) {
        ChessGame chessGame = new ChessGame();
        if (isStartCommand(command)) {
            chessGame.initBoard();
        }
        if (isContinueCommand(command)) {
            chessGame.loadMoves();
        }
        return chessGame;
    }

    private boolean isStartCommand(Command command) {
        return command.equals(StartCommand.START_COMMAND);
    }

    private boolean isContinueCommand(Command command) {
        return command.equals(ContinueCommand.CONTINUE_COMMAND);
    }

    private List<PieceWrapper> wrapPieces(List<Piece> piecesOnBoard) {
        return piecesOnBoard.stream()
                .map(PieceWrapper::new)
                .collect(Collectors.toList());
    }

    private GameStatus playMove(Command command, ChessGame chessGame) {
        MoveCommand moveCommand = (MoveCommand) command;
        Position from = moveCommand.getFrom();
        Position to = moveCommand.getTo();
        GameStatus gameStatus = chessGame.move(from, to);
        List<PieceWrapper> piecesOnBoard = wrapPieces(chessGame.getPiecesOnBoard());
        OutputView.printChessBoard(piecesOnBoard);
        return gameStatus;
    }
}
