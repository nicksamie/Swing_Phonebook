package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by gunner on 2/21/15.
 */
public class DatabaseConnection {
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String db_url = "jdbc:mysql://localhost:3306/phonebook";

    //Database Credentials
    static final String username = "root";
    static final String password = "root";

    private Connection conn;
    public Connection getConnection()
    {
        return  conn;
    }
    public DatabaseConnection(){
        try{
            //Open a connection
            System.out.print("Connecting to a selected database...");
            conn = DriverManager.getConnection(db_url, username, password);
            System.out.print("Connection Established...!!!");

        }catch (SQLException e){
            //Handle errors for JDBC
            e.printStackTrace();
        }catch( Exception e ) {
            System.out.println("Failed to load mySQL driver.");
            e.printStackTrace();
        }
    }
}

