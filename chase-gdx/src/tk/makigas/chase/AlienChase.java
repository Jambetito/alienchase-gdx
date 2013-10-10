/*
 * Alien Chase 2013 -- a remake of Alien Chase 2012 (also developed by me)
 * Copyright (C) 2012, 2013 Dani Rodríguez <danirod@outlook.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tk.makigas.chase;

import tk.makigas.chase.screen.AbstractScreen;
import tk.makigas.chase.screen.GameOverScreen;
import tk.makigas.chase.screen.GameplayScreen;
import tk.makigas.chase.screen.LoadingScreen;
import tk.makigas.chase.screen.MainScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;

/**
 * Juego de aliens.
 * 
 * @author danirod
 */
public class AlienChase extends Game {
	
	/** Gestor de recursos usado por el juego. */
	public static final AssetManager MANAGER = new AssetManager();
	
	/**
	 * SpriteBatch compartido por los distintos estados del juego.
	 * Se declara aquí porque varios estados necesitan tener acceso a
	 * este recurso y no es recomendable crear múltiples SpriteBatches. 
	 */
	private SpriteBatch sb;
	
	/** Cámara compartida por los distintos estados del juego. */
	private OrthographicCamera camera;
		
	public final AbstractScreen GAMEOVER, GAMEPLAY, LOADING, MAIN;
	
	public AlienChase() {
		GAMEOVER = new GameOverScreen(this);
		GAMEPLAY = new GameplayScreen(this);
		LOADING = new LoadingScreen(this);
		MAIN = new MainScreen(this);
	}

	@Override
	public void create() {
		int width, height;
		
		sb = new SpriteBatch();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(width, height);
		
		// Load game assets.
		// Splash screen has to be loaded first, so that the loader can
		// display the splash image while the rest of the assets are loading.
		MANAGER.load("ui/splash.png", Texture.class);
		
		MANAGER.load("entities/alien.gif", Texture.class);
		MANAGER.load("entities/bala.png", Texture.class);
		MANAGER.load("entities/cohete.png", Texture.class);
		MANAGER.load("entities/star.png", Texture.class);
		
		MANAGER.load("sound/explosion.ogg", Sound.class);
		MANAGER.load("sound/hit.ogg", Sound.class);
		MANAGER.load("sound/shoot.ogg", Sound.class);
		
		MANAGER.load("ui/fixed12.png", Texture.class);
		MANAGER.load("ui/fixed12.fnt", BitmapFont.class);
		MANAGER.load("ui/gameover.png", Texture.class);
		MANAGER.load("ui/jugar.png", Texture.class);
		MANAGER.load("ui/pad.png", Texture.class);
		MANAGER.load("ui/salir.png", Texture.class);
		MANAGER.load("ui/title.png", Texture.class);
		MANAGER.load("ui/ui_button.png", Texture.class);
		MANAGER.load("ui/vida.png", Texture.class);
		
		setScreen(LOADING);
	}
	
	/**
	 * Recupera la instancia compartida de SpriteBatch
	 * @return SpriteBatch en uso por el juego
	 */
	public SpriteBatch getSpriteBatch() {
		return sb;
	}

	/**
	 * Recupera la instancia compartida de OrthograhpicCamera
	 * @return cámara en uso por el juego.
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);	// llamada a super obligatoria
										// (no sabemos si el estado actual
										// podría necesitarlo)
		getCamera().setToOrtho(false, width, height);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		MANAGER.dispose();
		getSpriteBatch().dispose();
	}
	
}
