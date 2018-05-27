/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Point;
import java.awt.Rectangle;
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
        int xC, yC,xMe,yMe;
        xMe=characters.get(order).getX();
        yMe=characters.get(order).getY();
        Rectangle yo=new Rectangle(xMe, yMe, size, size);
        for (int i = 0; i < characters.size(); i++) {
            xC=characters.get(i).getX();
            yC=characters.get(i).getY();
            Rectangle elOtro=new Rectangle(xC, yC, size, size);
            if (i!=order && elOtro.intersects(yo)) {
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
