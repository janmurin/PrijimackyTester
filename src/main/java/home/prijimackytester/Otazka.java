/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Janco1
 */
public class Otazka {
    
    long id;
    String otazka;
    String[] odpovede=new String[8];
    boolean[] jeSpravne=new boolean[8];

    @Override
    public String toString() {
        return "id: "+id+"\n"
                + "otazka: "+otazka+"\n"
                + "odpovede: "+Arrays.toString(odpovede)+"\n"
                + "jeSpravne: "+Arrays.toString(jeSpravne)+"\n";
    }
    
    
    
}
