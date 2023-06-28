package com.mygdx.game;

import Enemies.Enemy;
import Enemies.Zombie;
import Enemies.speedZombie;
import Interface.Timer;
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
    private BitmapFont font;
    private ArrayList<Tower> towers = new ArrayList<>();
    private Music bgMusic;
    boolean play = false;
    private Enemy zombie, speedZombie;
    private Tower tower;
    public Timer timer;



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
        tower = new Tower();
        zombie = new Zombie();
        speedZombie = new speedZombie();
        timer = new Timer();
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(menu, 0, 0);
        batch.end();
        //Change Screen
        if (!play && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            play = true;
        } else if (play && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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
            timer.startTimer(batch);
            batch.end();
            zombie.render();
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    public void gameOver() {
        bgMusic.stop();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            play = false;
            Gdx.app.exit();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            play = true;
        }
    }
}


