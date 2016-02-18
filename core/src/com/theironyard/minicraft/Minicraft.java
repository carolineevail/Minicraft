package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.tools.classfile.TypeAnnotation;
import com.sun.tools.javac.util.Position;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;

    SpriteBatch batch;
    TextureRegion down, up, right, left, currentImage;

    float x, y, xv, yv;


    static final float MAX_VELOCITY = 700;


    @Override
    public void create () {
        batch = new SpriteBatch();

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        left.flip(true, false);
        currentImage = right;
    }

    @Override
    public void render () {
        move();
        draw();
    }

    float declerate(float velocity) {
        float deceleration = 0.7f; // the closer to 1, the slower the deceleration.
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }

    void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
        }

        yv = declerate(yv);
        xv = declerate(xv);
    }

    public void draw (){
        float oldX = x;
        float oldY = y;
        y += yv * Gdx.graphics.getDeltaTime();
        x += xv * Gdx.graphics.getDeltaTime();

        if (y < -5 || y > (Gdx.graphics.getHeight() - 85)) {
            y = oldY;
        }

        if (x < -20 || x > (Gdx.graphics.getWidth() - 90)) {
            x = oldX;
        }

        if (yv > 0) {
            currentImage = up;
        }
        else if (yv < 0) {
            currentImage = down;
        }
        else if (xv > 0){
            currentImage = right;
        }
        else if (xv < 0) {
            currentImage = left;
        }

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(currentImage, x, y, WIDTH, HEIGHT);
        batch.end();
    }
}


//        When hitting an edge, make the player "teleport" to the opposite edge.
//        When moving up or down, animate the movement by constantly flipping the tile horizontally.
//        Bring in the zombie tiles to draw a bad guy that walks around randomly.
//        Draw a green background, then bring in the tree tile and draw it randomly in the background.
//        Use a FitViewport to make the game scale as the window is resized.
