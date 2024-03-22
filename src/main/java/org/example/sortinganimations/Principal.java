package org.example.sortinganimations;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button vet[];
    private int TL = 10;
    private VBox codeContainer = new VBox(5);
    private int [] inMemory = new int[1000];
    private Task tasks[] = new Task[1000];
    private int TaskTL = 0;
    private Label LabelI = new Label();
    private Label LabelJ = new Label();
    private Label LabelAux = new Label();
    private Label LabelDist = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    private int parseInt(String num) {
        return Integer.parseInt(num);
    }

    private void renderCodeBox(){
        String [] codeLines = {
                "public void shell_sort() {", //0
                "    int dist = 1, i, j, aux;", //1
                "", //2
                "    while (dist < TL) {", //3
                "        dist = dist * 3 + 1;", //4
                "    }", //5
                "    dist /= 3;", //6
                "", //7
                "    while (dist > 0) {", //8
                "        for (i = dist; i < TL; i++) {", //9
                "            aux = array[i];", //10
                "            j = i;", //11
                "            while (j - dist >= 0 && aux < array[j - dist]) {", //12
                "                array[j] = array[j - dist];", //13
                "                j = j - dist;", //14
                "            }", //15
                "            array[j] = aux;", //16
                "        }", //17
                "        dist /= 3;", //18
                "    }", //19
                "}" //20
        };

        for(String line : codeLines){
            Label label = new Label(line);
            label.setFont(new Font(20));
            label.setStyle("-fx-text-fill: #FFFFFF");
            codeContainer.getChildren().add(label);
        }
    }

    private void clearHighlight(){
        for(int i = 0; i < codeContainer.getChildren().size(); i++){
            Label label = (Label) codeContainer.getChildren().get(i);
            label.setStyle("-fx-text-fill: #FFFFFF");
        }
    }

    private void highlightLine(int line){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                clearHighlight();
                Label label = (Label) codeContainer.getChildren().get(line);
                label.setStyle("-fx-text-fill: #FFFF00");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            };
        };

        tasks[TaskTL] = task;
        TaskTL++;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        botao_inicio = new Button();
        botao_inicio.setLayoutX(100);
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
            vet[i] = new Button(new String(String.valueOf(new Random().nextInt(25))));
            vet[i].setLayoutX(100 + (80 * i));
            vet[i].setLayoutY(200 + (20));
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setFont(new Font(14));
            pane.getChildren().add(vet[i]);
        }
        renderCodeBox();
        Rectangle frame = new Rectangle();
        frame.setWidth(500);
        frame.setHeight(700);
        frame.setFill(Color.BLACK);
        frame.setLayoutX(950);
        frame.setLayoutY(80);
        frame.setArcWidth(20);
        frame.setArcHeight(20);
        codeContainer.setLayoutX(0);
        codeContainer.setLayoutY(0);
        Group group = new Group();
        group.getChildren().addAll(frame, codeContainer);
        codeContainer.setLayoutX(1000);
        codeContainer.setLayoutY(100);
        pane.getChildren().add(group);
        inicializarLabels();
        Scene scene = new Scene(pane, 1280, 1080);
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

    public void colorSuccess(){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                for (int i = 0; i < TL; i++) {
                    vet[i].setStyle("-fx-background-color: green");
                }
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void shell_sort() throws InterruptedException {
        highlightLine(1);
        int dist = 1, j, aux, aux2, acumulatedDist;
        while (dist < TL) {
            highlightLine(3);
            dist = dist * 3 + 1;
            highlightLine(4);
            atribuiValorAoLabel(LabelDist, dist * 3 + 1);
        }
        highlightLine(5);
        dist = dist / 3;
        highlightLine(6);
        atribuiValorAoLabel(LabelDist, dist);


        while (dist > 0) {
            highlightLine(8);
            highlightLine(9);
            atribuiValorAoLabel(LabelI, dist);
            for (int i = dist; i < TL; i++) {
                highlightLine(9);
                vet = reorganizeVet();
                j = i;
                atribuiValorAoLabel(LabelAux, inMemory[i]);
                highlightLine(10);
                aux2 = inMemory[i];
                separaAux(j);
                acumulatedDist = 0;
                highlightLine(11);
                atribuiValorAoLabel(LabelJ, i);
                aux = j;
                highlightLine(12);
                colorir(j, j - dist, "yellow");
                while (j - dist >= 0 && aux2 < inMemory[j- dist]) {
                    highlightLine(12);
                    colorir(aux, j - dist, "green");
                    highlightLine(13);
                    andaComOAnterior(j-dist, dist);
                    inMemory[j] = inMemory[j - dist];
                    acumulatedDist += dist;
                    highlightLine(14);
                    atribuiValorAoLabel(LabelJ, j - dist);
                    j -= dist;
                }
                highlightLine(12);
                if(j-dist >= 0){
                    colorir(aux, j - dist, "red");
                }
                highlightLine(16);
                inMemory[j] = aux2;
                retornaOAux(aux, acumulatedDist);
                atribuiValorAoLabel(LabelI, i+1);
            }
            highlightLine(18);
            atribuiValorAoLabel(LabelDist, dist/3);
            dist = dist / 3;
        }
        colorSuccess();
        runTasks();
    }

    public void colorir(int i, int j, String color) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                vet[i].setStyle("-fx-background-color: " + color);
                vet[j].setStyle("-fx-background-color: " + color);
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
                        Thread.sleep(25);
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
                for (int i = 0; i < 20; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() - 5));
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16 * dist; i++) {
                    Platform.runLater(() -> vet[j].setLayoutX(vet[j].getLayoutX() + 5));
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 20; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() + 5));
                    try {
                        Thread.sleep(25);
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
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 20; i++) {
                    Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() - 5));
                    try {
                        Thread.sleep(25);
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

    private void inicializarLabels(){
        LabelI.setFont(new Font(20));
        LabelI.setLayoutY(350);
        LabelI.setLayoutX(850);
        LabelI.setText("i = 0");
        LabelJ.setFont(new Font(20));
        LabelJ.setLayoutY(400);
        LabelJ.setLayoutX(850);
        LabelJ.setText("j = 0");
        LabelAux.setFont(new Font(20));
        LabelAux.setLayoutY(450);
        LabelAux.setLayoutX(850);
        LabelAux.setText("aux = 0");
        LabelDist.setFont(new Font(20));
        LabelDist.setLayoutY(500);
        LabelDist.setLayoutX(850);
        LabelDist.setText("dist = 1");
        pane.getChildren().add(LabelI);
        pane.getChildren().add(LabelJ);
        pane.getChildren().add(LabelAux);
        pane.getChildren().add(LabelDist);
    }

    private void atribuiValorAoLabel(Label label, int valor){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                String textoAtual = label.getText();
                String[] partes = textoAtual.split("=");
                Platform.runLater(() -> label.setText(partes[0].trim() + " = " + valor));
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
}