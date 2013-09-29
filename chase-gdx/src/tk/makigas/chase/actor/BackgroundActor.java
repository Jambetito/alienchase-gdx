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
package tk.makigas.chase.actor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import tk.makigas.chase.AlienChase;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Dynamic star field.
 * 
 * Each star brights, and that brightness varies through the time. Each star
 * has a data structure with information about its position and its brightness.
 * Brightness is given by Math.sin function. Each star has a counter with
 * a value between 0 and 180. Star brightness equals to sin of that counter.
 * That counter increments with a random velocity.
 * 
 * @author danirod
 *
 */
public class BackgroundActor extends Actor {
	
	/**
	 * sin table. Holds the value for Math.sin(x) for each x in range 0,180.
	 * Caching sin values instead of doing every single calculation every
	 * single tick will increase performance. 
	 */
	private static final float[] SIN_VALUES;
	
	static {
		// Cache sin values in sin table.
		SIN_VALUES = new float[180];
		for(int i = 0; i < 180; i++)
			SIN_VALUES[i] = (float) Math.sin(i);
	}
	
	/**
	 * Data structure for each star.
	 * 
	 * @author danirod
	 */
	private static class StarData {
		
		/** Star position in stage. */
		public float x, y;
		
		/** Current brightness level for star. */
		public float currentBrightness;
		
		/** Brightness increment per second. */
		public float brightnessIncrement;
		
		/** Star brightness is cached. */
		public float starAlpha;
	}
	
	/** Every value is obtained using this random number generator. */
	private Random dataGenerator;
	
	/** List of stars. */
	private List<StarData> stars;
	
	private Texture starTexture;
	
	/**
	 * Fancy mode.
	 * 
	 * If fancy mode is enabled, stars blink and they render at a variable
	 * brightness level. If fancy mode is disabled, stars don't blink and they
	 * render always at full brightness.
	 */
	public boolean fancyMode = true;
	
	public BackgroundActor(int amount)
	{
		dataGenerator = new Random();
		starTexture = AlienChase.MANAGER.get("entities/star.png", Texture.class);
		stars = new ArrayList<StarData>();
		
		StarData star;
		for(int i = 0; i < 50; i++) {
			star = new StarData();
			
			// generate random information for the star.
			// star is randomly placed across the background area.
			// brightness increment is set to a value between 5-30 units/sec.
			star.x = dataGenerator.nextFloat();
			star.y = dataGenerator.nextFloat();
			star.currentBrightness = dataGenerator.nextInt(180);
			star.brightnessIncrement = (dataGenerator.nextFloat() * 3.0f + 0.5f);
			star.starAlpha = SIN_VALUES[(int) star.currentBrightness];
			
			stars.add(star);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// Because stars don't blink if fancy graphics is not enabled,
		// there's no need to do all the calculations if fancy graphics
		// is disabled.
		if(!fancyMode) return;
		
		StarData star;
		Iterator<StarData> starIterator = stars.iterator();
		while(starIterator.hasNext()) {
			star = starIterator.next();
			star.currentBrightness += star.brightnessIncrement * delta;
			star.currentBrightness %= 180.0;
			star.starAlpha = SIN_VALUES[(int) star.currentBrightness];
		}
	}
	
	/**
	 * This variable is used by draw() method. SpriteBatch color is saved
	 * here while we are working with alpha transparency and then is
	 * reloaded after rendering stars. This field saves the garbage collector
	 * from creating and destroying a Color object each tick.
	 */
	private Color color;
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		color = batch.getColor();
		
		// I know this look ugly but I think that it's more efficient not
		// to test fancyMode in every single iteration of the loop. Instead,
		// test before entering the loop.
		Iterator<StarData> starIterator = stars.iterator();
		StarData star;
		if(fancyMode) {
			// If fancy graphics are enabled, we set the alpha for each star
			// in order to make them look brighter or darker. Alpha was
			// calculated in act().
			while(starIterator.hasNext()) {
				star = starIterator.next();
				batch.setColor(1.0f, 1.0f, 1.0f, star.starAlpha);
				batch.draw(starTexture, star.x * getWidth(), star.y * getHeight(), starTexture.getWidth() >> 1, starTexture.getHeight() >> 1);
			}	
		} else {
			// If fancy graphics is disabled, stars have full bright.
			while(starIterator.hasNext()) {
				star = starIterator.next();
				batch.draw(starTexture, star.x * getWidth(), star.y * getHeight(), starTexture.getWidth() >> 1, starTexture.getHeight() >> 1);
			}	
		}

		batch.setColor(color);
	}
}