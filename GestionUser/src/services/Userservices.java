/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import outils.MyDB;

/**
 *
 * @author Aziz
 */
public class Userservices {
     Connection cnx;

    public Userservices() {
        cnx = MyDB.createorgetInstance().getCon();
    }
    public void ajouter(User t) {
        try {
            String sql = "insert into user(email,roles,password,lastname)"
                    + "values (?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getEmail());
            ste.setString(2, t.getRoles());
            ste.setString(3, t.getPassword());
            ste.setString(4, t.getLastname());
            ste.executeUpdate();
            System.out.println("Personne ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    } 
    public User getUser(String username , String password) throws SQLException{
        User u=null;
        String sql ="SELECT * FROM user WHERE lastname = ? AND password = ?";
         PreparedStatement  stmt = cnx.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

          ResultSet  rs = stmt.executeQuery();
          while(rs.next()){
              u = new User( rs.getInt("id"),rs.getString("email"), rs.getString("lastname"), rs.getString("password"), rs.getString("roles"));
          }
        return u;
    }
}
