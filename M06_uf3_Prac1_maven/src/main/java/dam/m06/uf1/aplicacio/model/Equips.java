/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.m06.uf1.aplicacio.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manel
 */
@XmlRootElement
public class Equips {
    
    private ArrayList<Equip> equips = new ArrayList<>();

    public Equips() {
    }

    @XmlElement (name = "equip")
    public ArrayList<Equip> getEquips() {
        return equips;
    }

    public void setEquips(ArrayList<Equip> equips) {
        this.equips = equips;
    }
}
