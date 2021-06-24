package com.javasegfault.shroomite;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.DirtBlock;
import com.javasegfault.shroomite.blocks.LavaBlock;
import com.javasegfault.shroomite.blocks.RockBlock;
import com.javasegfault.shroomite.blocks.SandBlock;
import com.javasegfault.shroomite.blocks.WaterBlock;
import com.javasegfault.shroomite.blocks.WoodBlock;
import com.javasegfault.shroomite.util.Position;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.agents.PlayerAgent;

public class GameScreen extends ScreenAdapter {
	private final Shroomite game;
	private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont font = new BitmapFont();
	
	private int BLOCK_WIDTH = Shroomite.BLOCK_WIDTH;
	private int BLOCK_HEIGHT = Shroomite.BLOCK_HEIGHT;
	private int GRID_WIDTH = Shroomite.GRID_WIDTH;
	private int GRID_HEIGHT = Shroomite.GRID_HEIGHT;
	
	private Block blocks[][] = new Block[GRID_HEIGHT][GRID_WIDTH];
	private Block lastClickedBlock;

    private World world = new World(GRID_WIDTH, GRID_HEIGHT);
    private Physics physics = new Physics(world);
    private PlayerAgent player = new PlayerAgent(world, new Vector2(3.0f * BLOCK_WIDTH, 20.0f * BLOCK_HEIGHT));

    public GameScreen(final Shroomite game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		generateWorld();
		lastClickedBlock = null;
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
		handleInput();
        physics.updatePlayer(player, delta);

        game.batch.begin();
		renderWorld();
        float fps = 1/Gdx.graphics.getDeltaTime();
        font.draw(game.batch, String.format("%.2f", fps), 50, 500);

        font.draw(game.batch,String.format("velocity: (%.2f, %.2f)",
                    player.getSpeed().x, player.getSpeed().y), 300, 500);
        
        Array<Block> collidingBlocks = physics.getCollidingBlocks(player);
        for (Block block : collidingBlocks) {
            game.drawBlockRegion(Shroomite.textures.get(TextureName.WATER_CRITICAL),
                    block.getPosition().getX(), block.getPosition().getY());
        }

        // bottom left corner coordinates
        Vector2 pos = player.getPosition();
        int gridPosX0 = (int) (pos.x/(float) BLOCK_WIDTH);
        int gridPosY0 = (int) (pos.y/(float) BLOCK_HEIGHT);
        font.draw(game.batch,String.format("grid position: (%d, %d)", gridPosX0, gridPosY0), 600, 500);

        float playerX = player.getPosition().x;
        float playerY = player.getPosition().y;
        game.batch.draw(player.getTexture(), playerX, playerY,
                player.getTextureWidth(), player.getTextureHeight());

		game.batch.end();

        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1.0f);

        for (int x = 0; x < GRID_WIDTH * BLOCK_WIDTH; x++) {
            shapeRenderer.line(BLOCK_WIDTH*x, 0, BLOCK_WIDTH*x, 800);
        }

        for (int y = 0; y < GRID_HEIGHT * BLOCK_HEIGHT; y++) {
            shapeRenderer.line(0, BLOCK_HEIGHT*y, 800, BLOCK_HEIGHT*y);
        }

        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(pos.x, pos.y, player.getWidth(), player.getHeight());
        shapeRenderer.end();
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
	public void drawBlockRegion(Texture texture, int gridPosX, int gridPosY) {
		game.batch.draw(texture, gridPosX*BLOCK_WIDTH, gridPosY*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
	}

    private void generateWorld() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            Position lowerHorizontal = new Position(x, 0);
            Position upperHorizontal = new Position(x, GRID_HEIGHT-1);
            world.addBlock(new RockBlock(lowerHorizontal, world));
            world.addBlock(new RockBlock(upperHorizontal, world));
        }

        for (int y = 0; y < GRID_HEIGHT; y++) {
            Position leftVertical = new Position(0, y);
            Position rightVertical = new Position(GRID_WIDTH-1, y);
            world.addBlock(new RockBlock(leftVertical, world));
            world.addBlock(new RockBlock(rightVertical, world));
        }

        for (int x = GRID_WIDTH / 2 - 10; x < GRID_WIDTH / 2 + 10; x++) {
            for (int y = GRID_HEIGHT - 10; y < GRID_HEIGHT - 1; y++) {
                Position pos = new Position(x, y);
                world.addBlock(new WaterBlock(pos, world));
            }
        }

        for (int x = 0; x < 11; x++) {
            Position pos = new Position(x+4, x+14);
            world.addBlock(new RockBlock(pos, world));
        }
    }
	
    private long lastPhysicsCallTime = 0;
    private long lastMousePressTime = 0;

	private void handleInput() {
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            long currentTime = TimeUtils.millis();

            Vector3 mousePos = new Vector3(0, 0, 0);
            mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos);
            System.out.println("x = " + mousePos.x + ", y = " + mousePos.y);
            int mouseWorldPosX = (int) (mousePos.x / BLOCK_WIDTH);
            int mouseWorldPosY = (int) (mousePos.y / BLOCK_HEIGHT);
            if (currentTime - lastMousePressTime > 500) {
                System.out.println("world coordinates: x = " + mouseWorldPosX + ", y = " + mouseWorldPosY);
                System.out.println(world.getBlockAt(mouseWorldPosX, mouseWorldPosY));
                lastMousePressTime = currentTime;
            }
		}

        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
            long currentTime = TimeUtils.millis();
            if (currentTime - lastPhysicsCallTime > 17) {
                physics.updatePositions();
                lastPhysicsCallTime = TimeUtils.millis();
            }
        }

        /*
         * 5 blocks
         * 5*BLOCK_HEIGHT
         * v0^2/2a = 5*BLOCK_HEIGHT
         *
         * */
        if (Gdx.input.isKeyPressed(Keys.SPACE) && player.speed.y == 0) {
            player.setSpeed(player.speed.x, 400);
        }
        
        float speedX = 250;
        if (Gdx.input.isKeyPressed(Keys.D)) {
            player.setSpeed(speedX, player.speed.y);
        } else if (Gdx.input.isKeyPressed(Keys.A)) {
            player.setSpeed(-speedX, player.speed.y);
        } else {
            player.setSpeed(0, player.speed.y);
        }

        boolean noClip = false;
        if (noClip) {

            float speedY = 250;
            if (Gdx.input.isKeyPressed(Keys.W)) {
                player.setSpeed(player.speed.x, speedY);
            } else if (Gdx.input.isKeyPressed(Keys.S)) {
                player.setSpeed(player.speed.x, -speedY);
            } else {
                player.setSpeed(player.speed.x, 0);
            }
        }
	}

	private void renderWorld() {
		for (int i = 0; i < GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
				Block block = world.getBlockAt(i, j);
				if (block != null) {
					Texture texture = block.getTexture();
                    Position pos = block.getPosition();
                    game.drawBlockRegion(texture, pos.getX(), pos.getY());
				} else {
					// desenha a textura padrão para a altura correspondente
					// Texture texture = null;
					// if (j == 15 || j == 14) {
					// 	texture = Shroomite.textures.get(TextureName.SKY_1);
					// } else if (j == 13) {
					// 	texture = Shroomite.textures.get(TextureName.SKY_2);
					// } else if (j == 12) {
					// 	texture = Shroomite.textures.get(TextureName.SKY_3);
					// } else {
					// 	texture = Shroomite.textures.get(TextureName.SKY_4);
					// }
					// drawBlockRegion(texture, i, j);
				}
			}
		}
	}
}
