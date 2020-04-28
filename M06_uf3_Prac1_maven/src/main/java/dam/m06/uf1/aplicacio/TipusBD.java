package dam.m06.uf1.aplicacio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TipusBD {
    
    BufferedReader br;
    
    private static String tipusbd;
    
    public void obtenirTipusBD() throws AplicacioException{
        // llegim si el paràmetre és sql o nosql
        try{
            br = new BufferedReader(new FileReader("tipusbd.txt"));
            setTipusbd(br.readLine());
        }
        
        catch (FileNotFoundException ex){
            throw new AplicacioException("Error al obtenir arxiu de configuració: " + ex.toString());
        }
        
        catch (IOException ex){
            throw new AplicacioException("Error de lectura de l'arxiu de configuració: " + ex.toString());
        }
        
    }

    public static String getTipusbd() {
        return tipusbd;
    }

    public void setTipusbd(String tipusbd) {
        this.tipusbd = tipusbd;
    }
    
}
