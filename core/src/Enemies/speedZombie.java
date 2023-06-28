package Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;
import Tower.Tower;

import java.util.Iterator;

public class speedZombie extends Enemy {
    private Animation<TextureRegion> zombieAnimationRight;
    private Texture zombieTextureMoveRight;
    public float x, y;
    private Array<Rectangle> speedZombie;
    private float stateTime;
    private long nextSpawnTime = generateNextSpawnTime();
    private SpriteBatch batch;
    private BitmapFont font;
    private TextureRegion currentFrame;
    private boolean gameOver = false;
    private Tower tower;
    private boolean isAlive = true;

    public speedZombie() {
        super(new Texture(Gdx.files.internal("speedZombieRight.png")), 0, 450, 50);
        batch = new SpriteBatch();
        font = new BitmapFont();
        // Buat Zombie
        zombieTextureMoveRight = new Texture(Gdx.files.internal("speedZombieRight.png"));

        TextureRegion[][] textureMoveRight = TextureRegion.split(zombieTextureMoveRight, 124 / 3, 36);
        TextureRegion[] walkFramesRight = new TextureRegion[textureMoveRight.length * textureMoveRight[0].length];
        int index = 0;
        for (int i = 0; i < textureMoveRight.length; i++) {
            for (int j = 0; j < textureMoveRight[i].length; j++) {
                walkFramesRight[index++] = textureMoveRight[i][j];
            }
        }

        speedZombie = new Array<Rectangle>();
        zombieAnimationRight = new Animation<>(0.3f, walkFramesRight);

        currentFrame = walkFramesRight[0];

        tower = new Tower();
    }

    @Override
    public long generateNextSpawnTime() {
        return TimeUtils.nanoTime() + (long) (Math.random() * 800000000) + 100000000;
    }

    @Override
    public void spawnZombie() {
        Rectangle zombie = new Rectangle();
        zombie.x = 0;
        zombie.y = MathUtils.random(0, 1080 - 100);
        zombie.width = 100;
        zombie.height = 100;
        speedZombie.add(zombie);

    }

    @Override
    public void render() {
        tower.Draw(batch);
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        if (!gameOver) {

            // Spawn a new zombie if it's time
            if (TimeUtils.nanoTime() > nextSpawnTime) {
                spawnZombie();
                nextSpawnTime = generateNextSpawnTime();
            }
            for (Rectangle zombie : speedZombie) {

                batch.draw(currentFrame, zombie.x, zombie.y, 100, 100);
                zombie.x += 200 * Gdx.graphics.getDeltaTime();
                // Check if zombie reaches the right end of the screen
                if (zombie.x + zombie.width >= 1920) {
                    // Game over logic
                    gameOver = true;
                    break;
                }
                isAlive = true;
            }
            // Move and render zombies
            if (isAlive) {
            for (Iterator<Rectangle> iter = speedZombie.iterator(); iter.hasNext(); ) {
                Rectangle zombie = iter.next();
                    if (zombie.overlaps(tower.projectile_sprite.getBoundingRectangle())) {
                        tower.projectile_position.x = 10000;
                        iter.remove();
                        isAlive = false;
                        break;
                    }
                }
            }


        } else {
            // Game over screen
            font.getData().setScale(10);
            font.draw(batch, "Game Over", 1920 / 2 - 400, 1080 / 2);
            Gdx.app.exit();
        }

        batch.end();

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}


