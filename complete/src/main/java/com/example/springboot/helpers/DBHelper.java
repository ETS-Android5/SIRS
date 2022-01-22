package com.example.springboot.helpers;

import com.example.springboot.user.User;
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
            statement.executeUpdate("create table user (username string, password string, sharedSecret string, loggedIn int)"); //loggedIn 1 loggetOut 0

            //create registration table
            statement.executeUpdate("drop table if exists registration");
            statement.executeUpdate("create table registration (code string, sharedSecret string)");

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

    public static ResponseEntity<String> insertUser(String username, String password, String code) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        if (confirmRegistrationCode(code).toString() == "Already associated to an account") {
            message = confirmRegistrationCode(code).toString();
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<String>(message, httpStatus);
        }

        String sql = "INSERT INTO user(username, password, code, sharedSecret, loggedIn) VALUES(?,?,? ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, code);

            String selectSql = "SELECT sharedSecret FROM registration where code = ?";
            try (PreparedStatement selectPstmt = connection.prepareStatement(selectSql)) {
                selectPstmt.setString(1, code);
                rs = selectPstmt.executeQuery();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            pstmt.setString(4, rs.getString("sharedSecret"));
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

    public static ResponseEntity<String> Login(String username, String sharedSecret) throws  SQLException {
        /*Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;
*/
        /*if (confirmRegistrationCode(code).toString() == "Already associated to an account") {
            message = confirmRegistrationCode(code).toString();
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<String>(message, httpStatus);
        }*/

        /*String sql = "INSERT INTO user(loggedIn) VALUES(?) WHERE passcode = ? and username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, sharedSecret);
            pstmt.setString(3, username);

            String selectSql = "SELECT sharedSecret FROM registration where code = ?";
            try (PreparedStatement selectPstmt = connection.prepareStatement(selectSql)) {
                selectPstmt.setString(1, code);
                rs = selectPstmt.executeQuery();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            pstmt.setString(4, rs.getString("sharedSecret"));
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
    */
        return new ResponseEntity<String>("Registration concluded", HttpStatus.OK);

    }


    public static ResponseEntity<String> confirmRegistrationCode(String code) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "SELECT code, sharedSecret FROM registration WHERE code = ?";
        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);
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

    public static ResponseEntity<String> LoginStatus(String username, String code) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "SELECT loggetIn FROM user WHERE username = ?";
        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            rs = pstmt.executeQuery(sql);

            /*if (rs.getInt("loggedIn")) {

            }*/

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

    public static ResponseEntity<String> confirmLoginCode(String code) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        String sql = "SELECT code FROM user WHERE code = ?";
        ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);
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

    public static ResponseEntity<String> insertRegistrationCode(String code, String sharedSecret) throws  SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        //String sqlConfirm = "SELECT code FROM registration WHERE code = ?";
        //ResultSet rs = null;
        String message = "";
        HttpStatus httpStatus = null;

        if (confirmRegistrationCode(code).toString() == "Already associated to an account") {
            message = confirmRegistrationCode(code).toString();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else {
            String sql = "INSERT INTO registration(code, sharedSecret) VALUES(?,?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, code);
                pstmt.setString(2, sharedSecret);
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