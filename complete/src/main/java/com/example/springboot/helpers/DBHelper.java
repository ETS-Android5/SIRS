package com.example.springboot.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

            //create registration table
            statement.executeUpdate("drop table if exists registration");
            statement.executeUpdate("create table registration (mobile integer, code string)");

            //create keys table
            statement.executeUpdate("drop table if exists keys");
            statement.executeUpdate("create table keys (mobile integer, key integer)");


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

    public static ResponseEntity<String> confirmCode(int mobile) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "SELECT code FROM registration WHERE mobile = ?";
        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, mobile);
            rs = pstmt.executeQuery(sql);
            message = "Already associated to an account";
            httpStatus = HttpStatus.OK;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        return new ResponseEntity<String>(message, httpStatus);
    }

    public static ResponseEntity<String> insertRegistrationCode(int mobile, String code) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sqlConfirm = "SELECT mobile FROM registration WHERE mobile = ?";
        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        if (confirmCode(mobile).toString() == "Already associated to an account") {
            message = "Already associated to an account";
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else {
            String sql = "INSERT INTO registration(mobile, code) VALUES(?,?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, mobile);
                pstmt.setString(2, code);
                pstmt.executeUpdate();
                message = "Mobile associated with success";
                httpStatus = HttpStatus.OK;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
        return new ResponseEntity<String>(message, httpStatus);
    }

    public static ResponseEntity<String> SuccessRegisterMobile(int key, int mobile) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sqlConfirm = "SELECT mobile FROM registration WHERE mobile = ?";
        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        if (confirmCode(mobile).toString() == "Mobile not associated to an account") {
            message = "Mobile not associated to an account";
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else {
            String sql = "INSERT INTO keys(mobile, key) VALUES(?,?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, mobile);
                pstmt.setInt(2, key);
                pstmt.executeUpdate();
                message = "Saved key with success";
                httpStatus = HttpStatus.OK;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
        return new ResponseEntity<String>(message, httpStatus);
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