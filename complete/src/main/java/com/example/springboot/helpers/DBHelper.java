package com.example.springboot.helpers;

import com.example.springboot.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.beans.PropertyEditorSupport;
import java.sql.*;

public class DBHelper {

    public static void SQLinit() throws ClassNotFoundException
    {
        System.out.println("Initializing DB...");
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        //DriverManager.registerDriver(new org.sqlite.JDBC());

        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //create user table
            statement.executeUpdate("drop table if exists user");
            statement.executeUpdate("create table user (username string, password int, sharedSecret byte[], loggedIn int)"); //loggedIn 1 loggetOut 0

            //create registration table
            statement.executeUpdate("drop table if exists registration");
            statement.executeUpdate("create table registration (code byte[], sharedSecret byte[])");

            /*//create worker table
            statement.executeUpdate("drop table if exists worker");
            statement.executeUpdate("create table worker (username string, password string, sharedSecret string)");*/


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

    public static ResponseEntity<String> insertUser(String username, int password, byte[] code) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        if (confirmRegistrationCode(code).toString() != "Already associated to an account") {
            message = confirmRegistrationCode(code).toString();
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<String>(message, httpStatus);
        }

        String sql = "INSERT INTO user(username, password, code, sharedSecret, loggedIn) VALUES(?,?,? ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, password);
            pstmt.setBytes(3, code);

            String selectSql = "SELECT sharedSecret FROM registration where code = ?";
            try (PreparedStatement selectPstmt = connection.prepareStatement(selectSql)) {
                selectPstmt.setBytes(1, code);
                rs = selectPstmt.executeQuery();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            pstmt.setBytes(4, rs.getBytes("sharedSecret"));
            pstmt.setInt(5, 0);

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
        return new ResponseEntity<String>("Registration concluded", HttpStatus.OK);
    }

    public static String Login(String username, byte[] sharedSecret) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "INSERT INTO user(loggedIn) VALUES(?) WHERE sharedSecret = ? and username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setBytes(2, sharedSecret);
            pstmt.setString(3, username);
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
        return "OK";

    }

    public static ResponseEntity<String> Logout(String username) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "INSERT INTO user(loggedIn) VALUES(?) WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, 0);
            pstmt.setString(2, username);
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

        return new ResponseEntity<String>("Logout concluded", HttpStatus.OK);

    }


    public static ResponseEntity<String> confirmRegistrationCode(byte[] code) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "SELECT code FROM registration WHERE code = ?";
        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBytes(1, code);
            rs = pstmt.executeQuery(sql);
            if (rs.getBytes("code") == code) { //Code already in the registration table
                message = "NOT OK";
                httpStatus = HttpStatus.BAD_REQUEST;
            }
            message = "OK";
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

    public static String insertRegistrationCode(byte[] code, byte[] sharedSecret) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String message = "";

        if (confirmRegistrationCode(code).toString() == "NOT OK") {
            message = "NOT OK";
        }
        else {
            String sql = "INSERT INTO registration(code, sharedSecret) VALUES(?,?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setBytes(1, code);
                pstmt.setBytes(2, sharedSecret);
                pstmt.executeUpdate();
                //message = "Mobile associated with success";
                //httpStatus = HttpStatus.OK;
                message = "OK";

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
        //return new ResponseEntity<String>(message, httpStatus);
        return message;
    }

    public static byte[] getSharedSecret(String username) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        byte[] result = null;

        String sql = "SELECT sharedSecret FROM user WHERE username = ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            result = pstmt.executeQuery().getBytes("sharedSecret");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
        //return new ResponseEntity<String>(message, httpStatus);
        return result;
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
    }*/