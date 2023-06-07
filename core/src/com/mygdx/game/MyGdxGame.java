package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {

    private Texture zombieUp;
    private Texture ZombieRight;
    private Texture zombieDown;
    private Sprite zombie1;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Array<Rectangle> zombies;
    private long spawnTime;
    private BitmapFont font;
    private long nextSpawnTime = generateNextSpawnTime();

    @Override
    public void create() {

        font = new BitmapFont();

        ZombieRight = new Texture(Gdx.files.internal("1 Zombie2.png"));
        zombieUp = new Texture(Gdx.files.internal("1 Zombie3.png"));
        zombieDown = new Texture(Gdx.files.internal("1 Zombie1.png"));
        zombie1 = new Sprite(ZombieRight);
        zombie1.setSize(100, 100);
//        zombie1.setPosition(1160, 340);

        batch = new SpriteBatch();

        texture = new Texture("Map.png");

        sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(0, 0);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        zombies = new Array<>();
        spawnZombie();

    }

    private void spawnZombie() {
        Rectangle zombies = new Rectangle();
        zombies.x = 0;
        zombies.y = 450;
        zombies.width = 100;
        zombies.height = 100;
        this.zombies.add(zombies);
        spawnTime = TimeUtils.nanoTime();
    }

    private long generateNextSpawnTime() {
        return TimeUtils.nanoTime() + (long) (Math.random() * 800000000) + 700000000;
    }

    public void render() {
        batch.begin();
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        if (TimeUtils.nanoTime() > nextSpawnTime) {
            spawnZombie();
            nextSpawnTime = generateNextSpawnTime();
        }
        batch.setProjectionMatrix(camera.combined);
        sprite.draw(batch);

        for (Rectangle zombie : zombies) {
            if (zombie.x < 255) {
                // Move to the right until the first turn
                zombie.x += 1000 * Gdx.graphics.getDeltaTime();
                zombie.y = 450;
                batch.draw(ZombieRight, zombie.x, zombie.y, zombie.width, zombie.height);
            } else if (zombie.x >= 650 && zombie.y > 340) {
                // Move down until reaching y = 340
                zombie.x = 650;
                zombie.y -= 1000 * Gdx.graphics.getDeltaTime();
                batch.draw(zombieDown, zombie.x, zombie.y, zombie.width, zombie.height);
            } else if (zombie.x > 1160 && zombie.y <= 340) {
                // Move up until reaching x = 1160
                zombie.y += 1000 * Gdx.graphics.getDeltaTime();
                zombie.x = 1160;
                batch.draw(zombieUp, zombie.x, zombie.y, zombie.width, zombie.height);
            } else if (zombie.y <= 340 && zombie.x >= 650 && zombie.x <= 1160) {
                // Move to the right again after reaching y = 340
                zombie.y = 340;
                zombie.x += 1000 * Gdx.graphics.getDeltaTime();
                batch.draw(ZombieRight, zombie.x, zombie.y, zombie.width, zombie.height);
            } else if (zombie.y < 775) {
                // Move up until the second turn
                zombie.y += 1000 * Gdx.graphics.getDeltaTime();
                zombie.x = 255;
                batch.draw(zombieUp, zombie.x, zombie.y, zombie.width, zombie.height);
            } else if (zombie.y >= 775 && zombie.x <= 650) {
                // Move to the right until the third turn
                zombie.y = 775;
                zombie.x += 1000 * Gdx.graphics.getDeltaTime();
                batch.draw(ZombieRight, zombie.x, zombie.y, zombie.width, zombie.height);
            }
        }
        batch.end();
    }




    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}