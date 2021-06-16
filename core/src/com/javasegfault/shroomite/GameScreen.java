package com.javasegfault.shroomite;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.javasegfault.shroomite.block.Block;
import com.javasegfault.shroomite.block.DirtBlock;
import com.javasegfault.shroomite.block.LavaBlock;
import com.javasegfault.shroomite.block.RockBlock;
import com.javasegfault.shroomite.block.SandBlock;
import com.javasegfault.shroomite.block.WaterBlock;
import com.javasegfault.shroomite.block.WoodBlock;
import com.javasegfault.shroomite.util.Position;

public class GameScreen extends ScreenAdapter {
    private final Shroomite game;
    private OrthographicCamera camera;

    private static final int BLOCK_WIDTH = 32;
    private static final int BLOCK_HEIGHT = 32;
    private static final int GRID_WIDTH = 16;
    private static final int GRID_HEIGHT = 16;

    private Block blocks[][] = new Block[GRID_HEIGHT][GRID_WIDTH];
    private Block lastClickedBlock;

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
//		ScreenUtils.clear(0, 0, 0, 1);
//
//        camera.update();
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeType.Line);
//        shapeRenderer.setColor(1, 1, 1, 1);
//
//        for (int i = 0; i < world.getWidth(); i++) {
//            shapeRenderer.line(40*i, 0, 40*i, 800);
//        }
//
//        for (int j = 0; j < world.getHeight(); j++) {
//            shapeRenderer.line(0, 40*j, 800, 40*j);
//        }
//
//        shapeRenderer.end();
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
        handleInput();
        game.batch.begin();
        renderWorld();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    public void drawBlockRegion(Texture texture, int gridPosX, int gridPosY) {
        game.batch.draw(texture, gridPosX * BLOCK_WIDTH, gridPosY * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
    }

    private void generateWorld() {
        Random rand = new Random();

        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                switch (i) {
                case 7:
                case 6:
                case 5:
                    if (j == 2 || j == 7 || j == 10) {
                        WoodBlock woodBlock = new WoodBlock(game, new Position(j, i));
                        // há 20% de chance de um bloco de madeira nascer pegando fogo
                        if (rand.nextFloat() < 0.2f) {
                            woodBlock.setOnFire(true);
                        }
                        blocks[i][j] = woodBlock;
                    } else {
                        blocks[i][j] = null;
                    }
                    break;
                case 4:
                    blocks[i][j] = new DirtBlock(game, new Position(j, i));
                    break;
                case 3:
                    blocks[i][j] = new WaterBlock(game, new Position(j, i));
                    break;
                case 2:
                    blocks[i][j] = new SandBlock(game, new Position(j, i));
                    break;
                case 1:
                    blocks[i][j] = new RockBlock(game, new Position(j, i));
                    break;
                case 0:
                    blocks[i][j] = new LavaBlock(game, new Position(j, i));
                    break;
                default:
                    blocks[i][j] = null;
                    break;
                }
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            int posX = Gdx.input.getX() / BLOCK_WIDTH;
            int posY = (Gdx.graphics.getHeight() - Gdx.input.getY()) / BLOCK_HEIGHT;
//			System.out.println("x = " + posX + ", y = " + posY);
            if (posX >= 0 && posX < GRID_WIDTH && posY >= 0 && posY < GRID_HEIGHT) {
                Block clickedBlock = blocks[posY][posX];
                if (clickedBlock != null && clickedBlock != lastClickedBlock) {
                    System.out.println(clickedBlock);
                    lastClickedBlock = clickedBlock;
                }
            }
        }
    }

    private void renderWorld() {
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                Block block = blocks[i][j];
                if (block != null) {
                    block.render();
                } else {
                    // desenha a textura padrão para a altura correspondente
                    Texture texture = null;
                    if (i == 15 || i == 14) {
                        texture = Shroomite.textures.get(TextureName.SKY_1);
                    } else if (i == 13) {
                        texture = Shroomite.textures.get(TextureName.SKY_2);
                    } else if (i == 12) {
                        texture = Shroomite.textures.get(TextureName.SKY_3);
                    } else {
                        texture = Shroomite.textures.get(TextureName.SKY_4);
                    }
                    drawBlockRegion(texture, j, i);
                }
            }
        }
    }
}
