/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.aplicacio.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manel
 */

@XmlRootElement
public class Equip {
    
    private Integer id;
    private String nombre;
    private String estadio;
    private String poblacion;
    private String provincia;
    private String cp;
    private ArrayList<Jugador> jugadors = new ArrayList<Jugador>();

    public Equip() {
    }

    public Equip(Integer id, String nombre, String estadio, String poblacion, String provincia, String cp) {
        this.id = id;
        this.nombre = nombre;
        this.estadio = estadio;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.cp = cp;
    }

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstadio() {
        return estadio;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCp() {
        return cp;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
    
    @XmlElement (name = "jugador")
    public ArrayList<Jugador> getJugadors() {
        return jugadors;
    }

    public void setJugadors(ArrayList<Jugador> jugadors) {
        this.jugadors = jugadors;
    }
    
    @Override
    public String toString() {
        return "Equip{" + "id=" + id + ", nombre=" + nombre + ", estadio=" + estadio + ", poblacion=" + poblacion + ", provincia=" + provincia + ", cp=" + cp + '}';
    }
    
    public String toStringCSV(String separador){
        String row = "";
        
        row += getId() + separador;
        row += getNombre() + separador;
        row += getEstadio() + separador;
        row += getPoblacion()+ separador;
        row += getProvincia() + separador;
        row += getCp();

        return row;
    }
}
