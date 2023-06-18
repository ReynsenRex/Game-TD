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
    public Sprite sprite;
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
        visibilityTimer = 5.0f;
        isVisible = true;

        Sprite towerSprite = new Sprite(towerTexture);
        float towerScale = 0.6f;
        towerSprite.setPosition(x, y);
        towerSprite.setScale(towerScale);

        sprite = towerSprite; // Assign the new Sprite instance to the tower's sprite variable
    }


    public void render(SpriteBatch batch) {
        if (isVisible) {
            batch.begin();
            sprite.draw(batch);
            batch.end();
        }
    }
    public void attack() {
    }

    public void update(float deltaTime) {
        if (isVisible) {
            visibilityTimer -= deltaTime;
            if (visibilityTimer <= 0) {
                isVisible = false;
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

    public boolean isVisible() {
        return isVisible;
    }
}