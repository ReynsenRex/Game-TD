package Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.ArcherTower;

import java.util.Iterator;

public class Zombie extends Enemy {
    private Animation<TextureRegion> zombieAnimationRight;
    private Texture zombieTextureMoveRight;
    private float screenWidth, screenHeight, stateTime;
    public float x, y;
    private Array<Enemy> zombies;
    private Array<Rectangle> zombiess;
    private long nextSpawnTime = generateNextSpawnTime();
    private SpriteBatch batch;
    private BitmapFont font;
    private Rectangle hitbox;
    private TextureRegion currentFrame;
    public Vector2 position;
    public Vector2 projectile_position;
    public Sprite sprite;
    public Sprite projectile_sprite;
    public float speed = 1000;
    public float projectile_speed = 10000;
    private speedZombie speedZombie;
    private boolean gameOver = false;
    public ArcherTower archerTower;
    private Texture projectile_texture;
    private boolean isAlive = true;


    public Zombie() {
        super(new Texture(Gdx.files.internal("ZombieRight.png")), 0, 450, 50);
        batch = new SpriteBatch();
        font = new BitmapFont();
        hitbox = new Rectangle(x, y, 70, 70); // Create the hitbox with initial position and size
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

        zombiess = new Array<Rectangle>();
        zombieAnimationRight = new Animation<>(0.3f, walkFramesRight);

        zombies = new Array<>();
        speedZombie = new speedZombie();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        currentFrame = walkFramesRight[0]; // Set initial frame to zombieMoveRight

        archerTower = new ArcherTower();
    }

    @Override
    public long generateNextSpawnTime() {
        return TimeUtils.nanoTime() + (long) (Math.random() * 800000000) + 100000000;
    }

    public void spawnZombie(float spawnRate, float minX, float maxX, float minY, float maxY) {
        if (TimeUtils.nanoTime() > nextSpawnTime) {
            Rectangle zombie = new Rectangle();
            zombie.x = MathUtils.random(minX, maxX);
            zombie.y = MathUtils.random(minY, maxY);
            zombie.width = 100;
            zombie.height = 100;
            zombiess.add(zombie);

            nextSpawnTime = TimeUtils.nanoTime() + (long) (spawnRate * 1000000000); // Convert spawnRate to nanoseconds
        }
    }

    @Override
    public void spawnZombie() {
        Rectangle zombie = new Rectangle();
        zombie.x = MathUtils.random(0, 1080 - 100);
        zombie.y = MathUtils.random(0, 1080 - 100);
        zombie.width = 100;
        zombie.height = 100;
        zombiess.add(zombie);
    }

    @Override
    public void render() {
        archerTower.Draw(batch);
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        if (!gameOver) {
            // Spawn a new zombie if it's time
            if (TimeUtils.nanoTime() > nextSpawnTime) {
                spawnZombie();
                spawnZombie(2.0f, 0, 1080 - 100, 0, 1080 - 100);
                nextSpawnTime = generateNextSpawnTime();
            }

            for (Rectangle zombie : zombiess) {
                batch.draw(currentFrame, zombie.x, zombie.y, 100, 100);

                // Check if zombie reaches the right end of the screen
                if (zombie.x + zombie.width >= 1920) {
                    // Game over logic
                    gameOver = true;
                    break;
                }
                isAlive = true;
            }

            // Move and render zombies
            for (Iterator<Rectangle> iter = zombiess.iterator(); iter.hasNext(); ) {
                Rectangle zombie = iter.next();
                zombie.x += 100 * Gdx.graphics.getDeltaTime();
                if (zombie.y + 64 < 0) iter.remove();
                if (zombie.overlaps(archerTower.projectile_sprite.getBoundingRectangle())) {
                    archerTower.projectile_position.x = 10000;
                    iter.remove();
                    isAlive = false;
                    break;
                }
            }


        } else {
            font.getData().setScale(10);
            font.draw(batch, "Game Over", 1920 / 2 - 400, 1080 / 2);
            Gdx.app.exit();
        }

        batch.end();
        speedZombie.render();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void Draw(SpriteBatch batch) {
        projectile_sprite.setPosition(projectile_position.x + 250, projectile_position.y + 100);
        sprite.setPosition(position.x, position.y);
        batch.begin();
        sprite.draw(batch);
        projectile_sprite.draw(batch);
        batch.end();
    }

}


