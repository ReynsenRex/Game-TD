package Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
    protected Texture texture;
    protected Sprite sprite;
    protected float x, y, targetX, targetY;
    protected boolean moving;
    protected int health;
    protected Vector2 position;
    private Rectangle hitbox;

    public Enemy() {
        position = new Vector2();
    }


    public Enemy(Texture texture, float x, float y) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        this.moving = false;
        this.health = 0; // Default health
    }

    public abstract long generateNextSpawnTime();

    public abstract void spawnZombie();

    public abstract void render();

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract int setHealth();

    public abstract int getHealth();

    public abstract Rectangle getHitbox();
    public boolean hasReachedEdge(Boolean condition) {
        return condition;
    }


    public void takeDamage(int damage) {
    }
}
