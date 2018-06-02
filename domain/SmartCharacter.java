/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author maikel
 */
public class SmartCharacter extends Character {

    public SmartCharacter(int size, int i, int j, Block[][] start, SharedBuffer buffer, int order) {
        super(size, i, j, start, buffer, order);
        super.speed = 6;
        super.tipo = "S";

    }

    @Override
    public void run() {
        while (super.getFlag()) {
            direction = (int) (Math.random() * (5 - 1) + 1);
            if (next(direction)) {
                crash = false;
                int pixels = 0;
                try {
                    switch (direction) {
                        case 1:
                            while (pixels < size && !crash) {
                                Thread.sleep(speed);
                                //buff.colisionVs(order);
                                y += movement;
                                pixels++;
                            }
                            break;
                        case 2:
                            while (pixels < size && !crash) {
                                Thread.sleep(speed);
                                //    buff.colisionVs(order);
                                x += movement;
                                pixels++;
                            }
                            break;
                        case 3:
                            while (pixels < size && !crash) {
                                System.err.println("Speed:" + speed);
                                Thread.sleep(speed);
                                //buff.colisionVs(order);
                                y -= movement;
                                pixels++;
                            }
                            break;
                        case 4:
                            while (pixels < size && !crash) {
                                System.err.println("Speed:" + speed);
                                Thread.sleep(speed);
                                //buff.colisionVs(order);
                                x -= movement;
                                pixels++;
                            }
                            break;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Character.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                if (!crash) {
                    metodoRandom(direction);
                }
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        switch (direction) {
            case 1:
                gc.setFill(Color.AQUA);
                break;
            case 2:
                gc.setFill(Color.RED);
                break;
            case 3:
                gc.setFill(Color.GREEN);
                break;
            default:
                gc.setFill(Color.BLUE);
                break;
        }

        gc.fillRect(x, y, size, size);
    }
}
