/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janco1
 */
public class helper {

    public static void main(String[] args) {
//        Database db=new Database();
//        List<Otazka> otazky=new ArrayList<>();
//        otazky=db.getOtazkyListBiologia();
//        System.out.println();
//        for (int k=0; k<otazky.size(); k++){
//            Otazka otazka=otazky.get(k);
//            //System.out.println((k)+":");
//            //System.out.println("o=new Otazka("+k+");");
//            //System.out.println("o.otazka = '"+otazka.otazka+"';");
//            System.out.println("+\""+otazka.otazka+"@\"");
//            for (int i=0; i<8; i++){
//                System.out.println("+\""+otazka.jeSpravne[i] +"_"+otazka.odpovede[i]+"@\"");
//                //System.out.println("o.odpovede.push('"+otazka.odpovede[i]+"');");
//                //System.out.println("o.jeSpravne.push("+otazka.jeSpravne[i]+");");
//            }
//            System.out.println("+\""+"|"+"\"");
//            //System.out.println("otazky.push(o);");
//            //System.out.println("");
//        }

        for (int n = 1; n < 50; n++) {
            //       int n=10;
            int pocet = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    pocet++;
                }
            }
            System.out.println("pocet pre n="+n+" je: "+pocet);
        }
    }
}
