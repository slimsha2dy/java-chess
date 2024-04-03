package dao;

import domain.Position;
import domain.command.MoveCommand;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MoveDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }

    public void add(Position from, Position to) {
        final String query = "INSERT INTO move (source, destination) VALUES(?, ?)";
        try (final PreparedStatement preparedStateMent = getConnection().prepareStatement(query)) {
            preparedStateMent.setString(1, from.name());
            preparedStateMent.setString(2, to.name());
            preparedStateMent.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MoveCommand> findAll() {
        final String query = "SELECT * FROM move";
        List<MoveCommand> moveCommands = new ArrayList<>();
        try (final PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                moveCommands.add(new MoveCommand(
                        resultSet.getString("source"),
                        resultSet.getString("destination")
                ));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return moveCommands;
    }

    public void deleteAll() {
        final String query = "TRUNCATE TABLE move";
        try (final Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
