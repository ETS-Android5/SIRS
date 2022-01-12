package com.example.springboot.helpers;

import java.sql.*;

public class DBHelper {

    public static void SQLinit() throws ClassNotFoundException
    {
        System.out.println("Initializing DB...");
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        // DriverManager.registerDriver(new org.sqlite.JDBC());

        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //create user table
            statement.executeUpdate("drop table if exists user");
            statement.executeUpdate("create table user (id integer, username string, password string, keyPath string)");

            //create worker table
            statement.executeUpdate("drop table if exists worker");
            statement.executeUpdate("create table worker (id integer, username string, password string, keyPath string)");


        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    public static void insertUser(int id, String username, String password, String keyPath) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "INSERT INTO user(id,username, password) VALUES(?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, keyPath);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    public static void insertWorker(int id, String username, String password, String keyPath) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "INSERT INTO user(id,username, password) VALUES(?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, keyPath);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }


}


/*Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("select * from mobileDevice");
            while(rs.next())
            {
                System.out.println("no while");
                // read the result set
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("username = " + rs.getString("username"));
                System.out.println("password = " + rs.getString("password"));
            }*/