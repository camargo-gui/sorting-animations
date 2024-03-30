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
    Button botao_shell;
    Button botao_counting;
    private Button vet[];
    private Button BButton[];
    private Button CButton[];
    private int TL = 10;
    private VBox codeContainer = new VBox(5);
    private int [] inMemory = new int[1000];
    private Task tasks[] = new Task[1000];
    private int TaskTL = 0;
    private Label [] LabelPosB = new Label[TL];
    private Label Label1 = new Label();
    private Label Label2 = new Label();
    private Label Label3 = new Label();
    private Label Label4 = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    private int parseInt(String num) {
        return Integer.parseInt(num);
    }

    private void renderCodeBox(String method){
        CodeBox codeBox = new CodeBox(method);
        String[] codeLines = codeBox.getCodeLines();
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

    public void setupCodeContainer(int frameHeight){
        Rectangle frame = new Rectangle();
        frame.setWidth(500);
        frame.setHeight(frameHeight);
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
    }


    public void setupShell(){
        renderCodeBox("ShellSort");
        setupCodeContainer(700);
        inicializarLabels("i", "j", "aux", "dist");
    }

    public void setupCounting(){
        renderCodeBox("CountingSort");
        setupCodeContainer(860);
        inicializarLabels("i", "major", "pos", "TL");
    }

    public void createButtonArray(Button [] vet, Label [] vetLabel, int TL, int height, boolean isRandom){
        for (int i = 0; i < TL; i++) {
            if(isRandom){
                vet[i] = new Button(String.valueOf(new Random().nextInt(10)));
            } else {
                vet[i] = new Button("0");
            }
            vet[i].setLayoutX(100 + (80 * i));
            vet[i].setLayoutY(height);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setFont(new Font(14));
            Label label = new Label();
            label.setText(String.valueOf("pos: " + i));
            label.setLayoutX(100 + (80 * i));
            label.setLayoutY(height + 40);
            vetLabel[i] = label;
            pane.getChildren().add(vet[i]);
            pane.getChildren().add(label);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        botao_shell = new Button();
        botao_shell.setLayoutX(100);
        botao_shell.setLayoutY(50);
        botao_shell.setText("Shell sort");
        botao_shell.setOnAction(e -> {
            try {
                setupShell();
                toInMemory();
                shell_sort();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        pane.getChildren().add(botao_shell);
        botao_counting = new Button();
        botao_counting.setLayoutX(100);
        botao_counting.setLayoutY(100);
        botao_counting.setText("Counting sort");
        botao_counting.setOnAction(e -> {
            setupCounting();
            toInMemory();
            counting_sort();
        });
        pane.getChildren().add(botao_counting);
        vet = new Button[TL];
        Label [] vetLabel = new Label[TL];
        createButtonArray(vet, vetLabel, TL,220, true);
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

    public void colorSuccess(Button [] vet){
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
        atribuiValorAoLabel(Label4, 1);
        highlightLine(1);
        int dist = 1, j, aux, aux2, acumulatedDist;
        while (dist < TL) {
            highlightLine(3);
            dist = dist * 3 + 1;
            highlightLine(4);
            atribuiValorAoLabel(Label4, dist * 3 + 1);
        }
        highlightLine(5);
        dist = dist / 3;
        highlightLine(6);
        atribuiValorAoLabel(Label4, dist);


        while (dist > 0) {
            highlightLine(8);
            highlightLine(9);
            atribuiValorAoLabel(Label1, dist);
            for (int i = dist; i < TL; i++) {
                highlightLine(9);
                vet = reorganizeVet();
                j = i;
                atribuiValorAoLabel(Label3, inMemory[i]);
                highlightLine(10);
                aux2 = inMemory[i];
                separaAux(j);
                acumulatedDist = 0;
                highlightLine(11);
                atribuiValorAoLabel(Label2, i);
                aux = j;
                highlightLine(12);
                colorir(j, j - dist, "yellow", vet);
                while (j - dist >= 0 && aux2 < inMemory[j- dist]) {
                    highlightLine(12);
                    colorir(aux, j - dist, "green", vet);
                    highlightLine(13);
                    andaComOAnterior(j-dist, dist);
                    inMemory[j] = inMemory[j - dist];
                    acumulatedDist += dist;
                    highlightLine(14);
                    atribuiValorAoLabel(Label2, j - dist);
                    j -= dist;
                }
                highlightLine(12);
                if(j-dist >= 0){
                    colorir(aux, j - dist, "red", vet);
                }
                highlightLine(16);
                inMemory[j] = aux2;
                retornaOAux(aux, acumulatedDist);
                atribuiValorAoLabel(Label1, i+1);
            }
            highlightLine(18);
            atribuiValorAoLabel(Label4, dist/3);
            dist = dist / 3;
        }
        colorSuccess(vet);
        runTasks();
    }

    public void counting_sort(){
        highlightLine(0);
        atribuiValorAoLabel(Label4, TL);
        BButton = new Button[100]; CButton = new Button[100];
        Label [] vetLabelC = new Label[TL];
        highlightLine(2);
        int major = 0, pos, posMaior = 0, j;
        atribuiValorAoLabel(Label2, major);

        highlightLine(3);
        atribuiValorAoLabel(Label1, 0);
        for (int i = 0; i < TL; i++){
            highlightLine(4);
            colorir(i, posMaior, "yellow", vet);
            if (inMemory[i] > major){
                major = inMemory[i];
                posMaior = i;
                highlightLine(5);
                colorir(i, posMaior, "green", vet);
                atribuiValorAoLabel(Label2, major);
            }
            highlightLine(3);
            atribuiValorAoLabel(Label1, i + 1);
        }

        highlightLine(8);
        int [] B = new int[major + 1];

        createButtonArrayTask(BButton, LabelPosB, major, 400);
        createButtonArrayTask(CButton, vetLabelC, TL - 1, 600);

        highlightLine(10);
        for(int i = 0; i < TL; i++){
            atribuiValorAoLabel(Label1, i);
            pos = inMemory[i];
            B[pos] = B[pos] + 1;
            highlightLine(11);
            tripleColor(vet,i, LabelPosB, pos, BButton, pos );
            incrementValue(BButton, i);
            highlightLine(10);
        }

        highlightLine(14);
        for(int i = 1; i < major + 1; i++){
            atribuiValorAoLabel(Label1, i);
            highlightLine(15);
            colorir(i, i, "yellow", BButton);
            acumulate(BButton, i);
            B[i] = B[i] + B[i - 1];
            highlightLine(14);
        }

        highlightLine(18);
        atribuiValorAoLabel(Label1, TL -1);
        for(int i = TL - 1; i >= 0; i--){
            j = inMemory[i];
            pos = B[j];
            atribuiValorAoLabel(Label3, pos - 1);
            highlightLine(19);
            tripleColor(vet, i, LabelPosB, j, CButton, pos - 1);
            subtractValue(BButton, j);
            highlightLine(20);
            setNewValue(CButton, pos - 1, inMemory[i]);
            highlightLine(21);
            B[j] = B[j] - 1;
            highlightLine(18);
            atribuiValorAoLabel(Label1, i - 1);
        }

        remove(BButton, major + 1);

        highlightLine(23);
        for (int i = 0; i < TL; i++){
            fromCToA(CButton, i);
        }

        colorSuccess(CButton);


        highlightLine(24);
        runTasks();
    }

    public void remove(Button [] vet, int TL){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                for (int i = 0; i < TL; i++) {
                    int finalI = i;
                    Platform.runLater(() -> {
                        pane.getChildren().remove(vet[finalI]);
                        pane.getChildren().remove(LabelPosB[finalI]);
                    });
                }
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void fromCToA(Button [] C, int pos){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                for (int i = 0; i < 76; i++) {
                    Platform.runLater(() -> C[pos].setLayoutY(C[pos].getLayoutY() - 5));
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

    public void subtractValue(Button [] vet, int pos){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    int value = parseInt(vet[pos].getText());
                    value--;
                    vet[pos].setText(String.valueOf(value));
                });
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void setNewValue(Button [] vet, int pos, int value){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    vet[pos].setText(String.valueOf(value));
                });
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void tripleColor(Button [] A, int i, Label [] B, int j, Button [] C, int k){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    A[i].setStyle("-fx-background-color: yellow");
                    B[j].setStyle("-fx-background-color: yellow");
                    C[k].setStyle("-fx-background-color: yellow");
                });
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    A[i].setStyle("-fx-background-color: #E9E9E9");
                    B[j].setStyle("-fx-background-color: #E9E9E9");
                    C[k].setStyle("-fx-background-color: #E9E9E9");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void acumulate(Button [] arrayb, int i){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                       int value = parseInt(arrayb[i].getText()) + parseInt(arrayb[i - 1].getText());
                       arrayb[i].setText(String.valueOf(value));
                });
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void incrementValue(Button [] arrayb, int i){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    int pos = parseInt(vet[i].getText());
                    int value = parseInt(arrayb[pos].getText());
                    value++;
                    arrayb[pos].setText(String.valueOf(value));

                });
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void createButtonArrayTask(Button [] vet, Label [] vetLabel, int length, int height){
        Task <Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    createButtonArray(vet, vetLabel, length + 1, height, false);
                });
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void colorir(int i, int j, String color, Button [] vet) {
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
                vet[i].setStyle("-fx-background-color: #E9E9E9");
                vet[j].setStyle("-fx-background-color: #E9E9E9");
                return null;
            }
        };
        tasks[TaskTL] = task;
        TaskTL++;
    }

    public void colorirLabel(int i, String color, Label [] vet) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                vet[i].setStyle("-fx-background-color: " + color);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                vet[i].setStyle("-fx-background-color: #E9E9E9");
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

    private void inicializarLabels(String label1, String label2, String label3, String label4){
        Label1.setFont(new Font(20));
        Label1.setLayoutY(350);
        Label1.setLayoutX(1550);
        Label1.setText(label1 + " = 0");
        Label2.setFont(new Font(20));
        Label2.setLayoutY(400);
        Label2.setLayoutX(1550);
        Label2.setText(label2 + " = 0");
        Label3.setFont(new Font(20));
        Label3.setLayoutY(450);
        Label3.setLayoutX(1550);
        Label3.setText(label3 + " = 0");
        Label4.setFont(new Font(20));
        Label4.setLayoutY(500);
        Label4.setLayoutX(1550);
        Label4.setText(label4 + " = 0");
        pane.getChildren().add(Label1);
        pane.getChildren().add(Label2);
        pane.getChildren().add(Label3);
        pane.getChildren().add(Label4);
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