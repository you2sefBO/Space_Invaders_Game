/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygame.spaceinvaderspatterns;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author YOUSSEF
 */

public class PlayingState implements GameState, GameObserver {

    private static final Logger logger = LogManager.getLogger(PlayingState.class);
    
    private Ship player;
    private AlienSquad fleet;
    private int currentScore = 0;
    private int level = 1;
    private Random random = new Random();
    
    private List<Projectile> projectiles = new ArrayList<>();
    private List<Bunker> bunkers = new ArrayList<>();

    public PlayingState() {
        this.player = new SpaceShip();
        this.fleet = new AlienSquad(0, 0);
        initBunkers();
        initLevel();
        
        ScoreManager.getInstance().addObserver(this);
        this.currentScore = ScoreManager.getInstance().getScore();
    }
    
    private void initBunkers() {
        bunkers.clear();
        double startX = 70;  
        double spacing = 200; 
        double y = 450;      

        for (int i = 0; i < 4; i++) {
            bunkers.add(new Bunker(startX + (i * spacing), y));
        }
    }
    
    private void initLevel() {
        fleet = new AlienSquad(0, 0); 
        int rows = Math.min(3 + (level - 1), 5); 
        
        int cols = Math.min(5 + (level - 1), 10);

        for (int row = 0; row < rows; row++) {
            AlienSquad rowSquad = new AlienSquad(0, 0);
            
            String type = (row % 2 == 0) ? "RED" : "GREEN";

            for (int col = 0; col < cols; col++) {
                double startX = (800 - (cols * 60)) / 2;
                double x = startX + (col * 60);
                double y = 50 + (row * 50);
                
                Alien alien = AlienFactory.createAlien(type, x, y);
                if (alien != null) rowSquad.add(alien);
            }
            fleet.add(rowSquad);
        }
        
        projectiles.clear();
        logger.info("Niveau " + level + " démarré avec " + rows + " lignes.");
    }

    @Override
    public void onScoreChanged(int newScore) {
        this.currentScore = newScore;
    }

    @Override
    public void update() {
        if (fleet.isEmpty()) {
            level++; 
            ScoreManager.getInstance().addPoints(500); 
            initLevel(); 
            return; 
        }
        
        fleet.checkWallCollision();
        fleet.update();
        
        if (checkInvasion()) {
            logger.info("INVASION RÉUSSIE ! Game Over.");
            GameEngine.getInstance().setState(new GameOverState());
            return;
        }

        if (random.nextDouble() < 0.02) { 
             double shootX = 50 + random.nextInt(700);
             double shootY = 100; 
             projectiles.add(new Projectile(shootX, shootY, true));
        }

        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.update();
            
            boolean hitSomething = false;
            
            for (Bunker b : bunkers) {
                if (b.checkCollision(p)) {
                    hitSomething = true;
                    break; // Un missile ne peut toucher qu'un objet
                }
            }
            
            if (hitSomething) {
                if (!p.isActive()) it.remove();
                continue; 
            }

            if (p.isEnemyShot()) {
                if (checkPlayerHit(p)) return; // Votre logique de mort joueur
            } else {
                fleet.checkCollision(p);
            }

            if (!p.isActive()) it.remove();
        }
        
        
        fleet.removeDead();
    }
    
    private boolean checkPlayerHit(Projectile p) {
         if (p.getX() < player.getX() + 50 && p.getX() + p.getWidth() > player.getX() &&
             p.getY() < player.getY() + 30 && p.getY() + p.getHeight() > player.getY()) {
             GameEngine.getInstance().setState(new GameOverState());
             return true;
         }
         return false;
    }
    
    private boolean checkInvasion() {
        return isAlienTooLow(fleet);
    }
    
    private boolean isAlienTooLow(Alien alien) {
        if (alien instanceof AlienSquad) {
            for (Alien member : ((AlienSquad) alien).getMembers()) {
                if (isAlienTooLow(member)) return true;
            }
        } else {
            if (alien.getY() > 500) return true; // 500 = hauteur du joueur environ
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);

        player.draw(gc);
        fleet.draw(gc);
        
        for (Bunker b : bunkers) {
            b.draw(gc);
        }
        for (Projectile p : projectiles) p.draw(gc);
        
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Consolas", 20));
        gc.fillText("SCORE: " + currentScore, 20, 30);
        gc.fillText("Vies: 1", 20, 60);
        gc.fillText("NIVEAU: " + level, 20, 90);
    }

    @Override
    public void handleInput(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) player.moveLeft();
        else if (event.getCode() == KeyCode.RIGHT) player.moveRight();
        else if (event.getCode() == KeyCode.B) {
             if (!(player instanceof ShieldDecorator)) {
                player = new ShieldDecorator(player);
                logger.info("Bouclier activé !");
            }
        }
        else if (event.getCode() == KeyCode.SPACE) {
             projectiles.add(player.shoot());
        }
    }
}