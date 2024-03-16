package org.example.sortinganimations;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button vet[];
    private int TL = 5;

    private boolean animationIsRunning = false;

    public static void main(String[] args)
    {
        launch(args);
    }

    private int parseInt(String num){
        return Integer.parseInt(num);
    }

    public void shell_sort() throws InterruptedException {
        int dist = 1, j, aux;
        while(dist < TL){
            dist = dist * 3 + 1;
        }
        dist = dist / 3;

        while(dist > 0){
            for (int i = dist; i < TL; i++){
                j = i;
                aux = parseInt(vet[j].getText());
                while(j-dist >= 0 && aux < parseInt(vet[j-dist].getText())){
                    move_botoes(j-dist, j);
                    j-=dist;
                }
            }
            dist = dist/3;
        }
    }
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10); botao_inicio.setLayoutY(100);
        botao_inicio.setText("Shell sort");
        botao_inicio.setOnAction(e->{
            try {
                shell_sort();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        pane.getChildren().add(botao_inicio);
        vet = new Button[TL];
        for(int i = 0; i<TL; i++){
            vet[i] = new Button(new String(String.valueOf(new Random().nextInt(50))));
            vet[i].setLayoutX(100 + (80 * i));
            vet[i].setLayoutY(200 + (20));
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setFont(new Font(14));
            pane.getChildren().add(vet[i]);
        }
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    public void move_botoes(int j, int k)
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() + 5));
                    Platform.runLater(() -> vet[k].setLayoutY(vet[k].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int dist = k - j;
                for (int i = 0; i < 16 * dist; i++) {
                    Platform.runLater(() -> vet[j].setLayoutX(vet[j].getLayoutX() + 5));
                    Platform.runLater(() -> vet[k].setLayoutX(vet[k].getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() - 5));
                    Platform.runLater(() -> vet[k].setLayoutY(vet[k].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Button aux = vet[j];
                vet[j] = vet[k];
                vet[k] = aux;
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}