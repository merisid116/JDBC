package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection conn = Util.getInstance().getConnection();
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)";
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (PreparedStatement pstm = conn.prepareStatement(SQL_CREATE)) {
            conn.setAutoCommit(false);
            pstm.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement pstm = conn.prepareStatement("DROP TABLE IF EXISTS users")) {
            conn.setAutoCommit(false);
            pstm.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pstm = conn.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")) {
            conn.setAutoCommit(false);
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            conn.setAutoCommit(false);
            pstm.setLong(1, id);
            pstm.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM users")) {
            conn.setAutoCommit(false);
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement pstm = conn.prepareStatement("TRUNCATE TABLE users")) {
            conn.setAutoCommit(false);
            pstm.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
