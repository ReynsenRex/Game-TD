package com.mygdx.game;

import Enemies.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Zombie extends Enemy {
    public Vector2 position;
    public Sprite sprite;
    public boolean alive = true;
    Texture zombie_texture;

    public Zombie() {
        zombie_texture = new Texture(Gdx.files.internal("speedZombieRight.png"));
        sprite = new Sprite();
        sprite.setScale(1);

    }

    @Override
    public long generateNextSpawnTime() {
        return 0;
    }

    @Override
    public void spawnZombie() {

    }

    @Override
    public void render() {

    }

    @Override
    public int setHealth() {
        return 0;
    }

    @Override
    public int getHealth() {
        return 0;
    }

    public void Draw(SpriteBatch batch) {
        Random rand = new Random();
        position.x = rand.nextInt(1920);
        position.y = rand.nextInt(1080);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);

    }
}
