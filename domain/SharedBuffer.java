/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author maikel
 */
public class SharedBuffer {
    private ArrayList<Character> characters;
    
    public SharedBuffer(ArrayList<Character>characters){
        this.characters=characters;
    }
    
    public synchronized void comparator(int order){
        int size=characters.get(0).size;
        int xColision, yColision;
        int direccion=0;
        for (int i = 0; i < characters.size(); i++) {
            xColision = (int) characters.get(i).getX();
            yColision = (int) characters.get(i).getY();
            direccion=characters.get(order).direction;
            switch (direccion) {
                case 3:
                    yColision += size;
                    break;
                case 1:
                    yColision -= size;
                    break;
                case 2:
                    xColision -= size;
                    break;
                case 4:
                    xColision += size;
                    break;
                default:
                    break;
            } // switch
            
            
            if (direccion!=characters.get(i).direction && characters.get(order).getX() == xColision && (int) characters.get(order).getY() == yColision) {
//                System.err.println(order);
                characters.get(i).crash=true;
                characters.get(order).crash=true;
                
            }
        } //for
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
    
    
}
