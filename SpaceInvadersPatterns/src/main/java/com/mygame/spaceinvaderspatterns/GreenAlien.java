/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author YOUSSEF
 */

public class GreenAlien extends Alien {
    public GreenAlien(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGREEN);
        gc.fillOval(x, y, width, height);
    }
}
