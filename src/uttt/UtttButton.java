/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uttt;

import javafx.scene.control.Button;
import uttt.move.IMove;

/**
 *
 * @author Charlotte
 */
public class UtttButton extends Button {
    
    
        private IMove move;

    public IMove getMove() {
        return move;
    }

    public void setMove(IMove move) {
        this.move = move;
    }

}
