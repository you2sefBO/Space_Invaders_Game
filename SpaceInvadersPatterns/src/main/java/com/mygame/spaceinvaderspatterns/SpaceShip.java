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

public class SpaceShip implements Ship {
    private double x;
    private double y;
    private double speed = 5.0;

    public SpaceShip() {
        this.x = 375; 
        this.y = 550; 
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIMEGREEN);
        double[] xPoints = {x, x + 25, x + 50};
        double[] yPoints = {y + 30, y, y + 30};
        gc.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void moveLeft() {
        if (x > 0) x -= speed;
    }

    @Override
    public void moveRight() {
        if (x < 750) x += speed; 
    }
    
    @Override
    public Projectile shoot() {
        return new Projectile(x + 22, y - 10, false);
    }
    
    @Override
    public double getX() { return x; }
    @Override
    public double getY() { return y; }
}