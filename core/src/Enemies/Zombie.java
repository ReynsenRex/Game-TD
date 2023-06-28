package Enemies;

import Tower.ArcherTower;
import com.badlogic.gdx.Gdx;
import Interface.Timer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameOver;

import java.util.Iterator;

public class Zombie extends Enemy {
    private Animation<TextureRegion> zombieAnimationRight;
    private Texture zombieTextureMoveRight;
    private float stateTime;
    private Array<Rectangle> zombies;
    private long nextSpawnTime = generateNextSpawnTime();
    private SpriteBatch batch;
    private BitmapFont font;
    private TextureRegion currentFrame;
    private speedZombie speedZombie;
    private boolean gameOver = false;
    public ArcherTower archerTower;
    private boolean isAlive = true;
    public Timer timer;
    private float initialSpawnRate = 0.5f;
    private float spawnRateMultiplier = 0.8f;
    private float minSpawnRate = 0.1f;
    public GameOver GO;
    private Rectangle zombie;
    public Zombie() {
        super(new Texture(Gdx.files.internal("ZombieRight.png")), 0, 450, 1);
        batch = new SpriteBatch();
        font = new BitmapFont();
        // Buat Zombie
        zombieTextureMoveRight = new Texture(Gdx.files.internal("ZombieRight.png"));

        TextureRegion[][] textureMoveRight = TextureRegion.split(zombieTextureMoveRight, 124 / 3, 36);
        TextureRegion[] walkFramesRight = new TextureRegion[textureMoveRight.length * textureMoveRight[0].length];
        int index = 0;
        for (int i = 0; i < textureMoveRight.length; i++) {
            for (int j = 0; j < textureMoveRight[i].length; j++) {
                walkFramesRight[index++] = textureMoveRight[i][j];
            }
        }
        zombieAnimationRight = new Animation<>(0.3f, walkFramesRight);

        zombies = new Array<>();
        speedZombie = new speedZombie();
        currentFrame = walkFramesRight[0];
        archerTower = new ArcherTower();
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
            zombies.add(zombie);

            // Adjust the spawn rate
            float spawnRate = Math.max(initialSpawnRate * spawnRateMultiplier, minSpawnRate);
            nextSpawnTime = TimeUtils.nanoTime() + (long) (spawnRate * 1000000000); // Convert spawnRate to nanoseconds
        }
    }

    @Override
    public void spawnZombie() {
        zombie = new Rectangle();
        zombie.x = MathUtils.random(0, 1080 - 100);
        zombie.y = MathUtils.random(0, 900 - 100);
        zombie.width = 100;
        zombie.height = 100;
        zombies.add(zombie);
    }
    @Override
    public void render() {
        archerTower.Draw(batch);
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Increase the spawn rate every 30 seconds
        if (timer.getPoints() >= 10 && timer.getPoints() % 10 == 0) {
            initialSpawnRate *= spawnRateMultiplier;
            initialSpawnRate = Math.max(initialSpawnRate, minSpawnRate);
            spawnZombie(0, 1080, 0, 600);
        }
        if (!gameOver) {
            timer.startTimer(batch);

            // Spawn a new zombie if it's time
            if (TimeUtils.nanoTime() > nextSpawnTime + 1000000000) {
                spawnZombie();
                nextSpawnTime = generateNextSpawnTime();
            }
            for (Rectangle zombie : zombies) {
                batch.draw(currentFrame, zombie.x, zombie.y, 100, 100);
                zombie.x += 50 * Gdx.graphics.getDeltaTime();
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
                for (Iterator<Rectangle> iter = zombies.iterator(); iter.hasNext(); ) {
                    Rectangle zombie = iter.next();
                    if (zombie.overlaps(archerTower.projectile_sprite.getBoundingRectangle())) {
                        archerTower.projectile_position.x = 10000;
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
        speedZombie.render();
    }

}


