package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
    private Texture texture, towerTexture;
    private SpriteBatch batch;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Array<Tower> towers;
    private Sprite spriteTower;
    private Zombie zombie;
    private BitmapFont font;

    @Override
    public void create() {

        batch = new SpriteBatch();
        // MAP
        texture = new Texture("Map.png");

        sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(0, 0);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = new BitmapFont();

        //BUAT TOWER //TODO:KENN LK WS MARI PINDAHNO INI KE CLASS TOWER
        towerTexture = new Texture(Gdx.files.internal("towerTexture.png"));

        towers = new Array<>();
        towers.add(spawnTower(400, 500)); // tower 1
        towers.add(spawnTower(50, 510)); // tower 2
        towers.add(spawnTower(750, 400)); // tower 3
        towers.add(spawnTower(1300, 700)); // tower 4

        // Create a new instance of Zombie
        zombie = new Zombie();
    }

    private Tower spawnTower(float x, float y) {
        Tower tower = new Tower();
        Tower.sprite = new Sprite(towerTexture);
        // Set the tower sprite's position and scale
        float towerScale = 0.6f; // Adjust the scale factor as desired

        Tower.sprite.setPosition(x, y);
        Tower.sprite.setScale(towerScale);

        spriteTower = Tower.sprite;

        return tower;
    }

    @Override
    public void render() {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Update the camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Begin the sprite batch
        batch.begin();

        // Render the map
        sprite.draw(batch);

        // Draw towers
        for (Tower tower : towers) {
            Tower.sprite.draw(batch);
        }

        // End the sprite batch
        batch.end();

        // Render the Zombies
        zombie.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
