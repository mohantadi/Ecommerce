package com.example.ecommerce;
import java.sql.*;



public class dbconnection {
    private final String dburl = "jdbc:mysql://localhost:3306/ecommerce";
    private final String userName = "root";
    private final String password = "8919867087Mm@";

    private Statement getStatement() {
        try {
            Connection connection = DriverManager.getConnection(dburl, userName, password);
            return connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query) {
        try {
            Statement statement = getStatement();
            return statement.executeQuery(query);
        } catch (Exception e) {
            return null;
        }
    }
    public int updateDatabase(String query){
        try {
            Statement statement = getStatement();
            return statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        } return 0;
    }

    public static void main(String[] args) {
        dbconnection conn = new dbconnection();
        ResultSet rs = conn.getQueryTable("Select *from customer");
        if (rs != null) {
            System.out.println("connection sr");
        } else {
            System.out.println("fail");
        }

    }
}