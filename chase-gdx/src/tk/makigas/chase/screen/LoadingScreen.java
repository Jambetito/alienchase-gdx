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
package tk.makigas.chase.screen;

import tk.makigas.chase.AlienChase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

/**
 * Pantalla de carga. Esta es la primera pantalla que se ve al cargar el
 * juego y es usada como pantalla de espera mientras el AssetManager de la
 * clase principal termina de cargar sus recursos.
 * 
 * @author danirod
 * @see AlienChase#MANAGER
 */
public class LoadingScreen extends AbstractScreen {

	/** Splash image scale. */
	private int scale;
	
	/** Splash image position. */
	private float x, y, size;

	public LoadingScreen(AlienChase game) {
		super(game);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, (51.0f / 256.0f), (153.0f / 256.0f), 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Para asegurarnos de que el SpriteBatch y la cámara están
		// siempre sincronizados, los ajustamos en cada fotograma.
		game.getCamera().update();			// recalcula las matrices de la cámara
		game.getCamera().apply(Gdx.gl10);	// reajusta las matrices de la cámara
		// lo que viene a continuación es super necesario: le indica al
		// SpriteBatch que use la matriz de proyección de la cámara en sus
		// cálculos. De este modo, se usarán las coordenadas de la cámara.
		game.getSpriteBatch().setProjectionMatrix(game.getCamera().combined);
		
		// Continuamos cargando recursos con normalidad.
		if(AlienChase.MANAGER.update()) {
			game.setScreen(game.MAIN); // si hemos terminado vamos al menú
		}
		
		// Intentamos mostrar la imagen que dice cargando.
		// Sólo se puede mostrar esta imagen si ya ha sido cargada por el
		// AssetManager (de ahí que sea necesario cargar este recurso
		// el primero de todos).
		if(AlienChase.MANAGER.isLoaded("ui/splash.png", Texture.class)) {
			// Está cargado.
			game.getSpriteBatch().begin();
			game.getSpriteBatch().draw(AlienChase.MANAGER.get("ui/splash.png", Texture.class),
					x, y, size, size);
			game.getSpriteBatch().end();
		}
	}

	@Override
	public void show() {
		int width, height;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		scale = Math.max(1, (width < height ? width : height) / 256);
		size = 256 * scale;
		x = (width - size) / 2;
		y = (height - size) / 2;
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		
	}
	
	@Override
	public void resize(int width, int height) {
		scale = Math.max(1, (width < height ? width : height) / 256);
		size = 256 * scale;
		x = (width - size) / 2;
		y = (height - size) / 2;
	}

}
