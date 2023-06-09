package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Zombie {
    private float stateTime; // Animation time for the zombie
    public Texture texture;
    public Sprite sprite;
    public float x;
    public float y;
    public float targetX;
    public float targetY;
    public boolean moving;
    public int health;

    public Zombie() {
        health = 100; // Set default health to 100
    }

    // Other methods and constructors

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

}
