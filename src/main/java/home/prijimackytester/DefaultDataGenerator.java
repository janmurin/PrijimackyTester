/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

/**
 *
 * @author Janco1
 */
public class DefaultDataGenerator {

    public static void main(String[] args) {
        int[][] otazky=new int[1501][8];
        for (int i = 1; i <= 1500; i++) {
            System.out.println("Otazka " + i + "");
            for (char j = 'a'; j <= 'h'; j++) {
                if (Math.random() < 0.4) {
                    otazky[i][j-'a']=1;
                    System.out.println(j + ") spravna odpoved");
                } else {
                    //otazky[i][j-'a']=0;
                    System.out.println(j + ") nespravna odpoved");
                }
            }
        }
        System.out.println("Spravne odpovede");
        for (int i = 1; i <= 1500; i++) {
            System.out.print(i+": ");
            for (int j=0; j<8; j++){
                if (otazky[i][j]==1){
                    int value=97+j;
                    System.out.print(Character.toChars(value)[0]);
                }
            }
            System.out.println("");
        }
//        System.out.println("a = "+Character.toChars(97)[0]);
//        System.out.println("b = "+Character.toChars(98)[0]);
//        System.out.println("c = "+Character.toChars(99)[0]);
    }
}
