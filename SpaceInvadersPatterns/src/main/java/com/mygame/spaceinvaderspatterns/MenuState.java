/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author YOUSSEF
 */

public class MenuState implements GameState {

    @Override
    public void update() {
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);
        
        gc.setFill(Color.WHITE);
        gc.fillText("SPACE INVADERS - MENU", 350, 200);
        gc.fillText("Appuyez sur ENTER pour jouer", 330, 300);
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            GameEngine.getInstance().setState(new PlayingState());
        }
    }
}