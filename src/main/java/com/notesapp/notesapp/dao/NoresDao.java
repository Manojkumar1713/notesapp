package com.notesapp.notesapp.dao;

import com.notesapp.notesapp.dbcon.DbConnection;
import com.notesapp.notesapp.model.User;

import java.net.URISyntaxException;
import java.sql.*;

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
    public long insertRecord(User u){
        long id = 0;
        String sql = "Insert into public.\"Test\"(name,password) values (?,?)";
        try{
            Connection con = connect();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,u.getUsername());
            preparedStatement.setString(2,u.getPassword());
            id = preparedStatement.executeUpdate();
            System.out.println("Executed ---> "+sql);
        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }
        return id;
    }
    public int getUserName(String name){
        String SQL = "SELECT count(*) FROM public.\"Test\" where name ='"+name+"'";
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
    public int validUser(String name,String password){
        String SQL = "SELECT count(*) FROM public.\"Test\" where name ='"+name+"' and password ='"+password+"'";
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
