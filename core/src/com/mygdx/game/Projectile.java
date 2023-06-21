package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.util.ArrayList;

public class Projectile {
    public Sprite sprite;
    public float x;
    public float y;
    public float speed;
    public float directionX;
    public float directionY;
    public Texture bulletTexture;

    public SpriteBatch batch;

    public void create() {
        bulletTexture = new Texture(Gdx.files.internal("fireBullet.png"));
        sprite = new Sprite(bulletTexture);
    }

    public void update(float deltaTime) {
        x += directionX * speed * deltaTime;
        y += directionY * speed * deltaTime;
    }

    public void render(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }
}
