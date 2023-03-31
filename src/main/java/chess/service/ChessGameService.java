package chess.service;

import chess.dao.ChessGameDao;
import chess.domain.board.ChessBoard;
import chess.domain.game.ChessGame;
import chess.dto.MoveDto;

import java.util.List;

public class ChessGameService {
    private final ChessGame chessGame;
    private final ChessGameDao chessGameDao;
    
    public ChessGameService(ChessGame chessGame, ChessGameDao chessGameDao) {
        this.chessGame = chessGame;
        this.chessGameDao = chessGameDao;
    }
    
    public void newChessGame() {
        chessGame.newChessGame();
    }
    
    public void initChessGame() {
        chessGameDao.deleteAll();
    }
    
    public ChessBoard chessBoard() {
        return chessGame.chessBoard();
    }
    
    public void move(List<String> inputCommand) {
        chessGame.move(inputCommand);
        chessGameDao.save(new MoveDto(inputCommand));
    }
    
    public boolean isChessBoardNotInitialized() {
        return chessGame.isChessBoardNotInitialized();
    }
    
    public boolean isKingDied() {
        return chessGame.isKingDied();
    }
    
    public void load() {
        for (MoveDto moveDto : chessGameDao.selectAllMovement()) {
            String sourceCoordinate = moveDto.sourceCoordinate();
            String destinationCoordinate = moveDto.destinationCoordinate();
            
            chessGame.move(List.of(sourceCoordinate, destinationCoordinate));
        }
    }
}
