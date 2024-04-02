package dao;

import domain.Position;
import domain.command.MoveCommand;
import java.sql.Connection;
import java.util.List;

public interface MoveDao {
    Connection getConnection();

    void add(Position from, Position to);

    List<MoveCommand> findAll();

    void deleteAll();
}
