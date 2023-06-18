//package com.mygdx.game;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.graphics.GL20;
//
//public class MenuScreen extends ScreenAdapter {
//    private MyGdxGame game;
//    private OrthographicCamera camera;
//    private SpriteBatch batch;
//    private Texture backgroundTexture;
//    private Sprite backgroundSprite;
//    private BitmapFont font;
//
//    public MenuScreen() {
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch = new SpriteBatch();
//        backgroundTexture = new Texture("Map.png");
//        backgroundSprite = new Sprite(backgroundTexture);
//        backgroundSprite.setPosition(0, 0);
//    }
//
//    @Override
//    public void show() {
//    }
//
//    @Override
//    public void render(float delta) {
//        // Clear the screen
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        // Render menu elements and handle input
//
//        // Example: Render a title
//         batch.begin();
//         font.draw(batch, "Tower Defense", 100, 100);
//         batch.end();
//
//        // Example: Handle input to start the game
//         if (Gdx.input.justTouched()) {
//             ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
//         }
//    }
//
//    @Override
//    public void resize(int width, int height) {
//    }
//
//    @Override
//    public void pause() {
//    }
//
//    @Override
//    public void resume() {
//    }
//
//    @Override
//    public void hide() {
//    }
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        backgroundTexture.dispose();
//    }
//}
