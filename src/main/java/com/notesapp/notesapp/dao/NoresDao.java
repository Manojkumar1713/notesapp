package com.notesapp.notesapp.dao;

import com.notesapp.notesapp.dbcon.DbConnection;
import com.notesapp.notesapp.model.Notes;
import com.notesapp.notesapp.model.User;
import org.springframework.stereotype.Repository;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
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
            conn.close();
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
            con.close();
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
            conn.close();
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
            conn.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return count;
    }
    public String getNote(String id,String userName){
        String SQL = "SELECT title,body,createdat,updatedat FROM public.\"Notes\" where \"noteId\" ='"+id+"' and username='"+userName+"'";
        String note = "";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            System.out.println("Executed ---> "+SQL);
            rs.next();
            note = rs.getString(1);
            note = note +"#"+ rs.getString(2);
            note = note +"#"+ rs.getString(3);
            note = note +"#"+ rs.getString(4);
            conn.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return note;
    }

    public long insertNotes(Notes note){
        long id = 0;
        String sql = "Insert into public.\"Notes\"(\"noteId\",title,body,createdat,updatedat,username" +
                ") values (?,?,?,?,?,?)";
        System.out.println("Executed ---> "+sql);
        try{
            Connection con = connect();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,note.getId());
            preparedStatement.setString(2,note.getTitle());
            preparedStatement.setString(3,note.getBody());
            preparedStatement.setString(4,note.getCreatedAt());
            preparedStatement.setString(5,note.getUpdatedAt());
            preparedStatement.setString(6,note.getUser());
            id = preparedStatement.executeUpdate();
            System.out.println("Executed ---> "+sql);
            con.close();
        }catch(Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return id;
    }
    public void updateNote(Notes note){
        String sql = "update public.\"Notes\" set title = ?,body = ?,updatedat =? where \"noteId\" ='"+note.getId()+"' and username ='"+note.getUser()+"'";
        try{
            Connection con = connect();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,note.getTitle());
            preparedStatement.setString(2,note.getBody());
            preparedStatement.setString(3,note.getUpdatedAt());
            preparedStatement.executeUpdate();
            System.out.println("Executed ---> "+sql);
            con.close();
        }catch(Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public List<Notes> getAllNotes(String user){
        List<Notes> notes = new ArrayList<>();
        String sql = "select * from public.\"Notes\" where username='"+user+"' order by seq asc";
        System.out.println(sql);
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Executed ---> "+sql);
            while(rs.next()) {
                Notes note = new Notes();
                note.setId(rs.getString(2));
                note.setTitle(rs.getString(3));
                note.setBody(rs.getString(4));
                note.setCreatedAt(rs.getString(5));
                note.setUpdatedAt(rs.getString(6));
                note.setUser(rs.getString(7));
                notes.add(note);
            }
            conn.close();
        }
        catch (SQLException | URISyntaxException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return notes;
    }

    public void deleteNote(String noteId,String username){
        String sql = "delete from public.\"Notes\" where \"noteId\" ='"+noteId+"' and username = '"+username+"'";
        System.out.println(sql);
        try{
            Connection con = connect();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Executed ---> "+sql);
            con.close();
        }catch(Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
