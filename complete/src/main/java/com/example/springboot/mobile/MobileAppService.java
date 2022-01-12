package com.example.springboot.mobile;

import org.springframework.stereotype.Service;

import javax.lang.model.element.NestingKind;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.sql.*;
import java.util.Dictionary;
import java.util.Hashtable;

@Service
public class MobileAppService {

    //private Dictionary<Integer, MobileApp> mobileList= new Hashtable<Integer, MobileApp>();
    private int deviceId = 0;

    public String RegisterMobile() throws GeneralSecurityException, IOException, SQLException {
        //create a deviceId
        //is received a public (and private key?) or generated

        MobileApp mobile = new MobileApp(deviceId++);
        //mobileList.put(deviceId, mobile);

        System.out.println("no mobile");

        String pathKey =  mobile.secretKey;

        System.out.println("no mobile1");


        //Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        //Statement statement = connection.createStatement();
        //statement.setQueryTimeout(30);  // set timeout to 30 sec.
        //statement.executeUpdate("insert into mobileDevice values(deviceId, pathKey)");
        final String SQL = "INSERT INTO mobileDevice VALUES(?,?)";
        System.out.println("no mobile2");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")){
             //PreparedStatement ps = connection.prepareStatement(SQL);) {
            System.out.println("no mobile3");
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, deviceId);
            ps.setString(2, pathKey);
            System.out.println("Starting the connection");
            ps.setQueryTimeout(30);  // set timeout to 30 sec.
            System.out.println("Inserting a new mobile device");
            ps.executeUpdate(SQL);
            System.out.println("Closing statement");
            ps.close();
            connection.commit();
            connection.close();
            System.out.println("Closing connection");
            // create a new mobile device table
            //stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return "olá";
    }
}
