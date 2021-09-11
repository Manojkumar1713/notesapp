package com.notesapp.notesapp.dbcon;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    public final static String url= "jdbc:postgresql://ec2-18-235-45-217.compute-1.amazonaws.com:5432/de0ec4eqe2p6gg";
    public static Connection getConnection() throws URISyntaxException, SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user","bfdmotfswjjejd");
        props.setProperty("password","764e07d6cbea3c961183a8000f30c0ce19f78ce03a55a2f2d5a18445a03ba895");
        props.setProperty("sslmode","require");
        return DriverManager.getConnection(url,props);
    }

    public static void main(String[] args) throws SQLException, URISyntaxException, ClassNotFoundException {
        Connection con = getConnection();
        if(con != null){
            System.out.println("OK");
        }
        else
            System.out.println("Not OK");
    }
}

