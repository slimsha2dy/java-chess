package domain;

import static domain.PieceMoveResult.*;

abstract class AbstractPiece extends Piece {
	private final Team team;
	private Position position;

	AbstractPiece(Position position, Team team) {
		this.position = position;
		this.team = team;
	}

	@Override
	public final PieceMoveResult move(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
		if (targetPosition == position) {
			return FAILURE;
		}
		PieceMoveResult pieceMoveResult = tryMove(targetPosition, piecesOnChessBoard);
		if (!pieceMoveResult.equals(FAILURE)) {
			position = targetPosition;
		}
		return pieceMoveResult;
	}

	abstract PieceMoveResult tryMove(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard);

	@Override
	public boolean isOn(Position position) {
		return this.position.equals(position);
	}

	@Override
	public Team getTeam() {
		return team;
	}

	@Override
	public int getColumn() {
		return position.getColumn();
	}

	@Override
	public int getRow() {
		return position.getRow();
	}

	protected Position getPosition() {
		return position;
	}
}