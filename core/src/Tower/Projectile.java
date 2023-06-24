package Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import Enemies.Zombie;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class Projectile {
    private static final float SPEED = 5000f;
    private Sprite sprite;
    private Vector2 position;
    private Vector2 target;
    private Vector2 direction;
    private Circle hitbox; // Hitbox representation
    private Sound hitSound;
    Array<Sprite> sprites;


    public Projectile() {
        Texture bulletTexture = new Texture(Gdx.files.internal("fireBullet.png"));
        sprite = new Sprite(bulletTexture);
        sprite.setSize(50, 50);

        position = new Vector2();
        direction = new Vector2();
        hitbox = new Circle();
//        hitSound = Gdx.audio.newSound(Gdx.files.internal("hitSound.wav"));
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        sprite.setPosition(x, y);
        updateHitbox(); // Update the hitbox position when setting the position
    }

    public void setTarget(float targetX, float targetY) {
        target = new Vector2(targetX, targetY);
        direction = target.cpy().sub(position).nor();
    }

    public void update(float deltaTime, Zombie zombie) {
        float dx = direction.x * SPEED * deltaTime;
        float dy = direction.y * SPEED * deltaTime;
        position.add(dx, dy);
        sprite.setPosition(position.x, position.y);
        updateHitbox();

        // Check for collision with zombie
        if (collidesWithZombie(zombie)) {
            // Apply damage to the zombie
            zombie.takeDamage();

            // Play hit sound
//            hitSound.play();

            // Destroy the projectile
            destroy();
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean isOffScreen() {
        return position.x < 0 || position.x > Gdx.graphics.getWidth() ||
                position.y < 0 || position.y > Gdx.graphics.getHeight();
    }

    public boolean hasReachedTarget() {
        return position.epsilonEquals(target, 1f);
    }

    public Circle getHitbox() {
        return hitbox;
    }

    private void updateHitbox() {
        float radius = sprite.getWidth() / 2; // Adjust hitbox size as needed
        hitbox.set(position.x + radius, position.y + radius, radius);
    }

    private boolean collidesWithZombie(Zombie zombie) {
        Circle projectileHitbox = getHitbox();
        Rectangle zombieHitbox = zombie.getHitbox();
        return Intersector.overlaps(projectileHitbox, zombieHitbox);
    }

    private void destroy() {
        sprites.removeValue(sprite, true);

        // Dispose of the bullet sprite's texture
        sprite.getTexture().dispose();
    }
}
