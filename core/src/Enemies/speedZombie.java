package Enemies;

import Interface.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;
import Tower.Tower;
import com.mygdx.game.GameOver;

import java.util.Iterator;

public class speedZombie extends Enemy {
    private Animation<TextureRegion> zombieAnimationRight;
    private Texture zombieTextureMoveRight;
    private Array<Rectangle> speedZombie;
    private long nextSpawnTime = generateNextSpawnTime();
    private SpriteBatch batch;
    private BitmapFont font;
    private TextureRegion currentFrame;
    private boolean gameOver = false;
    private Tower tower;
    private boolean isAlive = true;
    public Timer timer;
    private float initialSpawnRate = 0.5f;
    private float spawnRateMultiplier = 0.8f;
    private float minSpawnRate = 0.1f;
    public GameOver GO;

    public speedZombie() {
        super(new Texture(Gdx.files.internal("speedZombieRight.png")), 0, 450, 1);
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
        timer = new Timer();
        GO = new GameOver();
    }

    @Override
    public long generateNextSpawnTime() {
        return TimeUtils.nanoTime() + (long) (Math.random() * 800000000) + 100000000;
    }
    public void spawnZombie(float minX, float maxX, float minY, float maxY) {
        if (TimeUtils.nanoTime() > nextSpawnTime) {
            Rectangle zombie = new Rectangle();
            zombie.x = MathUtils.random(minX, maxX);
            zombie.y = MathUtils.random(minY, maxY);
            zombie.width = 100;
            zombie.height = 100;
            speedZombie.add(zombie);

            // Adjust the spawn rate
            float spawnRate = Math.max(initialSpawnRate * spawnRateMultiplier, minSpawnRate);
            nextSpawnTime = TimeUtils.nanoTime() + (long) (spawnRate * 1000000000); // Convert spawnRate to nanoseconds
        }
    }
    @Override
    public void spawnZombie() {
        Rectangle zombie = new Rectangle();
        zombie.x = 0;
        zombie.y = MathUtils.random(0, 900 - 100);
        zombie.width = 100;
        zombie.height = 100;
        speedZombie.add(zombie);
    }

    @Override
    public void render() {
        tower.Draw(batch);
        batch.begin();
        if (timer.getPoints() >= 10 && timer.getPoints() % 10 == 0) {
            // Spawn zombies
            initialSpawnRate *= spawnRateMultiplier;
            initialSpawnRate = Math.max(initialSpawnRate, minSpawnRate);
            spawnZombie(0, 600, 0, 600);
        }
        if (!gameOver) {

            // Spawn a new zombie if it's time
            if (TimeUtils.nanoTime() > nextSpawnTime) {
                spawnZombie();
                nextSpawnTime = generateNextSpawnTime();
            }
            for (Rectangle zombie : speedZombie) {
                batch.draw(currentFrame, zombie.x, zombie.y, 100, 100);
                zombie.x += 100 * Gdx.graphics.getDeltaTime();
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
            GO.GOScreen(batch);
        }

        batch.end();

    }

}


