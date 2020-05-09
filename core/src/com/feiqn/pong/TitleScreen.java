package com.feiqn.pong;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TitleScreen extends ScreenAdapter {

    PongGame game;

    private Stage stage;

    public TitleScreen(PongGame game) { this.game = game; }

    @Override
    public void show() {
        stage = new Stage();

        Label.LabelStyle titleLabelStyle = new Label.LabelStyle();
        Label.LabelStyle menuLabelStyle = new Label.LabelStyle();

        titleLabelStyle.font = game.titleFont;
        menuLabelStyle.font = game.menuFont;

        Label titleLabel = new Label("PONG", titleLabelStyle);
        titleLabel.setPosition(Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .80f);

        Label menuLabel1 = new Label("Press \"W\" for single player", menuLabelStyle);
        Label menuLabel2 = new Label("Press UP ARROW for two players", menuLabelStyle);

        menuLabel1.setPosition(Gdx.graphics.getWidth() * .10f, Gdx.graphics.getHeight() * .25f);
        menuLabel2.setPosition(Gdx.graphics.getWidth() * .05f, Gdx.graphics.getHeight() * .15f);

        stage.addActor(titleLabel);
        stage.addActor(menuLabel1);
        stage.addActor(menuLabel2);

        InputProcessor MenuInputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.W) {
                    game.setScreen(new GameScreen(game));
                    return true;
                } else if (keycode == Input.Keys.UP) {
                    GameScreen.setMultiplayer();
                    game.setScreen(new GameScreen(game));
                    return true;
                }
                return false;
            }
        };
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(MenuInputProcessor);
        Gdx.input.setInputProcessor(multiplexer);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}

