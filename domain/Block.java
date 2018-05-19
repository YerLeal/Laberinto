package domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Block {

    private int x, y, size;
    private String type;
    private ArrayList<Block> next;
    private BufferedImage image;

    public Block(BufferedImage image, int x, int y, int size, String type) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.size = size;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public ArrayList<Block> getNext() {
        return next;
    }

    public void setNext(ArrayList<Block> next) {
        this.next = next;
    }

    public void draw(GraphicsContext gc) {
        if (this.type.equals("wall")) {
            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(image, null));
            SnapshotParameters snapshot = new SnapshotParameters();
            gc.drawImage(imageView.snapshot(snapshot, null), (x * size), (y * size));
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(x * size, y * size, size, size);
        }
    }

    public boolean in(int xMouse, int yMouse) {
        return (xMouse >= this.x * size && xMouse <= this.x * size + this.size) && (yMouse >= this.y * size && yMouse <= this.y * size + this.size);
    }
    
    public boolean isClicked(int xMouse, int yMouse) {
        if ((xMouse >= this.x * this.size && xMouse <= this.x * this.size + this.size)
                && (yMouse >= this.y * this.size && yMouse <= this.y * this.size + this.size)) {
            return true;
        }
        return false;
    }

    public void setType(String type) {
        this.type = type;
    }

}
