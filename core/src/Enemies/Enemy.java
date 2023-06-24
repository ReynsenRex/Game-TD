package Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Enemy {
    protected Texture texture;
    protected Sprite sprite;
    protected float x, y;
    protected boolean moving;
    protected int health;

    public Enemy(Texture texture, float x, float y) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        this.moving = false;
        this.health = 100; // Default health
    }

    // Add any common methods or attributes here
    // ...

    public void render(SpriteBatch batch) {
        // Render the enemy sprite
        sprite.setPosition(x, y);
        sprite.draw(batch);

        // Render additional enemy-specific details
        // ...
    }

    public abstract long generateNextSpawnTime();
    public abstract void spawnZombie();
    public abstract void render();



    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
