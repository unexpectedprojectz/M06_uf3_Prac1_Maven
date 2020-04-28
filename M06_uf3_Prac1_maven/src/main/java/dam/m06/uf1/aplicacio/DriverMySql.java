/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.aplicacio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author manel
 */
public class DriverMySql {
    
    private static volatile DriverMySql instance = null;
    
    // Per seguretat aquestes dades s'haurien de llegir d'un fitxer xifrat o similar
    private String bd = "lliga_futbol";
    private String usuari = "root";
    private String password = "12345678";
    
    private Connection conn = null;

    public static DriverMySql getInstance() throws AplicacioException {
        if (instance == null) {
            // mecanisme per garantir coherencia en entorns multifil
            synchronized(DriverMySql.class) {
                if (instance == null) {
                    instance = new DriverMySql();
                }
            }
        }

        return instance;
    }
    
    public Connection getConnection() throws AplicacioException
    {
        Connection ret = null;
        
        ret = ConnectarBD();
        
        return ret;
    }
    
    private DriverMySql() throws AplicacioException {
        this.ConnectarBD();
    }
    
        
    /**
    * Connecta a una BD mysql i gestiona la connexió
    *
    * @return objecte Connection
    * @throws SQLException 
    */
    private Connection ConnectarBD() throws AplicacioException
    { 
        try {
            conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bd+"?useUnicode=true&"
                    + "useJDBCCompliantTimezoneShift=true&"   
                    + "useLegacyDatetimeCode=false&serverTimezone=UTC", usuari, password);
        } catch (SQLException ex) {
            throw new AplicacioException("Error ConnectarBD: " + ex.toString());
        }
       
        return conn;
    }
    
    public void closeConnection() throws AplicacioException
    {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            throw new AplicacioException("Error inicialitzant connexió: " + ex.toString());
        }
    }
}