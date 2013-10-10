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
import tk.makigas.chase.actor.ui.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainScreen extends AbstractScreen {

	private Texture titulo;

	private Button playButton, quitButton;
	
	private Image background;

	private Stage stage;

	public MainScreen(AlienChase game) {
		super(game);
	}

	@Override
	public void show() {
		titulo = AlienChase.MANAGER.get("ui/title.png", Texture.class);
		stage = new Stage(640, 360, true, game.getSpriteBatch());
		
		background = new Image(titulo);
		background.setFillParent(true);
		stage.addActor(background);
		
		playButton = new Button("Play game");
		playButton.setSize(120, 16);
		stage.addActor(playButton);
		playButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(game.GAMEPLAY);
				return true;
			}
		});
		
		quitButton = new Button("Quit game");
		quitButton.setSize(120, 16);
		stage.addActor(quitButton);
		quitButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
				return true;
			}
		});

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
	
	/** UI scaling factor (has to be integer for not breaking pixels) */
	private int uiScale;

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		if(width > height)
			uiScale = height / 120 + 1;
		else
			uiScale = width / 160 + 1;
		
		playButton.setScale(uiScale);
		quitButton.setScale(uiScale);
		
		// horizontal center
		playButton.setX((width - playButton.getWidth() * uiScale) / 2);
		quitButton.setX((width - quitButton.getWidth() * uiScale) / 2);
		
		// vertical positioning
		playButton.setY(height / 2 - 10 * uiScale);
		quitButton.setY(height / 2 - 30 * uiScale);
	}

}
