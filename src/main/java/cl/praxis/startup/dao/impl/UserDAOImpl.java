package cl.praxis.startup.dao.impl;

import cl.praxis.startup.connection.DBConnection;
import cl.praxis.startup.dao.UserDAO;
import cl.praxis.startup.model.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String SELECT_ALL_USERS = "SELECT id, email, created_at, nick, name, password, weight, updated_at FROM users";
    private static final String SELECT_USER_BY_ID = "SELECT id, email, created_at, nick, name, password, weight, updated_at FROM users WHERE id = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO users (email, created_at, nick, name, password, weight, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    @Override
    public UserDTO selectUser(int id) {
        UserDTO user = null;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                Date createdAt = resultSet.getDate("created_at");
                String nick = resultSet.getString("nick");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                int weight = resultSet.getInt("weight");
                Date updatedAt = resultSet.getDate("updated_at");
                user = new UserDTO(id, email, createdAt, nick, name, password, weight, updatedAt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<UserDTO> selectAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                Date createdAt = resultSet.getDate("created_at");
                String nick = resultSet.getString("nick");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                int weight = resultSet.getInt("weight");
                Date updatedAt = resultSet.getDate("updated_at");
                users.add(new UserDTO(id, email, createdAt, nick, name, password, weight, updatedAt));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public UserDTO insertUser(UserDTO user) {
        UserDTO newUser = null;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            java.sql.Date sqlCreatedAt = new java.sql.Date(user.getCreatedAt().getTime());
            java.sql.Date sqlUpdatedAt = new java.sql.Date(user.getUpdatedAt().getTime());

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setDate(2, sqlCreatedAt);
            preparedStatement.setString(3, user.getNick());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getWeight());
            preparedStatement.setDate(7, sqlUpdatedAt);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                newUser = new UserDTO(
                        id,
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getNick(),
                        user.getName(),
                        user.getPassword(),
                        user.getWeight(),
                        user.getUpdatedAt()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newUser;
    }
}