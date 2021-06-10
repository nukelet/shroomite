package mc322.lab07;

import mc322.lab07.blocks.*;
import mc322.lab07.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

public class Shroomite extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture img;
    World world;
    Physics physics;
    ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("cobblestone.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        shapeRenderer = new ShapeRenderer();
        world = new World();
        physics = new Physics(world);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);

        for (int i = 0; i < world.getWidth(); i++) {
            shapeRenderer.line(40*i, 0, 40*i, 800);
        }

        for (int j = 0; j < world.getHeight(); j++) {
            shapeRenderer.line(0, 40*j, 800, 40*j);
        }

        shapeRenderer.end();

		batch.begin();

        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                Block block = world.getBlockAt(i, j);
                if (block != null) {
                    Position pos = block.getPosition();
                    batch.draw(block.texture, 40*pos.x, 40*pos.y, 40, 40);
                }
            }
        }
        
		batch.end();

        // update physics
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            physics.updateInteractions();
            physics.updatePositions();
        }
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
