package Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Enemy {
    protected Texture texture;
    protected Sprite sprite;
    protected float x, y;
    protected boolean moving;
    protected int health;

    public Enemy(Texture texture, float x, float y, int health) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        this.moving = false;
        this.health = health; // Default health
    }

    public abstract long generateNextSpawnTime();

    public abstract void spawnZombie();

    public abstract void render();

}
