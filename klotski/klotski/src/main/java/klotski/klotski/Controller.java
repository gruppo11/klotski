package klotski.klotski;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class Controller implements Initializable {
    @FXML
    private Label lbl_movesCounter;
    @FXML
    private Button btn_reset;
    @FXML
    private Button btn_undo;
    @FXML
    private Canvas canvas_game = new Canvas();
    //serve per disegnare sul canvas
    private GraphicsContext gc;
    //oggetto che conterrà i dati dei blocchi
    private Board board;
    //contatore delle mosse
    private int moves;
    //cronologia delle mosse (una mossa sarà data da un indice e da una lettera che rappresenterà una direzione)
    private Vector<String> History;
    //coordinate di inizio pressione del mouse
    private Coordinates dragStart;
    //coordinate di rilascio del mouse
    private Coordinates dragEnd;
    private SaverLoader sl;
    private Solver solver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board = new Board(1);

        gc = canvas_game.getGraphicsContext2D();
        canvas_game.setOnMousePressed(e -> {dragStart.setX((int) e.getX()); dragStart.setY((int) e.getY());});
        canvas_game.setOnMouseReleased(e -> {dragEnd.setX((int) e.getX()); dragEnd.setY((int) e.getY()); evaluate();});

        Image img_undo = new Image(String.valueOf(Klotski.class.getResource("undo.png")));
        ImageView view_undo = new ImageView(img_undo);
        view_undo.setFitHeight(64);
        view_undo.setFitWidth(64);
        btn_undo.setGraphic(view_undo);

        btn_undo.setFocusTraversable(false);

        Image img_reset = new Image(String.valueOf(Klotski.class.getResource("reset.png")));
        ImageView view_reset = new ImageView(img_reset);
        view_reset.setFitHeight(64);
        view_reset.setFitWidth(64);
        btn_reset.setGraphic(view_reset);

        btn_reset.setFocusTraversable(false);

        reset();

        dragStart = new Coordinates(0,0);
        dragEnd = new Coordinates(0,0);

        sl = new SaverLoader();
        solver = new Solver(board);
    }
    //metodo che salva lo stato corrente della partita
    @FXML
    private void save(){
        if(sl.save(board.getConf(), History).equals("error")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your game could not be saved");
            alert.showAndWait();
        }
    }
    //metodo che recupera le informazioni dell'ultimo salvataggio
    @FXML
    private void load(){
        Vector<String> out = sl.load();
        if(out != null) {
            board = Board.strToBoard(out.lastElement(), Integer.parseInt(out.remove(0)));
            History = out;
            moves = out.size() - 1;

            if(moves != 0){
                if(btn_reset.isDisable() || btn_undo.isDisable()){
                    btn_reset.setDisable(false);
                    btn_undo.setDisable(false);
                }
            }

            lbl_movesCounter.setText(Integer.toString(moves));

            refresh();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your game could not be loaded");
            alert.showAndWait();
        }
    }

    @FXML
    private void changeConfiguration(){
        //disabilito le varie funzioni
        canvas_game.setDisable(true);
        btn_reset.setDisable(true);
        btn_undo.setDisable(true);
        //creo il contenitore dei bottoni per finestra di selezione
        Pane pane = new Pane();
        pane.setMinSize(159, 83);
        //creo e setto i bottoni
        Button conf1 = new Button("1");
        Button conf2 = new Button("2");
        Button conf3 = new Button("3");
        Button conf4 = new Button("4");
        Button quit = new Button("OK");
        conf1.setLayoutX(3);
        conf1.setLayoutY(2);
        conf1.setMinSize(75,25);
        conf2.setLayoutX(81);
        conf2.setLayoutY(2);
        conf2.setMinSize(75,25);
        conf3.setLayoutX(3);
        conf3.setLayoutY(29);
        conf3.setMinSize(75,25);
        conf4.setLayoutX(81);
        conf4.setLayoutY(29);
        conf4.setMinSize(75,25);
        quit.setLayoutX(3);
        quit.setLayoutY(56);
        quit.setMinSize(152, 25);
        //aggiungo i bottoni al pannello
        pane.getChildren().addAll(conf1, conf2, conf3, conf4, quit);
        //assemblo la finestra
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Select a Configuration");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {canvas_game.setDisable(false); btn_reset.setDisable(false); btn_undo.setDisable(false);});
        stage.setAlwaysOnTop(true);
        //setto le azioni dei bottoni
        conf1.setOnMouseClicked(e -> {gc.clearRect(0, 0, 400, 500); setMoveToZero(); board.setConfiguration1(); reset(); refresh();});
        conf2.setOnMouseClicked(e -> {gc.clearRect(0, 0, 400, 500); setMoveToZero(); board.setConfiguration2(); reset(); refresh();});
        conf3.setOnMouseClicked(e -> {gc.clearRect(0, 0, 400, 500); setMoveToZero(); board.setConfiguration3(); reset(); refresh();});
        conf4.setOnMouseClicked(e -> {gc.clearRect(0, 0, 400, 500); setMoveToZero(); board.setConfiguration4(); reset(); refresh();});
        quit.setOnMouseClicked(e -> {canvas_game.setDisable(false); if(moves != 0){btn_reset.setDisable(false); btn_undo.setDisable(false);} stage.close();});
    }
    @FXML
    private void quit(){
        javafx.application.Platform.exit();
    }
    @FXML
    private void reset(){
        //azzero il numero di mosse e resetto la cronologia
        setMoveToZero();
        History = new Vector<>();
        History.add(board.toString());
        //disabilito reset e undo
        btn_reset.setDisable(true);
        btn_undo.setDisable(true);
        //resetto la board
        board.reset();
        //ricarico
        refresh();
    }
    @FXML
    private void undo(){
        //estraggo la configurazione precendente
        board = Board.strToBoard(History.get(History.size() - 2), board.getConf());
        History.remove(History.size() - 1);
        //decremento le mosse e ricarico
        decMoves();
        refresh();
    }
    @FXML
    private void nextBestMove(){
        board = solver.getNextMove(board);
        History.add(board.toString());
        incMoves();
        refresh();
    }
    private void setMoveToZero(){
        moves = 0;
        lbl_movesCounter.setText("0");
    }

    private void incMoves(){
        moves++;
        //riabilito i bottoni reset e undo
        if(btn_reset.isDisable() || btn_undo.isDisable()){
            btn_reset.setDisable(false);
            btn_undo.setDisable(false);
        }
        //aggiorno la label delle mosse
        lbl_movesCounter.setText(Integer.toString(moves));
    }

    private void decMoves(){
        moves--;
        //se sono tornato allo stato iniziale disabilito undo e reset
        if(moves == 0){
            btn_reset.setDisable(true);
            btn_undo.setDisable(true);
        }
        //aggiorno la label delle mosse
        lbl_movesCounter.setText(Integer.toString(moves));
    }

    private void evaluate(){
        //chiedo alla board di fare la mossa
        String outcome = board.evaluate(dragStart, dragEnd);
        //se la mossa era valida controllo se ho vinto, altrimenti aggiorno mosse e cronologia
        if(outcome != null){
            if(outcome.equals("win"))
                win();
            else{
                incMoves();
                History.add(board.toString());
                refresh();
            }
        }
    }

    private void refresh(){
        //pulisco il canvas
        gc.clearRect(0, 0, 400, 500);

        gc.setFill(Color.GRAY);
        int blockwidth = 96;
        int blockheight = 96;
        //disegno i blocchi
        for(int i = 0; i < 10; i++){
            if(i == 4)
                blockheight = 196;
            if(board.getConf() == 1 || board.getConf() == 4)
                if(i == 8){
                    blockheight = 96;
                    blockwidth = 196;
                }
            if(board.getConf() == 2 || board.getConf() == 3)
                if(i == 7){
                    blockheight = 96;
                    blockwidth = 196;
                }
            if(i == 9){
                blockheight = 196;
                gc.setFill(Color.BLACK);
            }
            gc.strokeRoundRect(board.get(i).getX(), board.get(i).getY(), blockwidth, blockheight, 15, 15);
            gc.fillRoundRect(board.get(i).getX(), board.get(i).getY(), blockwidth, blockheight, 15, 15);
        }

        gc.setFill(Color.LIGHTGREEN);
        gc.strokePolygon(new double[]{100,100,300,300},new double[]{498,500,500,498},4);
        gc.fillPolygon(new double[]{100,100,300,300},new double[]{498,500,500,498},4);
    }

    private void win(){
        //disabilito le varie funzioni
        canvas_game.setDisable(true);
        btn_reset.setDisable(true);
        btn_undo.setDisable(true);
        //creo il contenitore dei bottoni della finestra di vittoria
        Pane pane = new Pane();
        pane.setMinSize(159, 83);
        //creo e setto i bottoni
        Label lbl_win = new Label("You won in " + lbl_movesCounter.getText() + " moves");
        Button playAgain = new Button("Play Again");
        Button quit = new Button("Quit");
        lbl_win.setLayoutX(3);
        lbl_win.setLayoutY(2);
        lbl_win.setMinSize(150, 25);
        lbl_win.setAlignment(Pos.CENTER);
        playAgain.setLayoutX(3);
        playAgain.setLayoutY(29);
        playAgain.setMinSize(150,25);
        quit.setLayoutX(3);
        quit.setLayoutY(56);
        quit.setMinSize(150,25);
        //aggiungo i bottoni al pannello
        pane.getChildren().addAll(lbl_win, playAgain, quit);
        //assemblo la finestra
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("You Won!");
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        //aggiungo le azioni ai bottoni
        playAgain.setOnMouseClicked(e -> {reset(); stage.close(); changeConfiguration();});
        quit.setOnMouseClicked(e -> quit());
    }
}