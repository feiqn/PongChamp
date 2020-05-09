package com.feiqn.pong;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Timer.*;
import java.util.Random;

public class GameScreen extends ScreenAdapter {

    PongGame game;
    private Texture paddleTexture;
    private Sprite p1, p2, ball, foresight;
    static boolean multiplayer;
    float ballX, paddle1X, paddle2X;
    float xMiddle = Gdx.graphics.getWidth() * .5f;
    float yMiddle = Gdx.graphics.getHeight() * .5f;
    float xSpeed = 4f;
    float ySpeed = 3f;
    int p1Score, p2Score = 0;
    float paddle1Y, paddle2Y, ballY = yMiddle;
    boolean p1UpMove, p1DownMove, p2UpMove, p2DownMove;
    Random random;


    public GameScreen(PongGame game) { this.game = game; }

    public static void setMultiplayer() {
        multiplayer = true;
    }

    @Override
    public void show() {

        paddleTexture = new Texture("white.jpg");
        p1 = new Sprite(paddleTexture, 15,100);
        p2 = new Sprite(paddleTexture, 15,100);
        ball = new Sprite(paddleTexture, 20, 20);

        paddle1X = 50f;
        paddle2X = Gdx.graphics.getWidth() - 50f;

        paddle1Y = yMiddle;
        paddle2Y = yMiddle;

        ballX = xMiddle;
        ballY = yMiddle;

        ball.setPosition(ballX, ballY);

        foresight = ball;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                switch (keycode) {
                    case Input.Keys.W:
                        p1UpMove = true;
                        break;
                    case Input.Keys.S:
                        p1DownMove = true;
                        break;
                    case Input.Keys.UP:
                        if (multiplayer) {
                            p2UpMove = true;
                        }
                        break;
                    case Input.Keys.DOWN:
                        if (multiplayer) {
                            p2DownMove = true;
                        }
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {

                switch(keycode) {
                    case Input.Keys.W:
                        p1UpMove = false;
                        break;
                    case Input.Keys.S:
                        p1DownMove = false;
                        break;
                    case Input.Keys.UP:
                        if(multiplayer) { p2UpMove = false; }
                        break;
                    case Input.Keys.DOWN:
                        if(multiplayer) { p2DownMove = false; }
                        break;
                }
                return true;
            }
        });
    }

    public void reset() { // TODO: display score, game over screen?
        ballY = yMiddle;
        ballX = xMiddle;
        xSpeed = 0;
        ySpeed = 0;
        Timer.schedule(new Task() { // kick off the ball once it has been reset
            public void run() {
                random = new Random();
                xSpeed = random.nextInt(6) + 2;
                ySpeed = random.nextInt(4) + 2;
            }
        }, 2f);
    }

    @Override
    public void render(float delta) {

        foresight.setPosition(ballX + xSpeed, ballY + ySpeed); // predict if the ball would collide with either paddle, 1 frame early

        p1.setPosition(paddle1X, paddle1Y);
        p2.setPosition(paddle2X, paddle2Y);

        ball.setPosition(ballX, ballY);

        //ball collision
        if(Intersector.overlaps(foresight.getBoundingRectangle(), p1.getBoundingRectangle()) || Intersector.overlaps(foresight.getBoundingRectangle(), p2.getBoundingRectangle())) { // bounce off the paddles
            xSpeed *= -1f;
            if(xSpeed > 0 && xSpeed < 13f) { // speed up
                xSpeed *= 1.2f;
            } else if(xSpeed < 0 && xSpeed > -13f) {
                xSpeed *= 1.2f;
            }
        } // TODO: fix bouncing off top & bottom of paddles (ballX > paddleX ...)
        if(ballX < 0) { // score
            xSpeed *= -1f;
            p2Score++;
            reset();
        } else if(ballX > Gdx.graphics.getWidth()) {
            xSpeed *= -1f;
            p1Score++;
            reset();
        }
        if(ballY < 0 || ballY > Gdx.graphics.getHeight()) { // bounce off the walls
            ySpeed *= -1f;
        }

        ballX += xSpeed;
        ballY += ySpeed;

        // p1 movement
        if(p1UpMove && paddle1Y < Gdx.graphics.getHeight() - 101f) {
            paddle1Y += 300f * Gdx.graphics.getDeltaTime();
        } else if(p1DownMove && paddle1Y > 0) {
            paddle1Y -= 300f * Gdx.graphics.getDeltaTime();
        }

        // p2 movement
        if(p2UpMove && paddle2Y < Gdx.graphics.getHeight() - 101f) {
            paddle2Y += 300f * Gdx.graphics.getDeltaTime();
        } else if(p2DownMove && paddle2Y > 0) {
            paddle2Y -= 300f * Gdx.graphics.getDeltaTime();
        } // TODO: single-player ai

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        p1.draw(game.batch);
        p2.draw(game.batch);
        ball.draw(game.batch);
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        game.batch.dispose();
    }

}
