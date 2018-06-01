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

    private ArrayList<Character> lista = new ArrayList<>();
    private final int WIDTH = 1360;
    private final int HEIGHT = 720;
    private Canvas canvas;
    private Pane pane;
    private GraphicsContext gc;
    private FileChooser fileChooserImage;
    private Logica logica;
    private boolean bol = false;
    private Thread thread;
    private Character c1, c2, c3;
    private Item i1;
    Block[][] maze;
    int size;
    private Runnable hilos = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < lista.size(); i++) {

                try {
                    buffer.getCharacters().add(lista.get(i));
                    lista.get(i).start();
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Laberinto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

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
    private SharedBuffer buffer = new SharedBuffer(new ArrayList<>(), new ArrayList<>());

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
                c1 = new FastCharacter(logica.getSize(), logica.ini(), buffer, 0);
                for (int i = 0; i < 5; i++) {
                    System.err.println("lista" + i);
                    if (i < 2) {
                        lista.add(new SmartCharacter(logica.getSize(), logica.ini(), buffer, i));

                    } else if (i < 4) {
                        lista.add(new FastCharacter(logica.getSize(), logica.ini(), buffer, i));
                    } else {
                        lista.add(new FuriousCharacter(logica.getSize(), logica.ini(), buffer, i));
                    }

                }

                thread.start();
                new Thread(hilos).start();

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
        for(int i=0;i<lista.size();i++){
            lista.get(i).draw(gc);
        }

    }

    public static void main(String[] args) {
        launch(args);
    } // main
}
