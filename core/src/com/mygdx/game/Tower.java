package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Tower {
    private int damage;
    private int range;
    private int fireRate;
    public static Sprite sprite;
    public Texture towerTexture;

    private SpriteBatch batch;

    private float visibilityTimer; // Timer to control tower visibility
    private boolean isVisible; // Flag indicating tower visibility

    public Tower(int damage, int range, int fireRate) {
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
    }

    public Tower(SpriteBatch batch) {
        this.batch = batch;
    }

    public Tower() {
    }

    public void spawnTower(float x, float y) {
        towerTexture = new Texture(Gdx.files.internal("towerTexture.png"));
        visibilityTimer = 5.0f; // Set the visibility timer to 5 seconds
        isVisible = true; // Initially, tower is visible
        Tower tower = new Tower();
        Tower.sprite = new Sprite(towerTexture);

        float towerScale = 0.6f; // Adjust the scale factor as desired

        Tower.sprite.setPosition(x, y);
        Tower.sprite.setScale(towerScale);
    }

    public void render(SpriteBatch batch) {
        if (isVisible) {
            batch.begin();

            Tower.sprite.draw(batch);

            batch.end();
        }
    }
    public void attack() {
    }

    public void update(float deltaTime) {
        if (isVisible) {
            visibilityTimer -= deltaTime; // Decrease the visibility timer

            if (visibilityTimer <= 0) {
                isVisible = false; // If the timer reaches or goes below 0, hide the tower
            }
        }
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }
}
