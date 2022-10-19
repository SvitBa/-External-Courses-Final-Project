package com.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    final static Logger logger = Logger.getLogger(DBConnection.class);

    private static DBConnection instance = null;
    private static String CONNECTION_URL = "jdbc:mysql://localhost:3306/carrentalapp?user=root&password=root";

    private DBConnection () {
    }

    public static DBConnection getInstance(){
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }



    public Connection getConnection(){
        Context ctx;
        Connection connection = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/carrentalapp");
            connection= ds.getConnection();
//            connection = DriverManager.getConnection(CONNECTION_URL);
        } catch (NamingException | SQLException e) {
//            logger.error("Connection from pool not established");
            System.out.println("Connection from pool not established");
        }
        return connection;
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
//            logger.error("Connection from pool not rollbacked");
            System.out.println("Connection from pool not rollbacked");
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
//            logger.error("Connection from pool not closed");
            System.out.println("Connection from pool not closed");
        }
    }
}
