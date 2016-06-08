/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.util.Arrays;

/**
 *
 * @author Janco1
 */
public class User {
    
    String username;
    int[] statistika;

    @Override
    public String toString() {
        return "username: "+username+"\n "+Arrays.toString(statistika)+"";
    }
    
    
}
