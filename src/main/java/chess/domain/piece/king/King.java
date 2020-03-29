package chess.domain.piece.king;

import java.util.Map;

import chess.domain.Team;
import chess.domain.piece.MovingStrategy;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

public class King extends Piece {
	private static final MovingStrategy STRATEGY = new KingStrategy();
	public static final String WHITE_KING = "\u2654";
	public static final String BLACK_KING = "\u265a";

	public King(Team team, Position position) {
		super(team, position);
	}

	public static King of(Team team, Position position) {
		return new King(team, position);
	}

	@Override
	public String toString() {
		if (team.equals(Team.WHITE)) {
			return WHITE_KING;
		}
		return BLACK_KING;
	}

	@Override
	public Piece move(Position from, Position to, Map<Position, Team> dto) {
		STRATEGY.validateMove(from, to, dto);
		this.position = to;
		return this;
	}
}