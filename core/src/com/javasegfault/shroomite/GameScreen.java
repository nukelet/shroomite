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
	
	// Código anterior
//	World world;
//    Physics physics;
//    ShapeRenderer shapeRenderer;

    public GameScreen(final Shroomite game) {
        // Código anterior
//		shapeRenderer = new ShapeRenderer();
//        world = new World();
//        physics = new Physics(world);
		
		
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
        
        // TODO: hard-coded
        // also this creates problems when the division is
        // rounded down :/
        // BLOCK_WIDTH = 800 / GRID_WIDTH;
        // BLOCK_HEIGHT = 600 / GRID_HEIGHT;
		
		generateWorld();
		lastClickedBlock = null;
	}
	
	@Override
	public void render(float delta) {
		// Código anterior
//		// update physics
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//            physics.updateInteractions();
//            physics.updatePositions();
//        }
//        
		// ScreenUtils.clear(0, 0, 0, 1);

        // camera.update();
        // shapeRenderer.setProjectionMatrix(camera.combined);
        // shapeRenderer.begin(ShapeType.Line);
        // shapeRenderer.setColor(1, 1, 1, 1);

        // for (int i = 0; i < world.getWidth(); i++) {
            // shapeRenderer.line(40*i, 0, 40*i, 800);
        // }

        // for (int j = 0; j < world.getHeight(); j++) {
            // shapeRenderer.line(0, 40*j, 800, 40*j);
        // }

        // shapeRenderer.end();
//
//		batch.begin();
//
//        for (int i = 0; i < world.getWidth(); i++) {
//            for (int j = 0; j < world.getHeight(); j++) {
//                Block block = world.getBlockAt(i, j);
//                if (block != null) {
//                    Position pos = block.getPosition();
//                    batch.draw(block.getTexture(), 40*pos.getX(), 40*pos.getY(), 40, 40);
//                }
//            }
//        }
//        
//		batch.end();
		
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
		handleInput();

        game.batch.begin();
		renderWorld();
        float fps = 1/Gdx.graphics.getDeltaTime();
        font.draw(game.batch, String.format("%.2f", fps), 50, 500);
		game.batch.end();

        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);

        for (int x = 0; x < GRID_WIDTH * BLOCK_WIDTH; x++) {
            shapeRenderer.line(BLOCK_WIDTH*x, 0, BLOCK_WIDTH*x, 800);
        }

        for (int y = 0; y < GRID_HEIGHT * BLOCK_HEIGHT; y++) {
            shapeRenderer.line(0, BLOCK_HEIGHT*y, 800, BLOCK_HEIGHT*y);
        }

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

        for (int y = world.getHeight() - 1; y >= world.getHeight() - 40; y--) {
            // Position pos = new Position(world.getWidth() / 2, y);
            // world.addBlock(new SandBlock(pos, world));
            // world.addBlock(new SandBlock(pos.right(), world));
            // world.addBlock(new SandBlock(pos.left(), world));
            
            // Position pos2 = pos.add(new Position(8, 0));
            // world.addBlock(new SandBlock(pos2, world));
            // world.addBlock(new SandBlock(pos2.right(), world));
            // world.addBlock(new SandBlock(pos2.left(), world));

            // Position pos3 = pos.add(new Position(-8, 0));
            // world.addBlock(new SandBlock(pos3, world));
            // world.addBlock(new SandBlock(pos3.right(), world));
            // world.addBlock(new SandBlock(pos3.left(), world));

            // Position pos = new Position(world.getWidth() / 2, y);
            // world.addBlock(new WaterBlock(pos, world));
            // world.addBlock(new WaterBlock(pos.right(), world));
            // world.addBlock(new WaterBlock(pos.left(), world));
        }

        for (int x = GRID_WIDTH / 2; x < GRID_WIDTH / 2 + 1; x++) {
            for (int y = GRID_HEIGHT - 8; y < GRID_HEIGHT - 1; y++) {
                Position pos = new Position(x, y);
                world.addBlock(new WaterBlock(pos, world));
                world.addBlock(new WaterBlock(pos.right(), world));
                world.addBlock(new WaterBlock(pos.left(), world));
            }
        }
    }
	
	// private void generateWorld() {
	// 	Random rand = new Random();
		
	// 	for (int i = 0; i < GRID_HEIGHT; i++) {
	// 		for (int j = 0; j < GRID_WIDTH; j++) {
	// 			switch (i) {
	// 			case 7:
	// 			case 6:
	// 			case 5:
	// 				if (j == 2 || j == 7 || j == 10) {
	// 					WoodBlock woodBlock = new WoodBlock(game, new Position(j, i));
	// 					// há 20% de chance de um bloco de madeira nascer pegando fogo
	// 					if (rand.nextFloat() < 0.2f) {
	// 						woodBlock.setOnFire(true);
	// 					}
	// 					blocks[i][j] = woodBlock;
	// 				} else {
	// 					blocks[i][j] = null;							
	// 				}
	// 				break;
	// 			case 4:
	// 				blocks[i][j] = new DirtBlock(game, new Position(j, i));
	// 				break;
	// 			case 3:
	// 				blocks[i][j] = new WaterBlock(game, new Position(j, i));
	// 				break;
	// 			case 2:
	// 				blocks[i][j] = new SandBlock(game, new Position(j, i));
	// 				break;
	// 			case 1:
	// 				blocks[i][j] = new RockBlock(game, new Position(j, i));
	// 				break;
	// 			case 0:
	// 				blocks[i][j] = new LavaBlock(game, new Position(j, i));
	// 				break;
	// 			default:
	// 				blocks[i][j] = null;
	// 				break;
	// 			}
	// 		}
	// 	}
	// }
	
    private long lastPhysicsCallTime = 0;
    // TODO: this is broken and needs to be fixed
	private void handleInput() {
        // there is a problem here -- this depends on the size of the window
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

			int posX = (int) ((Gdx.input.getX()/screenWidth) * BLOCK_WIDTH);
			float posY = (Gdx.graphics.getHeight() - Gdx.input.getY())/Gdx.graphics.getHeight();
            // System.out.println("x = " + posX + ", y = " + posY);
            // System.out.println("x = " + Gdx.input.getX() + ", y = " + Gdx.input.getY());
            // System.out.printf("width: %d, height: %d\n", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			// if (posX >= 0 && posX < GRID_WIDTH && posY >= 0 && posY < GRID_HEIGHT) {
			// 	Block clickedBlock = world.getBlockAt(posX, posY);
			// 	if (clickedBlock != null && clickedBlock != lastClickedBlock) {
			// 		System.out.println(clickedBlock);
			// 		lastClickedBlock = clickedBlock;
			// 	}
			// }
		}

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            long currentTime = TimeUtils.millis();
            if (currentTime - lastPhysicsCallTime > 100) {
                physics.updatePositions();
                lastPhysicsCallTime = TimeUtils.millis();
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
					Texture texture = null;
					if (j == 15 || j == 14) {
						texture = Shroomite.textures.get(TextureName.SKY_1);
					} else if (j == 13) {
						texture = Shroomite.textures.get(TextureName.SKY_2);
					} else if (j == 12) {
						texture = Shroomite.textures.get(TextureName.SKY_3);
					} else {
						texture = Shroomite.textures.get(TextureName.SKY_4);
					}
					drawBlockRegion(texture, i, j);
				}
			}
		}
	}
}
