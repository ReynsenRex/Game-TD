package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
 public static void main (String[] arg) {
  Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
  config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
  //config.setWindowedMode(1280, 720); // untuk tes windowed mode
  config.setForegroundFPS(60);
  config.useVsync(true);
  config.setTitle("Tower Defense");
  new Lwjgl3Application(new MyGdxGame(), config);
 }
}