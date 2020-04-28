/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.dades;

import dam.m06.uf1.aplicacio.model.Equip;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author manel
 */
public class EquipsBD {
    
    /**
     * Elimina l'equip amb l'identificador subministrat
     * @param con
     * @param e
     * @throws SQLException 
     */
    public static void deleteAllEquipsBD(Connection con) throws DadesException
    {
        Statement sentencia;
        
        try
        {
            sentencia = con.createStatement();
            String sqlStr = "DELETE FROM Equips";
            sentencia.executeUpdate(sqlStr);
        }
        catch (SQLException ex)
        {
            throw new DadesException(ex.toString());
        }
    }
    
     /**
     * Retorna el contingut de la tauls en una col.lecció de equips
     * @param con
     * @return 
     */
    public static ArrayList<Equip> getEquipsBD(Connection con) throws DadesException
    {
        ArrayList<Equip> ret = new ArrayList<>();
        
        Statement sentencia;
        
        try
        {
            sentencia = con.createStatement();
            sentencia.executeQuery("SELECT * FROM Equips");
            ResultSet rs = sentencia.getResultSet();
            while (rs.next()) {
                ret.add(new Equip(rs.getInt("idEquipo"), rs.getString("nombre"), rs.getString("estadio"), rs.getString("poblacion"), rs.getString("provincia"), rs.getString("cod_postal")));
            }
        }
        catch (SQLException ex)
        {
            throw new DadesException(ex.toString());
        }
        
        return ret;
    }
    
    /**
     * Elimina l'equip amb l'identificador subministrat
     * @param con
     * @param e
     * @throws SQLException 
     */
    public static void deleteEquipBD(Connection con, Equip e) throws DadesException
    {
        Statement sentencia;
        
        try
        {
            sentencia = con.createStatement();
            String sqlStr = "DELETE FROM Equips WHERE idEquipo = " + e.getId();
            sentencia.executeUpdate(sqlStr);
        }
        catch (SQLException ex)
        {
            throw new DadesException(ex.toString());
        }
    }
    
    /**
     * Insereix un nou equip amb les dades subministrades
     * Ignora l'id de l'equip
     * @param con
     * @param e
     * @throws SQLException 
     */
    public static void insertEquipBD(Connection con, Equip e) throws DadesException
    {
        Statement sentencia;
        int id;
        try{
        sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        sentencia.executeQuery("SELECT * FROM Equips");
        ResultSet rs = sentencia.getResultSet();
        
         if (rs.next()){
                rs.last();
                id = rs.getInt("idEquipo")+1;
                
            }
            else id = 1;
         
        rs.moveToInsertRow();
        rs.updateInt("idEquipo", id);
        rs.updateString("nombre", e.getNombre());
        rs.updateString("estadio", e.getEstadio());
        rs.updateString("poblacion", e.getPoblacion());
        rs.updateString("provincia", e.getProvincia());
        rs.updateString("cod_postal", e.getCp());

        rs.insertRow();
        }
        catch (SQLException ex)
        {
            throw new DadesException(ex.toString());
        }
    }
    
    /**
     * Modifica un equip si existeix
     * @param con
     * @param e
     * @throws SQLException 
     */
    public static void modifyEquipBD(Connection con, Equip e) throws DadesException
    {
        Statement sentencia;
        
        try{
        sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        sentencia.executeQuery("SELECT * FROM Equips where idEquipo = " + e.getId());
        ResultSet rs = sentencia.getResultSet();
        
        if (rs.next())
        {
            rs.updateString("nombre", e.getNombre());
            rs.updateString("estadio", e.getEstadio());
            rs.updateString("poblacion", e.getPoblacion());
            rs.updateString("provincia", e.getProvincia());
            rs.updateString("cod_postal", e.getCp());
            rs.updateRow();
        }
        }
        catch (SQLException ex)
        {
            throw new DadesException(ex.toString());
        }
    }
    
   
}
