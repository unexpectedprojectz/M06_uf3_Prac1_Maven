/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.dades;

import dam.m06.uf1.aplicacio.model.Equip;
import dam.m06.uf1.aplicacio.model.Equips;
import dam.m06.uf1.aplicacio.model.Jugador;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author manel
 */
public class CSV {
    
    public static void exportaEquipsACSV(File fitx, Equips dades) throws DadesException
    {
        String row = "";
        String separador = ",";
        FileWriter fileWriter;
        
        try
        {
            fileWriter = new FileWriter(fitx);

            for(Equip e : dades.getEquips())
            {
                row += e.toStringCSV(separador);
                row += System.getProperty("line.separator");
                fileWriter.write(row);
                row = "";
            }
            
            fileWriter.flush();
            fileWriter.close();
            
        } catch (IOException ex) {
            throw new DadesException(ex.toString());
        }
    }
    
    public static void exportaJugadorsACSV(File fitx, Equips dades) throws DadesException
    {
        String row = "";
        String separador = ",";
        FileWriter fileWriter;
        
        try
        {
            fileWriter = new FileWriter(fitx);

            for(Equip e : dades.getEquips())
            {
                for (Jugador j : e.getJugadors())
                {
                    row += j.toStringCSV(separador);
                    row += System.getProperty("line.separator");
                    fileWriter.write(row);
                    row = "";
                }
            }
            
            fileWriter.flush();
            fileWriter.close();
            
        } catch (IOException ex) {
            throw new DadesException(ex.toString());
        }
    }
}
