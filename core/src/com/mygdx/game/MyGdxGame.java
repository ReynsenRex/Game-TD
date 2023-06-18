package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private Texture texture;
    private SpriteBatch batch;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Zombie zombie;
    private BitmapFont font;
    private ArrayList<Tower> towers = new ArrayList<>();
    private boolean Start;

    @Override
    public void create() {
//        MenuScreen menuScreen = new MenuScreen();
//        // Set the initial screen to the menu screen
//        setScreen(menuScreen);


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

        for (int i = 0; i < 6; i++) {
            Tower tower = new Tower();
            towers.add(tower);
        }

    }

    //    public void setScreen(MenuScreen gameScreen) {
//        gameScreen.render(1f);
//    }
    boolean play = false; // Add this variable outside the main game loop

    @Override
    public void render() {
        texture = new Texture("MENU.png");
        batch.begin();
        batch.draw(texture,0,0);
        batch.end();
        // Clear the screen
        if (!play && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            // First time any key is pressed, enable rendering
            play = true;
        } else if(play && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            play = false;
            Gdx.app.exit();
        }

        if (play) {
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

            for (Tower tower : towers) {
                if (tower.isVisible()) {
                    tower.render(batch);
                    tower.update(Gdx.graphics.getDeltaTime());
                }
            }

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


