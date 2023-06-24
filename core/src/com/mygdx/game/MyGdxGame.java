package com.mygdx.game;

import Enemies.Zombie;
import Tower.Tower;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private Texture texture, menu;
    private SpriteBatch batch;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Zombie zombie;
    private BitmapFont font;
    private ArrayList<Tower> towers = new ArrayList<>();
    private Music bgMusic;
    boolean play = false;

    @Override
    public void create() {

        batch = new SpriteBatch();

        // MAP
        texture = new Texture("Map.png");

        // Menu
        menu = new Texture("MENU.png");

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("BG_Sound.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.05f);

        sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(0, 0);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = new BitmapFont();

        // Create a new instance of Zombie
        zombie = new Zombie();

        for (int i = 0; i < 6; i++) {
            Tower tower = new Tower();
            towers.add(tower);
        }

    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(menu,0,0);
        batch.end();
        //Change Screen
        if (!play && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            play = true;
        } else if(play && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            play = false;
            Gdx.app.exit();
        }

        if (play) {
            bgMusic.play();

            // Update the camera
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            // Begin the sprite batch
            batch.begin();

            // Render the map
            sprite.draw(batch);
            batch.end();

            // Render tower karena tower disimpan di ArrayList jadi menggunakan for each
            for (Tower tower : towers) {
                if (tower.isVisible()) {
                    tower.render(batch);
                    tower.update(Gdx.graphics.getDeltaTime());
                    tower.attack(zombie.getX(), zombie.getY()); // Call attack() method on each tower
                }
            }
            // Render the Zombies
            zombie.render();

            // Spawn towers based on key input
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) && !towers.get(0).isVisible()) {
                towers.get(0).spawnTower(50, 510);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) && !towers.get(1).isVisible()) {
                towers.get(1).spawnTower(400, 550);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3) && !towers.get(2).isVisible()) {
                towers.get(2).spawnTower(750, 400);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_4) && !towers.get(3).isVisible()) {
                towers.get(3).spawnTower(1100, 100);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_5) && !towers.get(4).isVisible()) {
                towers.get(4).spawnTower(1300, 700);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_6) && !towers.get(5).isVisible()) {
                towers.get(5).spawnTower(1500, 300);
            }
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

}


