package laberinto;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Laberinto extends Application {

    private final int WIDTH = 1360;
    private final int HEIGHT = 720;
    private Canvas canvas;
    private Pane pane;
    private GraphicsContext gc;
    private FileChooser fileChooserImage;
    private Logica logica;

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
        logica = new Logica();
        canvas = new Canvas(WIDTH, HEIGHT);
        
        gc = canvas.getGraphicsContext2D();
        this.fileChooserImage = new FileChooser();
        this.fileChooserImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Extends", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.wbmp"));
        pane = new Pane(canvas);

        logica.selectImage(primaryStage, gc, fileChooserImage, canvas);
        logica.createMaze();
        logica.drawMaze(gc);
        
        this.canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getSource() == canvas) {
                logica.cambiarTipo((int)event.getX(), (int)event.getY(), gc);
                logica.imprimirTipo((int)event.getX(), (int)event.getY());
            }
        }
        });

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
    } // init

    public static void main(String[] args) {
        launch(args);
    } // main

} // fin de la clase
