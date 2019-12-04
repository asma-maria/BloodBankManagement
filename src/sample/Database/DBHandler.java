package sample.Database;

import java.sql.*;

public class DBHandler extends Config {
    public static Connection connect(){
        String connectionString = "jdbc:mysql://"
                + dbHost
                + ":"
                + dbPort
                + "/"
                + dbName
                +"?autoReconnect=true&useSSL=false";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
//            System.out.println("success");
            return connection;
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Not connected");
            return null;
        }

    }
}
