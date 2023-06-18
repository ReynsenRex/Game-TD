package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private Texture texture;
    private SpriteBatch batch;
    private Sprite sprite;
    private OrthographicCamera camera;

    private Zombie zombie;
    private BitmapFont font;
    private Tower tower;
    private Tower tower1;
    private Tower tower2;
    private Tower tower3;
    private Tower tower4;

    @Override
    public void create() {
        MenuScreen menuScreen = new MenuScreen();
        // Set the initial screen to the menu screen
        setScreen(menuScreen);


        batch = new SpriteBatch();
        // MAP
        texture = new Texture("Map.png");

        sprite = new Sprite(texture);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(0, 0);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = new BitmapFont();

        // Create a new instance of Zombie
        zombie = new Zombie();

        tower = new Tower(batch);
        tower1 = new Tower();
        tower2 = new Tower();
        tower3 = new Tower();
        tower4 = new Tower();
    }
    public void setScreen(MenuScreen gameScreen) {
        gameScreen.render(1f);
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

        // End the sprite batch
        batch.end();

        // Render the Zombies
        zombie.render();

        // render tower

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            tower.spawnTower(50, 510); // tower 1
            tower.render(batch);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            tower.spawnTower(400, 550); // tower 2
            tower.render(batch);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            tower.spawnTower(750, 400); // tower 3
            tower.render(batch);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            tower.spawnTower(1100, 100); // tower 4
            tower.render(batch);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            tower.spawnTower(1300, 700); // tower 5
            tower.render(batch);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            tower.spawnTower(1500, 300); // tower 6
            tower.render(batch);
        }
//        boolean button1Pressed = false;
//        boolean button2Pressed = false;
//        boolean button3Pressed = false;
//        boolean button4Pressed = false;
//
//        ArrayList<Tower> towersToRender = new ArrayList<>();
//        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
//            button1Pressed = true;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
//            button2Pressed = true;
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
//            button3Pressed = true;
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
//            button4Pressed = true;
//        }
//
//        if (button1Pressed) {
//
//            tower1 = new Tower();
//            tower1.spawnTower(50, 510);
//            towersToRender.add(tower1);
//        }
//
//        if (button2Pressed) {
//
//            tower2 = new Tower();
//            tower2.spawnTower(400, 500);
//            towersToRender.add(tower2);
//        }
//
//        if (button3Pressed) {
//
//            tower3 = new Tower();
//            tower3.spawnTower(750, 400);
//            towersToRender.add(tower3);
//        }
//
//        if (button4Pressed) {
//
//            tower4 = new Tower();
//            tower4.spawnTower(1300, 700);
//            towersToRender.add(tower4);
//        }
//
//       for (Tower tower : towersToRender) {
//        tower.update(Gdx.graphics.getDeltaTime()); // Update the tower's visibility status
//        tower.render(batch);
//        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}

