package dao;

import domain.command.MoveCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MoveDaoImpl implements MoveDao {
    @Override
    public void add(Connection connection, MoveCommand moveCommand) {
        final String query = "INSERT INTO move (`from`, `to`) VALUES(?, ?)";
        try (final PreparedStatement preparedStateMent = connection.prepareStatement(query)) {
            preparedStateMent.setString(1, moveCommand.getFrom().name());
            preparedStateMent.setString(2, moveCommand.getTo().name());
            preparedStateMent.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MoveCommand> findAllMoves(Connection connection) {
        final String query = "SELECT * FROM move";
        List<MoveCommand> moveCommands = new ArrayList<>();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                moveCommands.add(new MoveCommand(
                        resultSet.getString("from"),
                        resultSet.getString("to")
                ));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return moveCommands;
    }

    @Override
    public void deleteAll(Connection connection) {
        final String query = "TRUNCATE TABLE move";
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
