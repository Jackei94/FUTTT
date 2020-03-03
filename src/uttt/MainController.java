/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uttt;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import uttt.field.IField;
import uttt.game.GameManager;
import uttt.game.GameState;
import uttt.move.Move;
import uttt.game.IGameState;
import uttt.move.IMove;

/**
 *
 * @author Charlotte
 */
public class MainController implements Initializable {

    private GameManager gm;
    private IGameState gameState;
    
    private TilePane[][] tilePanes = new TilePane[3][3];    
    
    @FXML
    private AnchorPane mainPane;
    @FXML
    private GridPane gridBoard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameState = new GameState();

        gm = new GameManager(gameState);
        //createAllCells();
        populateMicroboards();
        updateBoard();
    }

    private void populateMicroboards() {
        
        // populate each microboard with buttons
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                // initialize tilepane
                tilePanes[x][y] = new TilePane();
                        
                createMicroboard(x, y);
                gridBoard.add(tilePanes[x][y], x, y);
            }
        }
    }
    
    private void updateBoard() {
        highlightActiveMicroboards();
    }
    
    /**
     * Highlight those macroboards that are avaliable for play
     * 
     * It checks if which macroboards are avalible for play,
     * by checking which microboards are active (0,0 3,3 6,6)
     * and
     */
    private void highlightActiveMicroboards() {
        String[][] macroBoard = gameState.getField().getMacroboard();
        
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                
                if(macroBoard[x][y].equals(IField.AVAILABLE_FIELD)) {
                    tilePanes[x][y].getStyleClass().add("active");
                    //System.out.println("TilePane[" + x + "][" + y + "] is active");
                } else {
                    tilePanes[x][y].getStyleClass().clear();
                    //System.out.println("TilePane[" + x + "][" + y + "] is not");
                }
            }
        }
    }

    /**
     * Creates buttons and adds them to the given microboard
     *
     * @param mbX is the microboard x-coordinate in the overall board
     * @param mbY is the microboard y-coordinate in the overall board
     * @return tilepane with 3x3 buttons
     */
    private void createMicroboard(int microboardX, int microboardY) {

        // board (9x9) coordinates for the first button in given microboard (top left)
        int buttonX = microboardX*3;
        int buttonY = microboardY*3;
        
        // create the microboards 9 buttons
        for (int y = buttonY; y < buttonY+3; y++) {
            for (int x = buttonX; x < buttonX+3; x++) {

                UtttButton btn = new UtttButton();
                btn.setMove(new Move(x, y));

                btn.setOnMouseClicked(event -> {
                    UtttButton b = (UtttButton) event.getSource();
                    boolean isSucces = gm.updateGame(b.getMove());
                    if (isSucces) {
                        if (gameState.getMoveNumber() % 2 == 0) {
                            b.getStyleClass().add("o");
                        } else {
                            b.getStyleClass().add("x");
                        }
                        
                        updateBoard();
                    }
                });
                
                tilePanes[microboardX][microboardY].getChildren().add(btn);
            }
        }
    }

}
