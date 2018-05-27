/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author maikel
 */
public class SmartCharacter extends Character {

    public SmartCharacter(int size, Block start, SharedBuffer buffer, int order) {
        super(size, start, buffer, order);
        super.speed = 6;
    }

    @Override
    public void run() {
        while (flag) {
            direction = (int) (Math.random() * (5 - 1) + 1);

            if (next(direction)) {
//                System.err.println("Direction:"+direction+"Hilo:"+order);
                crash = false;
                try {
                    switch (direction) {
                        case 1:
                            while (currentBlock.in(x, y) && !crash) {
                                y += 1;
                                buff.comparator(order);
                                Thread.sleep(speed);
                            }
                            break;
                        case 2:
                            while (currentBlock.in(x, y) && !crash) {
                                x += 1;
                                buff.comparator(order);
                                Thread.sleep(speed);
                            }
                            break;
                        case 3:
                            while (currentBlock.in(x, y) && !crash) {
                                y -= 1;
                                buff.comparator(order);
                                Thread.sleep(speed);
                            }
                            break;
                        case 4:
                            while (currentBlock.in(x, y) && !crash) {
                                x -= 1;
                                buff.comparator(order);
                                Thread.sleep(speed);
                            }
                            break;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Character.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                currentBlock = nextBlock;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(new Image("/assets/luigi2.png"), x, y, (size-20), size);
    }
}
