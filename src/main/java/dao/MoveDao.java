package dao;

import domain.command.MoveCommand;
import java.sql.Connection;
import java.util.List;

public interface MoveDao {
    void add(Connection connection, MoveCommand moveCommand);

    List<MoveCommand> findAllMoves(Connection connection);

    void deleteAll(Connection connection);
}
