/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package outils;

/**
 *
 * @author Aziz
 */
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew
 */
public class MyDB {
    
    String url= "jdbc:mysql://localhost:3306/appfirst";
    String user = "root";
    String pwd = "";
    
    
    Connection con;
    
    //2
    public static MyDB instance;
    
    
      //1 
    private MyDB() {
        
        try {
            System.out.println("en cours de connexion");
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("conexion etablie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    //3 
    public static MyDB createorgetInstance(){
        if(instance ==null ){
            instance = new MyDB();
            
        }
        
        return instance;
    }

    
    
    public Connection getCon() {
        return con;
    }
    
    
    
    
    
}

