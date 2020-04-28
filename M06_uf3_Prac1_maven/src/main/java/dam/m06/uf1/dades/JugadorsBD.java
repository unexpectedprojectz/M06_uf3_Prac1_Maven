/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.dades;

import dam.m06.uf1.aplicacio.model.Jugador;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author manel
 */
public class JugadorsBD {
    /**
     * Modifica el jugador amb les dades contingudes
     * @param con
     * @param j 
     * @throws dam.m06.uf1.dades.DadesException 
     */
    public static void modificarJugador(Connection con, Jugador j) throws DadesException
    {
         Statement sentencia;
         
         try{
            sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                 sentencia.executeQuery("SELECT * FROM Jugadors where idJugador = "+j.getId());
                ResultSet rs = sentencia.getResultSet();
                if (rs.next())
                {
                     rs.updateInt("idEquipo" , j.getIdEquip()); 
                     rs.updateString("nombre" , j.getNombre()); 
                     rs.updateInt("dorsal" , j.getDorsal()); 
                     rs.updateInt("edad" , j.getEdad()); 
                     rs.updateString("cp_jugador" , j.getCp()); 
                     rs.updateRow();
                }else 
                    throw new DadesException("El jugador " + j + "no s'ha trobat");
                    
        }catch(SQLException ex){
            throw new DadesException("Error modificarJugador: " + ex.toString());
        }
    }
    
    
    public static void eliminarJugadors(Connection con) throws DadesException
    {
         Statement sentencia;
         
         try{
                sentencia = con.createStatement();
                String sqlStr = "DELETE FROM Jugadors";
                sentencia.executeUpdate(sqlStr);
              
        }catch(SQLException ex){
            throw new DadesException("Error eliminarJugadors: " + ex.toString());
        }
    }
   
    
    /**
     * Insereix un nou jugador
     * @param con
     * @param j 
     */
    public static void eliminarJugador(Connection con, Jugador j) throws DadesException
    {
         Statement sentencia;
         
         try{
            sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            sentencia.executeQuery("SELECT * FROM Jugadors where idJugador = "+j.getId());
            ResultSet rs = sentencia.getResultSet();
            if (rs.next())
                rs.deleteRow();
              
        }catch(SQLException ex){
            throw new DadesException("Error eliminarJugador: " + ex.toString());
        }
    }
    
    /**
     * Retorna el contingut de la taula jugadors
     * @param con
     * @return 
     */
    public static ArrayList<Jugador> CarregarJugadors(Connection con) throws DadesException
    {
        ArrayList<Jugador> ret = new ArrayList<>();
        
         Statement sentencia;
         
         try{
            sentencia = con.createStatement();
            sentencia.executeQuery("SELECT * FROM Jugadors");
            ResultSet rs = sentencia.getResultSet();
            while (rs.next()) {
                ret.add(new Jugador(rs.getInt("idJugador"), rs.getInt("idEquipo"), rs.getString("nombre"), rs.getInt("dorsal"), rs.getInt("edad"), rs.getString("cp_jugador")));
            }
         }catch(SQLException ex){
           throw new DadesException("Error CarregarJugadors: " + ex.toString());
        }
        return ret;
    }
    
     /**
     * Retorna el contingut de la taula jugadors
     * @param con
     * @return 
     */
    public static ArrayList<Jugador> CarregarJugadorsByIdEquip(Connection con, int idEquip) throws DadesException
    {
        ArrayList<Jugador> ret = new ArrayList<>();
        
         Statement sentencia;
         
         try{
            sentencia = con.createStatement();
            sentencia.executeQuery("SELECT * FROM Jugadors where idEquipo = "+idEquip);
            ResultSet rs = sentencia.getResultSet();
            while (rs.next()) {
                ret.add(new Jugador(rs.getInt("idJugador"), rs.getInt("idEquipo"), rs.getString("nombre"), rs.getInt("dorsal"), rs.getInt("edad"), rs.getString("cp_jugador")));
            }
         }catch(SQLException ex){
           throw new DadesException("Error CarregarJugadors: " + ex.toString());
        }
        return ret;
    }
    
    /**
     * Insereix un nou jugador amb les dades subministrades
     * Ignora l'id de l'jugador
     * @param con
     * @param j
     * @param e 
     */
    public static void insertJugadorBD(Connection con, Jugador j) throws DadesException
    {
        Statement sentencia;
        int id;
        try{
            sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            sentencia.executeQuery("SELECT * FROM Jugadors");
            ResultSet rs = sentencia.getResultSet();
            if (rs.next()){
                rs.last();
                id = rs.getInt("idJugador")+1;
                
            }
            else id = 1;
            
            rs.moveToInsertRow();
            rs.updateInt("idJugador", id);
            rs.updateInt("idEquipo" , j.getIdEquip()); 
            rs.updateString("nombre" , j.getNombre()); 
            rs.updateInt("dorsal" , j.getDorsal()); 
            rs.updateInt("edad" , j.getEdad()); 
            rs.updateString("cp_jugador" , j.getCp()); 

            rs.insertRow();
        }
        catch (SQLException ex)
        {
            throw new DadesException("Error insertJugadorBD: " + ex.toString());
        }
    }
}
