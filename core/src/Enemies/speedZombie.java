package Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class speedZombie extends Enemy {
    private Animation<TextureRegion> zombieAnimationRight;
    private Texture zombieTextureMoveRight;
    private float screenWidth, screenHeight, stateTime; // Animation time for the zombie
    public float x, y;
    public int health;
    private Array<Enemy> zombies;
    private Array<Rectangle> speedZombie;
    private long nextSpawnTime = generateNextSpawnTime();
    private SpriteBatch batch;
    private BitmapFont font;
    private Rectangle hitbox;
    private TextureRegion currentFrame;

    public speedZombie() {
        super(new Texture(Gdx.files.internal("speedZombieRight.png")), 0, 450, 50);
        batch = new SpriteBatch();
        font = new BitmapFont();
        hitbox = new Rectangle(x, y, 70, 70); // Create the hitbox with initial position and size
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

        zombies = new Array<>();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        currentFrame = walkFramesRight[0]; // Set initial frame to zombieMoveRight
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
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Spawn a new zombie if it's time
        if (TimeUtils.nanoTime() > nextSpawnTime) {
            spawnZombie();
            nextSpawnTime = generateNextSpawnTime();
        }

        for (Rectangle zombie : speedZombie) {
            batch.draw(currentFrame, zombie.x, zombie.y, 100,100);
        }

        // Move and render zombies
        for (Iterator<Rectangle> iter = speedZombie.iterator(); iter.hasNext(); ) {
            Rectangle zombie = iter.next();
            zombie.x += 200 * Gdx.graphics.getDeltaTime();
            if (zombie.y + 64 < 0) iter.remove();
//            if(zombie.overlaps(bucket)) {
//                iter.remove();
//            }


        }
        batch.end();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public int setHealth() {
        return health = 50;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }
}

