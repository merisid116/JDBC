package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection conn = null;
    private static Util instance = null;

    private final String URL = "jdbc:mysql://localhost:3307/newbase";
    private final String USERNAME = "root";
    private final String PASSWORD = "1357";

    private Util() {
        try {
            if (null == conn || conn.isClosed()) {
                conn = DriverManager
                        .getConnection(URL, USERNAME,PASSWORD);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    public static Util getInstance() {
        if (null == instance) {
            instance = new Util();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }
}
