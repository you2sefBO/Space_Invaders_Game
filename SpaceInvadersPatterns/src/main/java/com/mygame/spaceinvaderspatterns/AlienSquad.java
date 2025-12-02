/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
/**
 *
 * @author YOUSSEF
 */

public class AlienSquad extends Alien {
    private List<Alien> members = new ArrayList<>();

    public AlienSquad(double x, double y) {
        super(x, y);
    }

    @Override
    public void add(Alien alien) {
        members.add(alien);
    }

    @Override
    public void remove(Alien alien) {
        members.remove(alien);
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (Alien member : members) {
            member.draw(gc);
        }
    }

    @Override
    public void update() {
        for (Alien member : members) {
            member.update();
        }
    }

    public void checkWallCollision() {
        boolean hitWall = false;
        List<Alien> allAliens = getAllAliens();

        for (Alien a : allAliens) {
            if (a.getX() + a.getWidth() >= 800 && a.dx > 0) {
                hitWall = true;
                break;
            }
            if (a.getX() <= 0 && a.dx < 0) {
                hitWall = true;
                break;
            }
        }

        if (hitWall) {
            for (Alien a : allAliens) {
                a.descend();
                a.reverseDirection();
            }
        }
    }
    
    public boolean isEmpty() { return members.isEmpty(); }
    
    public List<Alien> getMembers() { return members; }

    private List<Alien> getAllAliens() {
        List<Alien> flatList = new ArrayList<>();
        for (Alien member : members) {
            if (member instanceof AlienSquad) {
                flatList.addAll(((AlienSquad) member).getAllAliens());
            } else {
                flatList.add(member);
            }
        }
        return flatList;
    }

    @Override
    public boolean checkCollision(Projectile p) {
        for (Alien member : members) {
            if (member.checkCollision(p)) return true;
        }
        return false;
    }

    public void removeDead() {
        members.removeIf(alien -> !alien.isAlive());
        for (Alien member : members) {
            if (member instanceof AlienSquad) ((AlienSquad) member).removeDead();
        }
        members.removeIf(alien -> (alien instanceof AlienSquad) && ((AlienSquad) alien).isEmpty());
    }
}
