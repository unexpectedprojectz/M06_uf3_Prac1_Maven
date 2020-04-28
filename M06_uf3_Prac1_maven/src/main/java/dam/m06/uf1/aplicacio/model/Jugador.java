/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.aplicacio.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manel
 */

@XmlRootElement
public class Jugador {
    
    private Integer id;
    private Integer idEquip;
    private String nombre;
    private Integer dorsal;
    private Integer edad;
    private String cp;
    
    public Jugador() {
    }

    public Jugador(Integer id, Integer idEquip, String nombre, Integer dorsal, Integer edad, String cp) {
        this.id = id;
        this.idEquip = idEquip;
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.edad = edad;
        this.cp = cp;
    }

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    public Integer getIdEquip() {
        return idEquip;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getDorsal() {
        return dorsal;
    }

    
    public Integer getEdad() {
        return edad;
    }
    
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdEquip(Integer idEquip) {
        this.idEquip = idEquip;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDorsal(Integer dorsal) {
        this.dorsal = dorsal;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
    
    @Override
    public String toString() {
        return "Jugador{" + "id=" + id + ", idEquip=" + idEquip + ", nombre=" + nombre + ", dorsal=" + dorsal + ", edad=" + edad + ", cp=" + cp + '}';
    }
    
    public String toStringCSV(String separador){
        String row = "";
        
        row += this.getId() + separador;
        row += this.getIdEquip() + separador;
        row += this.getNombre() + separador;
        row += this.getDorsal() + separador;
        row += this.getEdad() + separador;
        row += this.getCp();

        return row;
    }
    
}
