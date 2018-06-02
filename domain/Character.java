/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Rectangle;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author maikel
 */
public abstract class Character extends Thread {

    protected SharedBuffer buff;
    protected int xPos, yPos, x, y, size, speed, order;
    protected Block[][] matrixBlock;
    protected int direction, dirAux;
    protected boolean crash, wai = false;
    protected String tipo;
    public int movement = 1;

    public Character(int size, int i, int j, Block[][] start, SharedBuffer buffer, int order) {
        xPos = j;
        yPos = i;
        x = xPos * size;
        y = yPos * size;
        this.size = size;
        this.matrixBlock = start;
        this.buff = buffer;
        this.order = order;
    }

    private Boolean flag = true;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public SharedBuffer getBuff() {
        return buff;
    }

    public void setBuff(SharedBuffer buff) {
        this.buff = buff;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //selecciona la siguiente posición
    public boolean next(int dir) {

        if (((dir == 1 && dirAux == 3) || (dirAux == 1 && dir == 3)) && !encerrado() && !crash) {
            return false;
        } else if (((dir == 2 && dirAux == 4) || (dirAux == 2 && dir == 4)) && !encerrado() && !crash) {
            return false;
        }
        if (crash && dir == dirAux) {
            return false;
        }
        int aux;
        if (dir == 1 || dir == 2) {
            aux = 1;
        } else {
            aux = -1;
        }
        int xMe = x;
        int yMe = y;
        if (dir == 1 || dir == 3) {
            yMe += aux;
        } else {
            xMe += aux;
        }
        if (xMe < 0 || xMe + size > 1360 || yMe < 0 || yMe + size > 720) {
            return false;
        }
        Rectangle me = new Rectangle(xMe, yMe, size, size);
        for (int i = 0; i < matrixBlock.length; i++) {
            for (int j = 0; j < this.matrixBlock[0].length; j++) {
                Rectangle other = new Rectangle(i * size, j * size, size, size);
                if (other.intersects(me) && matrixBlock[i][j].getType().equalsIgnoreCase("wall")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void metodoRandom(int dir) {
        int aux;
        if (dir == 1 || dir == 2) {
            aux = 1;
        } else {
            aux = -1;
        }

        if (dir == 1 || dir == 3) {
            yPos += aux;
        } else {
            xPos += aux;
        }
        this.dirAux = dir;
    }

    public boolean encerrado() {
        int dir = oposDir(dirAux);
        boolean up = true, down = true, left = true, right = true;
        int xMe = x;
        int yMe = y;

        if (xMe < 0 || xMe + size > 1360 || yMe < 0 || yMe + 1 + size > 720) {
            up = false;
        } else if (xMe < 0 || xMe + size > 1360 || yMe - 1 < 0 || yMe + size > 720) {
            down = false;
        } else if (xMe < 0 || xMe + size + 1 > 1360 || yMe < 0 || yMe + size > 720) {
            right = false;
        } else if (xMe - 1 < 0 || xMe + size > 1360 || yMe < 0 || yMe + size > 720) {
            left = false;
        }
        int cont = 0;
        Rectangle meUp = new Rectangle(xMe, yMe + 1, size, size);
        Rectangle meDown = new Rectangle(xMe, yMe - 1, size, size);
        Rectangle meLeft = new Rectangle(xMe - 1, yMe, size, size);
        Rectangle meRigth = new Rectangle(xMe + 1, yMe, size, size);
        System.err.println(xPos + " " + yPos);
        for (int i = 0; i < matrixBlock.length; i++) {
            for (int j = 0; j < this.matrixBlock[0].length; j++) {
                Rectangle other = new Rectangle(i * size, j * size, size, size);
                if (i != xPos && j != yPos) {
                    if (up && other.intersects(meUp) && !matrixBlock[i][j].getType().equalsIgnoreCase("wall")) {
                        cont++;
                    }
                    if (down && other.intersects(meDown) && matrixBlock[i][j].getType().equalsIgnoreCase("wall")) {
                        cont++;
                    }
                    if (left && other.intersects(meLeft) && matrixBlock[i][j].getType().equalsIgnoreCase("wall")) {
                        cont++;
                    }
                    if (right && other.intersects(meRigth) && matrixBlock[i][j].getType().equalsIgnoreCase("wall")) {
                        cont++;
                    }
                }
            }
        }
        System.err.println(cont);
        if (cont == 1) {
            direction = dir;
            return true;
        } else {
            return false;
        }
    }

    public int oposDir(int dir) {
        switch (dir) {
            case 1:
                return 3;

            case 2:
                return 4;

            case 3:
                return 1;

            default:
                return 2;
        }
    }

    public String getTipo() {
        return tipo;
    }

    public abstract void draw(GraphicsContext gc);

}
