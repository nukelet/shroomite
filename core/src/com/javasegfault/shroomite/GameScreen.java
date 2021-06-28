package com.javasegfault.shroomite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet.ObjectSetIterator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.javasegfault.shroomite.blocks.Block;
import com.javasegfault.shroomite.blocks.LavaBlock;
import com.javasegfault.shroomite.blocks.RockBlock;
import com.javasegfault.shroomite.entities.LevelExit;
import com.javasegfault.shroomite.entities.Lever;
import com.javasegfault.shroomite.entities.PlayerAgent;
import com.javasegfault.shroomite.entities.StatusEffect;
import com.javasegfault.shroomite.entities.UnlockableEntity;
import com.javasegfault.shroomite.physics.Physics;
import com.javasegfault.shroomite.util.Position;

public class GameScreen extends ScreenAdapter {
    private enum GameState {
        LEVEL_ONGOING,
        LEVEL_COMPLETE,
        GAME_OVER,
    }

    private GameState gameState;

	private final Shroomite game;
	private final String worldName;
	private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont font = new BitmapFont();

	private int BLOCK_WIDTH = Shroomite.BLOCK_WIDTH;
	private int BLOCK_HEIGHT = Shroomite.BLOCK_HEIGHT;
	private int GRID_WIDTH = Shroomite.GRID_WIDTH;
	private int GRID_HEIGHT = Shroomite.GRID_HEIGHT;

    private IWorld world;
    private Physics physics;
    private PlayerAgent player;
    Array<UnlockableEntity> unlockableEntities;
    LevelExit levelExit;

    private float framesPerSecond;
    private Position bottomLeftMousePosition;
    private Position mouseGridPosition;
    private Block blockPointedAt;
    private Position playerGridPosition;

    TextureAtlas atlas = new TextureAtlas("atlas/shroomite_textures.atlas");
    Animation<TextureRegion> playerAnimation =
        new Animation<TextureRegion>(0.125f, atlas.findRegions("elf_m_idle_anim"), PlayMode.LOOP);

    float stateTime;

    private Stage stage;
    private final Label infoLabel;
    private final Label debugInfoLabel;

    public GameScreen(final Shroomite game, final String worldName) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

        // libGDX looks for files in the core/assets/ folder
        this.worldName = worldName;
        WorldGenerator worldGenerator = new WorldGenerator("worlds/" + worldName);
        world = worldGenerator.getWorld();
        player = worldGenerator.getPlayer();
        unlockableEntities = worldGenerator.getUnlockableEntities();
        levelExit = worldGenerator.getLevelExit();

        physics = new Physics(world);

        BLOCK_WIDTH = Shroomite.BLOCK_WIDTH;
        BLOCK_HEIGHT = Shroomite.BLOCK_HEIGHT;

        GRID_WIDTH = world.getWidth();
        GRID_HEIGHT = world.getHeight();

        player = new PlayerAgent(world, new Vector2(3*BLOCK_WIDTH, 10*BLOCK_HEIGHT));

        bottomLeftMousePosition = new Position(-1, -1);
        mouseGridPosition = new Position(-1, -1);
        playerGridPosition = new Position(-1, -1);

        stateTime = 0;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        final Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        infoLabel = new Label(null, game.skin);
        rootTable.add(infoLabel).align(Align.topLeft);

        rootTable.add().grow();

        debugInfoLabel = new Label(null, game.skin);
        rootTable.add(debugInfoLabel).align(Align.topRight).width(250);
	}

    @Override
    public void render(float delta) {
        stateTime += delta;

        framesPerSecond = 1 / Gdx.graphics.getDeltaTime();
        bottomLeftMousePosition.set(Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY() - 1);
        mouseGridPosition.set(bottomLeftMousePosition.getX() / BLOCK_WIDTH,
                bottomLeftMousePosition.getY() / BLOCK_HEIGHT);
        blockPointedAt = world.getBlockAt(mouseGridPosition);
        playerGridPosition.set((int) (player.getPosition().x / BLOCK_WIDTH),
                (int) (player.getPosition().y / BLOCK_HEIGHT));

        camera.update();

        handleInput();

        physics.updatePlayer(player, delta);

        for (UnlockableEntity entity : unlockableEntities) {
            if (player.overlaps(entity)) {
                player.interact(entity);
            }
        }

        boolean isDoorUnlocked = true;
        for (UnlockableEntity entity : unlockableEntities) {
            if (entity.isLocked()) {
                isDoorUnlocked = false;
                break;
            }
        }

        if (isDoorUnlocked && player.overlaps(levelExit)) {
            gameState = GameState.LEVEL_COMPLETE;
        }

        if (player.getHp() < 0) {
            gameState = GameState.GAME_OVER;
        }

        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        game.batch.begin();
        renderBackground();
        renderWorld();
        drawCollidingBlocks();

        Rectangle textureRect = levelExit.getTextureRect(); 
        game.batch.draw(levelExit.getTexture(), textureRect.x, textureRect.y,
                textureRect.width, textureRect.height);

        for (UnlockableEntity entity : unlockableEntities) {
            textureRect = entity.getTextureRect(); 
            game.batch.draw(entity.getTexture(), textureRect.x, textureRect.y,
                    textureRect.width, textureRect.height);
        }

        game.batch.end();

        shapeRenderer.begin(ShapeType.Line);
        // drawGridLines();
        drawEntityHitboxes();
        shapeRenderer.end();

        game.batch.begin();
        drawPlayer();
        game.batch.end();

//        shapeRenderer.begin(ShapeType.Filled);
//        drawDebugInfoBackground();
//        shapeRenderer.end();

        game.batch.begin();
        drawDebugInfo();
        game.batch.end();

        String infoLabelText = String.format("HP: %d", player.getHp());
        ObjectSetIterator<StatusEffect> it = player.getStatusEffects().iterator();
        while (it.hasNext()) {
            StatusEffect statusEffect = it.next();
            infoLabelText += String.format("\n%s %ds", statusEffect.toString(), statusEffect.getRemainingTime() / 1000);
        }
        infoLabel.setText(infoLabelText);

        stage.act();
        stage.draw();
    }

    private void drawCollidingBlocks() {
        Array<Block> collidingBlocks = physics.getCollidingBlocks(player);
        for (Block block : collidingBlocks) {
            game.drawBlockRegion(Shroomite.textures.get(TextureName.WATER_CRITICAL),
                    block.getPosition().getX(), block.getPosition().getY());
        }
    }

    private void drawGridLines() {
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1.0f);

        for (int x = 0; x < GRID_WIDTH * BLOCK_WIDTH; x++) {
            shapeRenderer.line(BLOCK_WIDTH*x, 0, BLOCK_WIDTH*x, 800);
        }

        for (int y = 0; y < GRID_HEIGHT * BLOCK_HEIGHT; y++) {
            shapeRenderer.line(0, BLOCK_HEIGHT*y, 800, BLOCK_HEIGHT*y);
        }
    }

    private void drawEntityHitboxes() {
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(player.getPosition().x, player.getPosition().y,
                player.getWidth(), player.getHeight());
        shapeRenderer.rect(levelExit.getPosition().x, levelExit.getPosition().y,
                levelExit.getWidth(), levelExit.getHeight());
        for (UnlockableEntity entity : unlockableEntities) {
            shapeRenderer.rect(entity.getPosition().x, entity.getPosition().y,
                    entity.getWidth(), entity.getHeight());
        }
    }

    private void drawPlayer() {
        TextureRegion idleTexture = playerAnimation.getKeyFrame(stateTime, true);
        // game.batch.draw(player.getTexture(), player.getPosition().x, player.getPosition().y,
        //         player.getTextureWidth(), player.getTextureHeight());
        game.batch.draw(idleTexture, player.getPosition().x, player.getPosition().y,
                player.getTextureWidth(), player.getTextureHeight());
    }

    private void drawDebugInfoBackground() {
        shapeRenderer.setColor(0f, 0f, 0f, 0.2f);
        shapeRenderer.rect(538, 280, 252, 268);
    }

    private void drawDebugInfo() {
        String text = String.format("World: %s", worldName);
        text += String.format("\n%.0f FPS", framesPerSecond);
        text += String.format("\nPlayer position: (%.0f, %.0f)", player.getPosition().x, player.getPosition().y);
        text += String.format("\nPlayer grid position: (%d, %d)", playerGridPosition.getX(), playerGridPosition.getY());
        text += String.format("\nPlayer speed: (%.0f, %.0f)", player.getSpeed().x, player.getSpeed().y);
        text += String.format("\nTL mouse position: (%d, %d)", Gdx.input.getX(), Gdx.input.getY());
        text += String.format("\nBL mouse position: (%d, %d)", bottomLeftMousePosition.getX(), bottomLeftMousePosition.getY());
        text += String.format("\nMouse grid position: (%d, %d)", mouseGridPosition.getX(), mouseGridPosition.getY());
        text += String.format("\nBlock pointed at: %s", (blockPointedAt != null) ? blockPointedAt.getType() : "NONE");
        text += String.format("\nHP: %d", player.getHp());

        ObjectSetIterator<StatusEffect> it = player.getStatusEffects().iterator();
        while (it.hasNext()) {
            StatusEffect statusEffect = it.next();
            text += String.format("\n%s %ds", statusEffect.toString(), statusEffect.getRemainingTime() / 1000);
        }

        text += "\nInteracting: " + (player.interacting ? "yes" : "no");

        boolean leverLocked = true;
        for (UnlockableEntity lever : unlockableEntities) {
            if (!lever.isLocked()) {
                leverLocked = false;
                break;
            }
        }
        text += "\nLever locked: " + (leverLocked ? "yes" : "no");

        debugInfoLabel.setText(text);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public String getWorldName() {
        return worldName;
    }

    public String getWorldNameWithoutFileExtension() {
        return worldName.substring(0, worldName.lastIndexOf("."));
    }

    public int getPlayerHp() {
        return player.getHp();
    }

    public void goToMainMenu() {
        dispose();
        game.setScreen(new MainMenuScreen(game));
    }

    public void completeWorld() {
        CompletedWorldDialog cwd = new CompletedWorldDialog(game.skin, this);
        cwd.show(stage);
    }

	public void drawBlockRegion(Texture texture, int gridPosX, int gridPosY) {
		game.batch.draw(texture, gridPosX*BLOCK_WIDTH, gridPosY*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
	}
	
    private long lastPhysicsCallTime = 0;
    private long lastMousePressTime = 0;

	private void handleInput() {
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            long currentTime = TimeUtils.millis();

            Vector3 mousePos = new Vector3(0, 0, 0);
            mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos);
//            System.out.println("x = " + mousePos.x + ", y = " + mousePos.y);
            int mouseWorldPosX = (int) (mousePos.x / BLOCK_WIDTH);
            int mouseWorldPosY = (int) (mousePos.y / BLOCK_HEIGHT);

            if (currentTime - lastMousePressTime > 500) {
                System.out.println("world coordinates: x = " + mouseWorldPosX + ", y = " + mouseWorldPosY);
                Position mouseWorldPosition = new Position(mouseWorldPosX, mouseWorldPosY);
                if (world.hasBlockAt(mouseWorldPosition)) {
                    Block block = world.getBlockAt(mouseWorldPosition);
                    System.out.println(block);
                    player.breakBlock(block);
                }

                lastMousePressTime = currentTime;

                // if (block != null && block.getType() == BlockType.WATER) {
                //     Position pos = block.getPosition();

                //     System.out.println(world.getBlockAt(pos.up()));
                //     System.out.println(world.getBlockAt(pos.down()));
                //     System.out.println(world.getBlockAt(pos.left()));
                //     System.out.println(world.getBlockAt(pos.right()));
                // }
            }
		}

        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
            if (TimeUtils.timeSinceMillis(lastPhysicsCallTime) > 17) {
                physics.updatePositions();
                physics.updateInteractions();
                lastPhysicsCallTime = TimeUtils.millis();
            }
        }

        if (Gdx.input.isKeyPressed(Keys.E)) {
            if (!player.interacting) {
                player.enableInteractions();
            }
        }

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
        // boolean noClip = true;
        if (noClip) {
            physics.gravity = 0;
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

    private void renderBackground() {
		for (int x = 0; x < GRID_WIDTH; x++) {
			for (int y = 0; y < GRID_HEIGHT; y++) {
                int idx = x*GRID_WIDTH + y;
                Texture texture = null;
                if (idx % 3 == 0) {
                    texture = Shroomite.textures.get(TextureName.WALL_LEFT);
                } else if (idx % 3 == 1) {
                    texture = Shroomite.textures.get(TextureName.WALL_MID);
                } else {
                    texture = Shroomite.textures.get(TextureName.WALL_RIGHT);
                }
                game.drawBlockRegion(texture, x, y);
			}
		}
    }
	private void renderWorld() {
		for (int i = 0; i < GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
			    Position position = new Position(i, j);
			    if (world.hasBlockAt(position)) {
			        Block block = world.getBlockAt(position);
                    game.drawBlockRegion(block.getTexture(), position.getX(), position.getY());
                }
			}
		}
	}
}
