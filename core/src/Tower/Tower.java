package Tower;

import java.util.ArrayList;

import Enemies.Zombie;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Tower {
    private int damage;
    private int range;
    private int fireRate;
    public Sprite sprite;
    public Texture towerTexture;
    private SpriteBatch batch;
    private ArrayList<Projectile> projectiles;
    private Array<Zombie> zombies;
    private float visibilityTimer; // Timer to control tower visibility
    private boolean isVisible; // Flag indicating tower visibility

    public Tower(int damage, int range, int fireRate) {
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.projectiles = new ArrayList<>();
        this.zombies = new Array<>();
    }

    public Tower() {
        this.projectiles = new ArrayList<>();
        this.zombies = new Array<>();
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
            // Render each projectile
            for (Projectile projectile : projectiles) {
                projectile.render(batch);
            }
            sprite.draw(batch);

            batch.end();
        }
    }

    private float calculateDistance(float x1, float y1, float x2, float y2) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public void attack(float targetX, float targetY) {
        // Check if there are any zombies within range
        for (Zombie zombie : zombies) {
            float distance = calculateDistance(zombie.getX(), zombie.getY(), targetX, targetY);
            if (distance <= range) {
                // Create a new projectile
                Projectile projectile = new Projectile();
                projectile.create();
                projectile.x = sprite.getX();
                projectile.y = sprite.getY();
                projectile.directionX = (targetX - projectile.x) / distance;
                projectile.directionY = (targetY - projectile.y) / distance;
                projectiles.add(projectile);
            }
        }
    }

    public void update(float deltaTime) {
        if (isVisible) {
            visibilityTimer -= deltaTime;
            if (visibilityTimer <= 0) {
                isVisible = false;
            }
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                Projectile projectile = projectiles.get(i);
                projectile.update(deltaTime);
                // Remove projectiles that are off-screen
                if (projectile.x < 0 || projectile.x > Gdx.graphics.getWidth() ||
                        projectile.y < 0 || projectile.y > Gdx.graphics.getHeight()) {
                    projectiles.remove(i);
                }
            }
        }
        // Update each projectile
        for (Projectile projectile : projectiles) {
            projectile.render(batch);
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

    public Array<Zombie> getZombies() {
        return zombies;
    }
}