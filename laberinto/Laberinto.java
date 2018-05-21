package laberinto;

import domain.Personaje;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
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
    private Personaje c1;

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

    public void init(Stage primaryStage) {
        thread = new Thread(this);
        thread.start();
        logica = new Logica();
        canvas = new Canvas(WIDTH, HEIGHT);
        Button button = new Button("verga");
        button.relocate(400, 400);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c1 = new Personaje(30, logica.ini());
                bol = true;
                c1.start();
            }
        });

        gc = canvas.getGraphicsContext2D();
        this.fileChooserImage = new FileChooser();
        this.fileChooserImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Extends", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.wbmp"));
        pane = new Pane(canvas);
        pane.getChildren().add(button);

        logica.selectImage(primaryStage, gc, fileChooserImage, canvas);
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
        int fps = 60;
        long time = 1000 / fps;
        try {
            while (true) {

                start = System.nanoTime();
                

                if (bol) {
                    draw(gc);
                }
               
                elapsed=System.nanoTime()-start;
                wait=time-elapsed/1000000;
                if(wait<0) wait=5;
                Thread.sleep(wait);
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Laberinto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void draw(GraphicsContext gc) throws InterruptedException {

        logica.drawMaze(gc);
        gc.setFill(Color.BLACK);
        gc.fillRect(this.c1.getX(), this.c1.getY(), 30, 30);
    }

    public static void main(String[] args) {
        launch(args);
    } // main
}
