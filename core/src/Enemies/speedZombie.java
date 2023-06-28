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
import com.mygdx.game.Tower;

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
    public Vector2 position;
    public Vector2 projectile_position;
    public Sprite sprite;
    public Sprite projectile_sprite;
    public float speed = 1000;
    public float projectile_speed = 10000;
    private boolean gameOver = false;
    private Tower tower;


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

        currentFrame = walkFramesRight[0]; // Set initial frame to zombieMoveRight


        Texture projectile_texture = new Texture(Gdx.files.internal("fireBullet.png"));
        projectile_sprite = new Sprite(projectile_texture);
        projectile_sprite.setScale((float) 0.2);
        float desiredScale = 0.5f;  // Set the desired scale value
        position = new Vector2(1500, desiredScale * 510 / 2);
        projectile_position = new Vector2(0, 1000);
        tower = new Tower();
        tower.Update(Gdx.graphics.getDeltaTime());
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

        if (!gameOver) {
            // Spawn a new zombie if it's time
            if (TimeUtils.nanoTime() > nextSpawnTime) {
                spawnZombie();
                nextSpawnTime = generateNextSpawnTime();
            }

            for (Rectangle zombie : speedZombie) {
                batch.draw(currentFrame, zombie.x, zombie.y, 100, 100);

                // Check if zombie reaches the right end of the screen
                if (zombie.x + zombie.width >= 1920) {
                    // Game over logic
                    gameOver = true;
                    break;
                }
            }

            // Move and render zombies
            for (Iterator<Rectangle> iter = speedZombie.iterator(); iter.hasNext(); ) {
                Rectangle zombie = iter.next();
                zombie.x += 200 * Gdx.graphics.getDeltaTime();
                if (zombie.y + 64 < 0) iter.remove();
                if (zombie.overlaps(projectile_sprite.getBoundingRectangle())) {
                    iter.remove();
                }
            }


        } else {
            // Game over screen
            font.getData().setScale(10);
            font.draw(batch, "Game Over", 1920 / 2 - 400, 1080 / 2);
            Gdx.app.exit();
        }

        batch.end();
        tower.Draw(batch);
        Draw(batch);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public void Update(float deltatime){
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            projectile_position.x = position.x;
            projectile_position.y = position.y;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            position.y += deltatime * speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            position.y -= deltatime * speed;
        }



        projectile_position.x -= deltatime*projectile_speed;
    }

    public void Draw(SpriteBatch batch) {
        Update(Gdx.graphics.getDeltaTime());
        projectile_sprite.setPosition(projectile_position.x+250,projectile_position.y+100);
        batch.begin();
        projectile_sprite.draw(batch);
        batch.end();
    }
}


