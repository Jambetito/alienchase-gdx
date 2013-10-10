package tk.makigas.chase.actor.ui;

import static tk.makigas.chase.AlienChase.MANAGER;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Button actor.
 * 
 * @author danirod
 */
public class Button extends UIElement {
	
	/** Button texture. */
	protected Texture baseTex;
	
	/** Bitmap font used by the button. */
	protected BitmapFont font;
	
	/** Text label inside the button. */
	protected String text;
	
	/**
	 * Creates a new button.
	 * @param text label inside the button
	 */
	public Button(String text) {
		baseTex = MANAGER.get("ui/ui_button.png", Texture.class);
		font = MANAGER.get("ui/fixed12.fnt", BitmapFont.class);
		this.text = text;
	}
	
	/**
	 * Gets the button label.
	 * @return button label
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Changes the button label to a new value.
	 * @param text new value for the button
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// Button is rendered as three separated elements: left and right
		// borders and center area. This allows the button to be horizontally
		// resized without stretching borders: only center area gets
		// stretched.
		
		// Because button will be scaled, any parameter that depends on
		// width and height has to be scaled, that's why so many getScaleX/Y
		// calls in this snippet.
		
		// left border
		batch.draw(baseTex, getX(), getY(), // position
				2 * (int) getScaleX(), 16 * (int) getScaleY(), // size
				0, 0, 2, 16, false, false); // source skin coordinates
		// center area
		batch.draw(baseTex, getX() + 2 * (int) getScaleX(), getY(), // position
				(int) getScaleX() * (getWidth() - 4), // x-size
				(int) getScaleY() * 16, // y-size
				2, 0, 12, 16, false, false); // source skin coordinates
		// right border
		batch.draw(baseTex,
				getX() + (int) getScaleX() * (getWidth() - 2), getY(),
				(int) getScaleX() * 2, 16 * (int) getScaleY(),
				14, 0, 2, 16, false, false);
		
		// Next step: font
		font.setScale(getScaleX(), getScaleY());
		font.drawMultiLine(batch, text,
				getX(), getY() + getHeight() * getScaleY(),
				getWidth() * getScaleX(), HAlignment.CENTER);
	}
}
