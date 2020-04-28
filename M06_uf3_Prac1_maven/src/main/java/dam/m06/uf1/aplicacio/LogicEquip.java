/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.aplicacio;

import dam.m06.uf1.aplicacio.model.Equip;
import dam.m06.uf1.aplicacio.model.Equips;
import dam.m06.uf1.aplicacio.model.Jugador;
import dam.m06.uf1.dades.CSV;
import dam.m06.uf1.dades.DadesException;
import dam.m06.uf1.dades.EquipsBD;
import dam.m06.uf1.dades.EquipsMBD;
import dam.m06.uf1.dades.XML;
import dam.m06.uf1.dades.JugadorsBD;
import dam.m06.uf1.dades.JugadorsMBD;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author manel
 */
public class LogicEquip {
    static String tipusbd;
    /**
     * Retorna tots els equips amb els seus corresponents jugadors
     * @return
     * @throws AplicacioException 
     */
    public static ArrayList<Equip> getEquips() throws AplicacioException
    {
        tipusbd = TipusBD.getTipusbd();
        ArrayList<Equip> ret = null;
        
        if (tipusbd.equals("sql")) {
            
            DriverMySql conn = null;

            try {
                conn = DriverMySql.getInstance();
                ret = EquipsBD.getEquipsBD(conn.getConnection());
                for (Equip e : ret) {
                    e.setJugadors(JugadorsBD.CarregarJugadorsByIdEquip(conn.getConnection(), e.getId()));
                }
                conn.closeConnection();
            }
            
            catch (DadesException ex) {
                throw new AplicacioException("Error getEquips: " + ex.toString());
            }
            
        }
        
        else{
            
            DriverMongoDB conn = null;
            
            try {
                conn = DriverMongoDB.getInstance();
                ret = EquipsMBD.getEquipsMBD(conn.getConnection());
                for (Equip e : ret) {
                    e.setJugadors(JugadorsMBD.CarregarJugadorsByIdEquipM(conn.getConnection(), e.getId()));
                }
                conn.closeConnection();
            } 
            
            catch (DadesException ex) {
                throw new AplicacioException("Error getEquips: " + ex.toString());
            }
            
        }
        
        
        return ret;
    }
    
    public static void insertEquip(Equip e) throws AplicacioException
    {
        tipusbd = TipusBD.getTipusbd();
        
        if (tipusbd.equals("sql")) {
            
            DriverMySql conn = null;

            try {

                reglaNegoci1(e);
                reglaNegoci4(e);

                conn = DriverMySql.getInstance();

                EquipsBD.insertEquipBD(conn.getConnection(), e);
                conn.closeConnection();

            } 
            
            catch (DadesException ex) {
                throw new AplicacioException("Error insertEquip: " + ex.toString());
            }
            
        }
        
        else{
            
            DriverMongoDB conn = null;
            
            try {

                reglaNegoci1(e);
                reglaNegoci4(e);

                conn = DriverMongoDB.getInstance();

                EquipsMBD.insertEquipMBD(conn.getConnection(), e);
                conn.closeConnection();

            } 
            
            catch (DadesException ex) {
                throw new AplicacioException("Error insertEquip: " + ex.toString());
            }
            
        }
        
    }
    
    public static void deleteEquip(Equip e) throws AplicacioException
    {
        tipusbd = TipusBD.getTipusbd();
        
        if (tipusbd.equals("sql")) {
            
            DriverMySql conn = null;

            try {
                conn = DriverMySql.getInstance();
                EquipsBD.deleteEquipBD(conn.getConnection(), e);
                conn.closeConnection();
            } 
            
            catch (DadesException ex) {
                throw new AplicacioException("Error deleteEquip: " + ex.toString());
            }
            
        }
        
        else{
            
            DriverMongoDB conn = null;

            try {
                conn = DriverMongoDB.getInstance();
                EquipsMBD.deleteEquipMBD(conn.getConnection(), e);
                conn.closeConnection();
            } 
            
            catch (DadesException ex) {
                throw new AplicacioException("Error deleteEquip: " + ex.toString());
            }
            
        }
        
        
    }    
    
    public static void deleteAllEquips() throws AplicacioException
    {
        tipusbd = TipusBD.getTipusbd();
        
        if (tipusbd.equals("sql")) {
            
            DriverMySql conn = null;

            try {
                conn = DriverMySql.getInstance();
                EquipsBD.deleteAllEquipsBD(conn.getConnection());
                conn.closeConnection();
            } 
            
            catch (DadesException ex) {
                throw new AplicacioException("Error deleteAllEquips: " + ex.toString());
            }
            
        }
        
        else{
            
            DriverMongoDB conn = null;

            try {
                conn = DriverMongoDB.getInstance();
                EquipsMBD.deleteAllEquipsMBD(conn.getConnection());
                conn.closeConnection();
            } 
            
            catch (DadesException ex) {
                throw new AplicacioException("Error deleteAllEquips: " + ex.toString());
            }
            
        }
        
    }    
    
    public static void modifyEquip(Equip e) throws AplicacioException
    {
        tipusbd = TipusBD.getTipusbd();
        
        if (tipusbd.equals("sql")) {
            
            DriverMySql conn = null;

            try {
                reglaNegoci1(e);
                reglaNegoci4(e);

                conn = DriverMySql.getInstance();
                EquipsBD.modifyEquipBD(conn.getConnection(), e);
                conn.closeConnection();
            } catch (DadesException ex) {
                throw new AplicacioException("Error modifyEquip: " + ex.toString());
            }
            
        }
        
        else{
            
            DriverMongoDB conn = null;

            try {
                reglaNegoci1(e);
                reglaNegoci4(e);

                conn = DriverMongoDB.getInstance();
                EquipsMBD.modifyEquipMBD(conn.getConnection(), e);
                conn.closeConnection();
            } 
            
            catch (DadesException ex) {
                throw new AplicacioException("Error modifyEquip: " + ex.toString());
            }
            
        }
        
    }
    
    /**
     * Un equip pot tenir el codi postal en blanc, pero si el té informat, ha de ser en el format correcte: únicament 5 números.
     * @param e
     * @throws AplicacioException 
     */
    private static void reglaNegoci1 (Equip e) throws AplicacioException
    {
        int tmp;
        
        String txtReglaNegoci = "Un equip ha de tenir el CP informat i en el format correcte: únicament 5 números.";
        
        try
        { 
            //si és diferent de 5 caracters llavors llencem excepció
            if(e.getCp().length() != 5) 
                throw new AplicacioException(txtReglaNegoci);
            // Si no ho podem passar a numèric, petarà
            tmp = Integer.parseInt(e.getCp());
            
        }
        catch (NumberFormatException ex)
        {
            throw new AplicacioException(txtReglaNegoci);
        }
    }
    
    /**
     * Un equip ha de tenir un estadi informat diferent de blanc.
     * @param e
     * @throws AplicacioException 
     */
    private static void reglaNegoci4 (Equip e) throws AplicacioException
    {
        String txtReglaNegoci = "Un equip ha de tenir un estadi informat diferent de blanc.";
        
        if ("".equals(e.getEstadio()))
            throw new AplicacioException(txtReglaNegoci);
    }
    
    public static Equips carregaDadesDeXML(File fitxer) throws AplicacioException
    {
        Equips ret = new Equips();
        
        try {
           
           ret = XML.carregaDadesDeXML(fitxer);            	
   	}
        catch (DadesException e) {
       		throw new AplicacioException("Error: " + e.toString());
   	}
        
        return ret;
    }
    
    public static void exportaDadesToXML(File fitx, Equips dades) throws AplicacioException
    {
        try {
           
           XML.exportaDadesAXML(fitx, dades);            	
   	}
        catch (Exception e) {
       		throw new AplicacioException("Error: " + e.toString());
   	}
    }
    
    /**
     * Desa les dades a la BD, tot verificant les regles de negoci
     * @param e
     * @return string amb els errors o string buit si no hi ha errors
     */
    public static String desaDadesBD(Equips e)
    {
         String errors = "";
         
         if (e.getEquips() != null && e.getEquips().size() > 0)
            {    
                for (Equip eq : e.getEquips())
                {
                    try
                    {
                        LogicEquip.insertEquip(eq);
                    }
                    catch (AplicacioException ex)
                    {
                        errors += "ID equip: " +eq.getId() + " ; Error: " + ex.toString() + System.getProperty("line.separator");
                    }
                    
                    if (eq.getJugadors()!= null && eq.getJugadors().size() > 0)
                    {
                        for (Jugador j : eq.getJugadors())
                        {
                            try
                            {
                                LogicJugador.insertJugador(j);
                            }
                            catch (AplicacioException ex)
                            {
                                errors += "ID Jugador: " +eq.getId() + " ; Error: " + ex.toString() + System.getProperty("line.separator");
                            }
                        }
                    }
                }
            }
         
        return errors;
    }
    
     
    public static String desaEquipsCSV(File fitx, Equips e) throws AplicacioException 
    {
        String errors = "";
        
        if (e.getEquips().size() > 0)
        {
            try {
                CSV.exportaEquipsACSV(fitx, e);
            } catch (DadesException ex) {
                throw new AplicacioException("Error: " + ex.toString());
            }
        }
         
        return errors;
    }
}
