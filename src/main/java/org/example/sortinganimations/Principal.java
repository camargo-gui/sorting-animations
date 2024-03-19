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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button vet[];
    private int TL = 10;

    private int [] inMemory = new int[1000];

    private Task tasks[] = new Task[1000];
    private int TaskTL = 0;

    public static void main(String[] args) {
        launch(args);
    }

    private int parseInt(String num) {
        return Integer.parseInt(num);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(100);
        botao_inicio.setText("Shell sort");
        botao_inicio.setOnAction(e -> {
            try {
                toInMemory();
                shell_sort();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        pane.getChildren().add(botao_inicio);
        vet = new Button[TL];
        for (int i = 0; i < TL; i++) {
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

    public void toInMemory() {
        for (int i = 0; i < TL; i++) {
            inMemory[i] = parseInt(vet[i].getText());
        }
    }

    public Button[] reorganizeVet() {
        Arrays.sort(vet, Comparator.comparingDouble(Button::getLayoutX));
        return vet;
    }

    public void shell_sort() throws InterruptedException {
        int dist = 1, j, aux, aux2, acumulatedDist;
        while (dist < TL) {
            dist = dist * 3 + 1;
        }
        dist = dist / 3;

        while (dist > 0) {
            for (int i = dist; i < TL; i++) {
                vet = reorganizeVet();
                j = i;
                aux2 = inMemory[i];
                acumulatedDist = 0;
                aux = j;
                colorir(j, j - dist);
                separaAux(j);
                while (j - dist >= 0 && aux2 < inMemory[j- dist]) {
                    andaComOAnterior(j-dist, dist);
                    inMemory[j] = inMemory[j - dist];
                    acumulatedDist += dist;
                    j -= dist;
                }
                inMemory[j] = aux2;
                retornaOAux(aux, acumulatedDist);
            }
            dist = dist / 3;
        }
        runTasks();
    }

    public void colorir(int i, int j) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                vet[i].setStyle("-fx-background-color: green");
                vet[j].setStyle("-fx-background-color: green");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                vet[i].setStyle("-fx-background-color: #FFFFFF");
                vet[j].setStyle("-fx-background-color: #FFFFFF");
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void separaAux(int j) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call () {
                for (int i = 0; i < 20; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void andaComOAnterior(int currentJ, int dist) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                int j = currentJ;
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16 * dist; i++) {
                    Platform.runLater(() -> vet[j].setLayoutX(vet[j].getLayoutX() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                vet = reorganizeVet();
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void retornaOAux (int currentJ, int dist) {
        Task<Void> task = new Task<Void>() {
            int j = currentJ;
            @Override
            protected Void call () {
                for (int i = 0; i < 16 * dist; i++) {
                    Platform.runLater(() -> vet[j].setLayoutX(vet[j].getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 20; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                vet = reorganizeVet();
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    private void runTasks(){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                for (int i = 0; i < TaskTL; i++) {
                    tasks[i].run();
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
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
        tasks[TaskTL] = task;
        TaskTL++;
    }
}