/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.dades;

import dam.m06.uf1.aplicacio.model.Equips;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;

/**
 *
 * @author manel
 */
public class XML {
    
    public static void exportaDadesAXML(File fitx, Equips dades) throws DadesException
    {
        try {
            
            JAXBContext context = JAXBContext.newInstance(Equips.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(dades, fitx);
            
        } catch (JAXBException ex) {
            throw new DadesException(ex.toString());
        }
       
    }
    
    public static Equips carregaDadesDeXML(File fitx) throws DadesException
    {
        Equips ret = new Equips();
         
        try {
            
            JAXBContext context = JAXBContext.newInstance(Equips.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ret = (Equips)unmarshaller.unmarshal(fitx);
            
        } catch (JAXBException ex) {
            throw new DadesException(ex.toString());
        }
        
        return ret;
    }
    
    /**
     * Retorna un String que conté el XML 
     * @param txtUrl
     * @return
     * @throws DadesException 
     */
    public static String readRemoteXML(String txtUrl) throws DadesException
    {
        String ret = "";
        
        try {
            
            Builder analitzador = new Builder();
            Document doc;
            doc = analitzador.build(txtUrl);
            ret = doc.toXML();
            
        } catch (ParsingException ex) {
            throw new DadesException("readRemoteXML: URL incorrecta: " + ex.toString());
        }catch (IOException ex) {
             throw new DadesException("readRemoteXML: Error de connexió: " + ex.toString());
        }      
        
        return ret;
    }
    
}
