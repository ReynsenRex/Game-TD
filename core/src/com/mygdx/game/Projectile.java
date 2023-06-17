package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Projectile {
    public Sprite sprite;
    public float x;
    public float y;
    public float speed;
    public float directionX;
    public float directionY;

    public Projectile(Texture texture) {
        sprite = new Sprite(texture);
        speed = 5.0f; // Adjust the bullet speed as desired
    }

    public void update(float deltaTime) {
        x += directionX * speed * deltaTime;
        y += directionY * speed * deltaTime;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}

