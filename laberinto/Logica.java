package laberinto;

import domain.Block;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Logica {
    
    private final int WIDTH = 1360;
    private final int HEIGHT = 720;
    private int size;
    private Block maze[][];
    private BufferedImage image;
    
    public Logica(){
        getDificultad();
        this.maze = new Block[WIDTH / size][HEIGHT / size];
    }

    public void cambiarTipo(int x, int y, GraphicsContext gc){
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (this.maze[i][j].isClicked(x, y)) {
                    if(this.maze[i][j].getType().equals("wall")){
                        this.maze[i][j].setType("floor");
                    }else{
                        this.maze[i][j].setType("wall");
                    }
                    this.maze[i][j].draw(gc);
                    break;
                }
            }
        }
        imprimirTipo();
    }
    
    private void imprimirTipo(){
        for(int f=0; f<maze.length; f++){
            for(int c=0; c<maze[0].length; c++){
                System.out.print(f+", "+c+": "+maze[f][c].getType()+" - ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------");
    }
    
    public BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = newImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    public void selectImage(Stage primaryStage, GraphicsContext gc, FileChooser fileChooser, Canvas canvas) {
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                BufferedImage aux = ImageIO.read(file);
                this.image = resize(aux, WIDTH, HEIGHT);
            } catch (IOException ex) {
                Logger.getLogger(Laberinto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void createMaze() {
        BufferedImage aux;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                aux = this.image.getSubimage((i * size), (j * size), size, size);
                if ((i + j) % 2 == 0) {
                    maze[i][j] = new Block(aux, i, j, size, "wall");
                } else {
                    maze[i][j] = new Block(aux, i, j, size, "floor");
                }
            }
        }
    }

    private void getDificultad() {
        int aux = (int) (Math.random() * (3 - 1) + 1);
        switch (aux) {
            case 1:
                size = 120;
                break;
            case 2:
                size = 80;
                break;
            case 3:
                size = 40;
                break;
            default:
                break;
        }
    }

    public void drawMaze(GraphicsContext gc) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j].draw(gc);
            }
        }
    }
    
}
