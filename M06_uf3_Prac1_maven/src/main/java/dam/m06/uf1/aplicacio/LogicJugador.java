/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.aplicacio;

import dam.m06.uf1.aplicacio.model.Equips;
import dam.m06.uf1.aplicacio.model.Jugador;
import dam.m06.uf1.dades.CSV;
import dam.m06.uf1.dades.DadesException;
import dam.m06.uf1.dades.JugadorsBD;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author manel
 */
public class LogicJugador {
    
    public static ArrayList<Jugador> getJugadorsByIdEquip(int idEquip) throws AplicacioException
    {
        DriverMySql conn = null;
        ArrayList<Jugador> ret = null;
        
        try {
            conn = DriverMySql.getInstance();
            ret = JugadorsBD.CarregarJugadorsByIdEquip(conn.getConnection(), idEquip);
        }catch (DadesException ex) {
            throw new AplicacioException("Error getJugadors: " + ex.toString());
        }
        return ret;
    }
    
    public static ArrayList<Jugador> getJugadors() throws AplicacioException
    {
        DriverMySql conn = null;
        ArrayList<Jugador> ret = null;
        
        try {
            conn = DriverMySql.getInstance();
            ret = JugadorsBD.CarregarJugadors(conn.getConnection());
        }catch (DadesException ex) {
            throw new AplicacioException("Error getJugadors: " + ex.toString());
        }
        return ret;
    }
    
    public static void insertJugador(Jugador j) throws AplicacioException
    {
        DriverMySql conn = null;
        
        try {
            verificaReglesNegoci(j);
            
            conn = DriverMySql.getInstance();
           
            JugadorsBD.insertJugadorBD(conn.getConnection(), j);
            conn.closeConnection();
            
        } catch (DadesException ex) {
            throw new AplicacioException("Error insertJugador: " + ex.toString());
        }
    }
    
    public static void deleteJugador(Jugador j) throws AplicacioException
    {
        DriverMySql conn = null;
        
        try { 
            conn = DriverMySql.getInstance();
            JugadorsBD.eliminarJugador(conn.getConnection(), j);
            conn.closeConnection();
        } catch (DadesException ex) {
            throw new AplicacioException("Error deleteEquip: " + ex.toString());
        }
    }
    
    public static void deleteAllJugadors() throws AplicacioException
    {
        DriverMySql conn = null;
        
        try { 
            conn = DriverMySql.getInstance();
            JugadorsBD.eliminarJugadors(conn.getConnection());
            conn.closeConnection();
        } catch (DadesException ex) {
            throw new AplicacioException("Error deleteEquip: " + ex.toString());
        }
    }
    
    public static void modifyJugador(Jugador j) throws AplicacioException
    {
        DriverMySql conn = null;
        
        try {
            
            verificaReglesNegoci(j);
            
            conn = DriverMySql.getInstance();
            JugadorsBD.modificarJugador(conn.getConnection(), j);
            conn.closeConnection();
        } catch (AplicacioException | DadesException ex) {
            throw new AplicacioException("Error modifyJugador: " + ex.toString());
        }
    }   
    
    public static void verificaReglesNegoci(Jugador j) throws AplicacioException
    {
         reglaNegoci2(j);
         reglaNegoci3(j);
         reglaNegoci5(j);
    }
    
    /**
     * Si un jugador és menor d’edat no pot tenir un equip assignat
     * @param j
     * @throws AplicacioException 
     */
    private static void reglaNegoci2 (Jugador j) throws AplicacioException
    {
         String txtReglaNegoci2 = "Si un jugador és menor d’edat no pot tenir un dorsal diferent de zero assignat";
                 
        if (j.getEdad() < 18 && j.getDorsal() != 0)
            throw new AplicacioException(txtReglaNegoci2);
    }
    
    private static void reglaNegoci3 (Jugador j) throws AplicacioException
    {
         String txtReglaNegoci3 = "Un dorsal de jugador ha de ser entre 0 i 11";
                 
        if (j.getDorsal()< 0 || j.getDorsal() > 11)
            throw new AplicacioException(txtReglaNegoci3);
    }
    
    private static void reglaNegoci5 (Jugador j) throws AplicacioException
    {
         String txtReglaNegoci5 = "L’edat d’un jugador ha d’estar en un rang d’entre 16 i 30 anys";
                 
        if (j.getEdad() < 16 || j.getEdad() > 30)
            throw new AplicacioException(txtReglaNegoci5);
    }
    
    public static String desaJugadorsCSV(File fitx, Equips e) throws AplicacioException 
    {
        String errors = "";
        
        if (e.getEquips().size() > 0)
        {
            try {
                CSV.exportaJugadorsACSV(fitx, e);
            } catch (DadesException ex) {
                throw new AplicacioException("Error: " + ex.toString());
            }
        }
         
        return errors;
    }
}
