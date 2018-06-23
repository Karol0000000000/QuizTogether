package com.projekt.pw.mechanics;

import java.sql.*;

public abstract class Query {

    private String sql;
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultset;
    private String url = "jdbc:hsqldb:res:/pytania.db";

    protected abstract void process() throws SQLException;

    protected void connectToDataBase() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbcDriver");

        connection = DriverManager.getConnection(url, "SA", "");
    }

    protected void executeQuery() throws SQLException {
        statement = connection.createStatement();
        resultset = statement.executeQuery(sql);
    }

    protected void close() throws SQLException {
        resultset.close();
        statement.execute("SHUTDOWN");
        statement.close();
        connection.close();
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
