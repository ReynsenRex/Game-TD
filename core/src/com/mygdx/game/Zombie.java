package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Zombie {
    public Vector2 position;
    public Sprite sprite;
    public boolean alive = true;
    Texture zombie_texture;

    public Zombie() {
        zombie_texture = new Texture(Gdx.files.internal("speedZombieRight.png"));
        sprite = new Sprite();
        sprite.setScale(1);

    }

    public void Draw(SpriteBatch batch) {
        Random rand = new Random();
        position.x = rand.nextInt(1920);
        position.y = rand.nextInt(1080);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);

    }
}
