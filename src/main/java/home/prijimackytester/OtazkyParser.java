/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janco1
 */
public class OtazkyParser {

    static List<Otazka> parseSpravneOdpovede(List<Otazka> otazky) throws IOException {
        //File file = new File("odpovede.txt");
        File file = new File("processedOdpovede.txt");
        StringBuilder sb = new StringBuilder();
        BufferedReader f = null;
        try {
            f = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (true) {
                String line = f.readLine();
                if (line == null) {
                    break;
                }

                int idOtazky = Integer.parseInt(line.split(":")[0]);
                String odpovede = line.split(":")[1].trim();
                boolean[] jeSpravne = new boolean[8];
                if (odpovede.length() == 0) {
                    otazky.get(idOtazky).jeSpravne = jeSpravne;
                } else {
                    for (int i = 0; i < odpovede.length(); i++) {
                        int idxOdpovede = (int) (odpovede.charAt(i)) - 97;
                        jeSpravne[idxOdpovede] = true;
                    }
                    otazky.get(idOtazky).jeSpravne = jeSpravne;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return otazky;
    }
    
    static void opravitSpravneOdpovede() throws IOException {
        //File file = new File("odpovede.txt");
        File file = new File("odpovede.txt");
        StringBuilder sb = new StringBuilder();
        BufferedReader f = null;
        try {
            f = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            StringBuilder buffer=new StringBuilder();
            while (true) {
                String line = f.readLine();
                if (line == null) {
                    break;
                }
                boolean mameCislo=false;
                for (int i=0; i<line.length(); i++){
                    if (mameCislo){
                        if (line.charAt(i)>='0' && line.charAt(i)<='9'){
                            // len pridame do bufferu dalsiu cifru
                            buffer.append(line.charAt(i));
                        }else{
                            // mali sme cislo ale uz nemame, vypiseme cislo a zresetujeme buffer
                            System.out.print(buffer.toString().trim()+": ");
                            buffer=new StringBuilder();
                            mameCislo=false;
                        }
                        
                    }else{
                        if (line.charAt(i)>='0' && line.charAt(i)<='9'){
                            // nemali sme cislo, tak vypiseme buffer a zacneme do bufru nacitavat cislo
                            System.out.println(buffer.toString().trim());
                            if (buffer.toString().contains(".")){
                                System.out.println(line);
                            }
                            buffer=new StringBuilder();
                            mameCislo=true;
                            buffer.append(line.charAt(i));
                        }else{
                            // nemali sme cislo a ani nemame, iba appendujeme
                            buffer.append(line.charAt(i));
                        }
                    }
                }
                
                
            }

        } catch (Exception ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void kontrolaOtazky() {
        File file = new File("biologia.txt");

        BufferedReader f = null;
        try {
            f = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            f.readLine();
            for (int i = 1; i <= 1500; i++) {
                String line = f.readLine().trim();
//                if (line.startsWith("?")){
//                    line=line.substring(1);
//                }
                System.out.println("line "+i+": "+line);
                // check ci sedi cislo otazky
                int id=Integer.parseInt(line.split("\\.")[0]);
                if (id!=i){
                    System.out.println("chyba na riadku "+i);
                    return;
                }
                for (char c = 'a'; c <= 'h'; c++) {
                    line = f.readLine();
                    if (!line.startsWith(c+")")){
                        System.out.println("chyba na riadku "+(i*8+i)+" + "+c+") ");
                        return;
                    }
                }
            }
            System.out.println("ziadne chyby nenaslo");
        } catch (Exception ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void parseOtazky() {
        File file = new File("biologia.txt");

        BufferedReader f = null;
        try {
            f = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Otazka> otazky = new ArrayList<Otazka>();
        Otazka defaultna=new Otazka();
        defaultna.id=0;
        otazky.add(defaultna);
        
        try {
            f.readLine();
            while (true) {
                String line = f.readLine();
                if (line == null) {
                    break;
                }
                Otazka nova = new Otazka();
                nova.otazka = line.split("\\.")[0]+". "+line.substring(line.indexOf(".")+1).trim();
                String[] odpovede = new String[8];
                for (int i = 0; i < 8; i++) {
                    line = f.readLine();
                    odpovede[i] = line.substring(2).trim();
                }
                nova.odpovede = odpovede;
                otazky.add(nova);
            }
            otazky = parseSpravneOdpovede(otazky);

            // mame vsetko nacitane, hodime to do databazy
            Database database = new Database();
            System.out.println(otazky.get(1));
            //System.out.println("not inserted to db");
            database.inzertOtazkyBiologia(otazky);
            //}

        } catch (Exception ex) {
            Logger.getLogger(OtazkyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
        //parseSpravneOdpovede(null);
        //kontrolaOtazky();
        //parseOtazky();
        opravitSpravneOdpovede();
    }
}
