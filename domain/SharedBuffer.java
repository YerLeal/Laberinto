/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author maikel
 */
public class SharedBuffer {

    private ArrayList<Character> characters;
//    private ArrayList<Item> items;
    
    public SharedBuffer(ArrayList<Character> characters) {
//        this.items=items;
        this.characters = characters;
    }

    public synchronized boolean colisionVs(int order) {
        int size = characters.get(0).size;
        int xC, yC, xMe, yMe;
        int dirMe = characters.get(order).direction;
        int dirO;
        xMe = characters.get(order).getX();
        yMe = characters.get(order).getY();
        int aux;
        if (dirMe == 1 || dirMe == 2) {
            aux = 10;
        } else {
            aux = -10;
        }

        if (dirMe == 1 || dirMe == 3) {
            yMe += aux;
        } else {
            xMe += aux;
        }
        Rectangle yo = new Rectangle(xMe, yMe, size, size);
        for (int i = 0; i < characters.size(); i++) {
            xC = characters.get(i).getX();
            yC = characters.get(i).getY();
            dirO = characters.get(i).direction;
            Rectangle elOtro = new Rectangle(xC, yC, size, size);
            if (i != order && elOtro.intersects(yo) && characters.get(i).getFlag()) {
                if (characters.get(order).oposDir(dirMe) == dirO ) {
                    if (characters.get(order).crash && !characters.get(i).crash) {
                        characters.get(order).crash = false;
                        characters.get(i).crash = true;
                    } else if (characters.get(i).crash && !characters.get(order).crash) {
                        characters.get(i).crash = false;
                        characters.get(order).crash = true;
                    } else if(characters.get(i).crash && characters.get(order).crash){
                            characters.get(i).crash = false;
                        characters.get(order).crash = false;
                    }else {
                        characters.get(order).crash = true;
                        characters.get(i).crash = true;
                    }
                    return true;
                } else {
                    System.err.println("otro" + order);
                    characters.get(order).movement = 0;
                    return true;
                }
            }
        } //for
        characters.get(order).movement = 1;
        return false;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }
    
//    public synchronized void itemColision(int order){
//        int size = characters.get(0).size;
//        int xC, yC, xMe, yMe;
//        int dirMe = items.get(order).direction;
//        xMe = items.get(order).getX();
//        yMe = items.get(order).getY();
//        int aux;
//        if (dirMe == 1 || dirMe == 2) {
//            aux = 10;
//        } else {
//            aux = -10;
//        }
//
//        if (dirMe == 1 || dirMe == 3) {
//            yMe += aux;
//        } else {
//            xMe += aux;
//        }
//        Rectangle yo = new Rectangle(xMe, yMe, size, size);
//        for (int i = 0; i < characters.size(); i++) {
//            xC = characters.get(i).getX();
//            yC = characters.get(i).getY();
//            Rectangle elOtro = new Rectangle(xC, yC, size, size);
//            if ( elOtro.intersects(yo)) {
//                System.out.println("Entra?");
//                if(characters.get(i).getTipo().equals("S")){  
//                    characters.get(i).speed-=1;
//                    items.get(order).setFlag(false);
//                }else if(characters.get(i).getTipo().equals("F")){
//                    items.get(order).setFlag(false);
//                }
//            }
//        }
//    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

//    public ArrayList<Item> getItems() {
//        return items;
//    }
    

}
