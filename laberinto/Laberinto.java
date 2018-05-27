package laberinto;

import domain.Block;
import domain.FastCharacter;
import domain.FuriousCharacter;
import domain.Character;
import domain.Item;
import domain.SharedBuffer;
import domain.SmartCharacter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Laberinto extends Application implements Runnable {

    private final int WIDTH = 1360;
    private final int HEIGHT = 720;
    private Canvas canvas;
    private Pane pane;
    private GraphicsContext gc;
    private FileChooser fileChooserImage;
    private Logica logica;
    private boolean bol = false;
    private Thread thread;
    private Character c1,c2,c3;
    private Item i1;
    Block[][] maze;
    int size;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Laberinto");
        init(primaryStage);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        primaryStage.resizableProperty().set(false);

        primaryStage.show();
    } // start
    private SharedBuffer buffer=new SharedBuffer(new ArrayList<>());
    public void init(Stage primaryStage) {
        thread = new Thread(this);
        
        logica = new Logica();
        canvas = new Canvas(WIDTH, HEIGHT);
        Button button = new Button("verga");
        button.relocate(400, 400);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                maze = logica.getMaze();
                size = logica.getSize();
                c1 = new SmartCharacter(logica.getSize(), logica.ini(),buffer,0);
                buffer.getCharacters().add(c1);
                //c2 = new SmartCharacter(logica.getSize(), logica.ini2(),buffer,2);
                c3 = new SmartCharacter(logica.getSize(), logica.ini3(),buffer,1);
                buffer.getCharacters().add(c3);
//                buffer.getCharacters().add(c2);
//                i1 = new Item(logica.getSize(), maze[1][3]);
                thread.start();
                c1.start();
                //c2.start();
                c3.start();
//                i1.start();
                
                
            }
        });

        gc = canvas.getGraphicsContext2D();
        this.fileChooserImage = new FileChooser();
        this.fileChooserImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Extends", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.wbmp"));
        pane = new Pane(canvas);
        pane.getChildren().add(button);

//        logica.selectImage(primaryStage, gc, fileChooserImage, canvas);
        logica.createMaze();
        logica.drawMaze(gc);

        this.canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getSource() == canvas) {
                    logica.cambiarTipo((int) event.getX(), (int) event.getY(), gc);
                    logica.imprimirTipo((int) event.getX(), (int) event.getY());
                }
            }
        });

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
    } // init

    @Override
    public void run() {
        long start;
        long elapsed;
        long wait;
        int fps = 30;
        long time = 1000 / fps;
        try {
            while (true) {

                start = System.nanoTime();
                elapsed = System.nanoTime() - start;
                wait = time - elapsed / 1000000;

                    draw(gc);
                
                Thread.sleep(wait);
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Laberinto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void draw(GraphicsContext gc) throws InterruptedException {
        
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].getType().equals("wall")) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(i * size, j * size, size, size);

                } else {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(i * size, j * size, size, size);
                }

            }
        }
        c1.draw(gc);
        //c2.draw(gc);
        c3.draw(gc);
//        i1.draw(gc);
    }

    public static void main(String[] args) {
        launch(args);
    } // main
}
