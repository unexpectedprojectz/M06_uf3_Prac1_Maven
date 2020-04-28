/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.aplicacio;

import dam.m06.uf1.dades.DadesException;
import static dam.m06.uf1.dades.XML.readRemoteXML;
import java.io.IOException;
import java.io.StringReader;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;

/**
 *
 * @author manel
 */
public class Common {
    
    public static String retornaTempsCiutat(String url) throws AplicacioException
    {
       String ret = "";
       Builder analitzador;
       String strXml;
           
       try {
            strXml = readRemoteXML(url);
               
            analitzador = new Builder();
            
            //Carreguem el document XML mitjançant l'analitzador
            Document doc = analitzador.build(new StringReader(strXml));
           
            //llegim l'element arrel del document,<weatherdata>
            Element arrel = doc.getRootElement();
            //llegim el primer element de tipus <location> fill de l'arrel perquè només hi ha un
            Element prediccio = arrel.getFirstChildElement("prediccion");
           
            for (Element e : prediccio.getChildElements("ciudad"))
            {
               ret += "Ciutat: " + e.getFirstChildElement("nombre").getValue() + " Temperatures min/max: " + e.getFirstChildElement("minima").getValue() + " ; " + e.getFirstChildElement("maxima").getValue() + System.getProperty("line.separator");
            }
           
        }catch (ParsingException ex) {
           throw new AplicacioException("retornaTempsCiutat: " + ex.toString());
        }catch (IOException ex) {
           throw new AplicacioException("retornaTempsCiutat: " + ex.toString());
        }catch (DadesException ex) {
          throw new AplicacioException("retornaTempsCiutat: " + ex.toString());
        }
        
        return ret;
    }
    
}
