package com.feiqn.pong;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
*  This is my very first ever LibGDX project, made mostly
*  to learn and play around with the framework.
*/
public class PongGame extends Game { // TODO: set screen size
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont titleFont;
	BitmapFont menuFont;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		titleFont = new BitmapFont();
		menuFont = new BitmapFont();

		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("COPPERPLATE.ttf"));

		FreeTypeFontGenerator.FreeTypeFontParameter titleFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter menuFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		titleFontParameter.size = 80;
		menuFontParameter.size = 30;

		titleFontParameter.borderWidth = 3;
		menuFontParameter.borderWidth = 1;

		titleFontParameter.color = Color.WHITE;
		menuFontParameter.color = Color.WHITE;

		titleFontParameter.shadowOffsetX = 4;
		titleFontParameter.shadowOffsetY = 1;
		titleFontParameter.shadowColor = Color.GRAY;

		menuFont = fontGenerator.generateFont(menuFontParameter);
		titleFont = fontGenerator.generateFont(titleFontParameter);

		fontGenerator.dispose();

		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		menuFont.dispose();
		titleFont.dispose();
	}
}
