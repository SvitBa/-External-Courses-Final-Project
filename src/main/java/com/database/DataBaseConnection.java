package com.database;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DataBaseConnection {

    final static Logger logger = Logger.getLogger(DataBaseConnection.class);

    private static DataBaseConnection instance = null;
    private static String CONNECTION_URL = "jdbc:mysql://localhost:3306/carrentalapp?user=root&password=root";

    private DataBaseConnection() {
    }

    public static DataBaseConnection getInstance(){
        if (instance == null)
            instance = new DataBaseConnection();
        return instance;
    }



    public java.sql.Connection getConnection(){
        Context ctx;
        java.sql.Connection connection = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/carrentalapp");
            connection= ds.getConnection();

        } catch (NamingException | SQLException e) {
            logger.error("DataBaseConnection from pool not established");
            System.out.println("DataBaseConnection from pool not established");
        }
        return connection;
    }

    public static void rollback(java.sql.Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("DataBaseConnection from pool not rollbacked");
            System.out.println("DataBaseConnection from pool not rollbacked");
        }
    }

    public static void close(java.sql.Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("DataBaseConnection from pool not closed");
            System.out.println("DataBaseConnection from pool not closed");
        }
    }
}
