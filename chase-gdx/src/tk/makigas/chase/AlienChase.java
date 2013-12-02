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

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Juego de aliens.
 * 
 * @author danirod
 */
public class AlienChase extends Game {
	
	/**
	 * Gestor de recursos usado por el juego.
	 * 
	 * Realmente este campo debería llamarse manager y no MANAGER, pero
	 * anteriormente fue una variable de tipo final y ahora me parece
	 * demasiado arriesgado cambiar todas las referencias de MANAGER
	 * por manager (especialmente teniendo en cuenta que este código
	 * fuente está obsoleto y que seguramente la versión v1.1 de
	 * Alien Chase cambiará por dentro el sistema de forma notable.
	 */
	public static AssetManager MANAGER;
	
	public SpriteBatch SB;
		
	public final AbstractScreen GAMEOVER, GAMEPLAY, LOADING, MAIN;
	
	public AlienChase() {
		GAMEOVER = new GameOverScreen(this);
		GAMEPLAY = new GameplayScreen(this);
		LOADING = new LoadingScreen(this);
		MAIN = new MainScreen(this);
	}

	@Override
	public void create() {
		/*
		 * Inicializa un nuevo AssetManager. Antes esto se hacía en la
		 * propia definición del campo (de hecho la variable era final, lo
		 * que explica que esté escrita aún en mayúsculas), pero eso
		 * provocaba unos bugs muy feos.
		 * 
		 * Moraleja: inicializar cosas de LibGDX de forma estática y final
		 * es una idea MUY mala.
		 */
		MANAGER = new AssetManager();
		
		SB = new SpriteBatch();
		
		// Cargamos todos los elementos externos que usará el juego.
		MANAGER.load("cargando.png", Texture.class);
		MANAGER.load("alien.gif", Texture.class);
		MANAGER.load("bala.png", Texture.class);
		MANAGER.load("cohete.png", Texture.class);
		MANAGER.load("defensa.png", Texture.class);
		MANAGER.load("fondo.png", Texture.class);
		MANAGER.load("pad.png", Texture.class);
		MANAGER.load("vida.png", Texture.class);
		MANAGER.load("hit.ogg", Sound.class);
		MANAGER.load("explosion.ogg", Sound.class);
		MANAGER.load("shoot.ogg", Sound.class);
		MANAGER.load("gameover.png", Texture.class);
		MANAGER.load("title.png", Texture.class);
		MANAGER.load("jugar.png", Texture.class);
		MANAGER.load("salir.png", Texture.class);
		
		setScreen(LOADING);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		MANAGER.dispose();
		SB.dispose();
	}
	
}
