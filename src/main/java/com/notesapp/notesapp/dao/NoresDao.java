package com.notesapp.notesapp.dao;

import com.notesapp.notesapp.dbcon.DbConnection;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NoresDao {
    public Connection connect() throws SQLException, URISyntaxException, ClassNotFoundException {
        return DbConnection.getConnection();
    }

    public int getCount(){
        String SQL = "SELECT count(*) FROM public.\"Test\"";
        int count = 0;

        try {
            Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL);
            System.out.println("Executed ---> "+SQL);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException | URISyntaxException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return count;
    }
}
