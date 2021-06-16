package com.javasegfault.shroomite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WorldEditorScreen extends ScreenAdapter {
	private final Shroomite game;
	private Stage stage;

	private String selectedTool;
	private BlockType selectedBlockType = null;
	private StatusBarInfo statusBarInfo = new StatusBarInfo();
	private Label statusBarLabel;
	private Image gridTableImages[][];
	
	public WorldEditorScreen(final Shroomite game) {
		this.game = game;
		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		// Tabela base da interface
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
//		table.setDebug(true);
		
		// Barra de menu superior
		final TextButton newFileButton = new TextButton("New", game.skin);
		final TextButton openFileButton = new TextButton("Open", game.skin);
		final TextButton saveFileButton = new TextButton("Save", game.skin);
		final TextButton clearGridButton = new TextButton("Clear", game.skin);
		clearGridButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++) {
						gridTableImages[j][i].setDrawable(Shroomite.defaultTextureDrawable);
					}
				}
			}
		});
		final TextButton exitButton = new TextButton("Exit", game.skin);
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				game.setScreen(new MainMenuScreen(game));
			}
		});
		final HorizontalGroup menuBarGroup = new HorizontalGroup();
		menuBarGroup.addActor(newFileButton);
		menuBarGroup.addActor(openFileButton);
		menuBarGroup.addActor(saveFileButton);
		menuBarGroup.addActor(clearGridButton);
		menuBarGroup.addActor(exitButton);
		menuBarGroup.space(10);
		table.add(menuBarGroup).colspan(3).left();
		
		// Barra de ferramentas lateral
		table.row();
		final List<String> toolsList = new List<String>(game.skin);
		toolsList.setItems(new String[]{"Pencil", "Eraser"});
		selectedTool = toolsList.getSelected();
		toolsList.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				selectedTool = toolsList.getSelected();
				statusBarInfo.setSelectedTool(selectedTool);
				statusBarLabel.setText(statusBarInfo.getLabelText());
			}
		});
		final ScrollPane toolbarPane = new ScrollPane(toolsList, game.skin);
		table.add(toolbarPane).align(Align.topLeft).width(100).pad(40, 0, 40, 0);
		
		// Área central, onde fica a grid
		Table gridTable = new Table(game.skin);
		gridTableImages = new Image[16][16];
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				final Image image = new Image(Shroomite.textures.get(TextureName.SKY_4));
				image.addListener(new ClickListener() {
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//						System.out.printf("touchDown(): (%f, %f), pointer = %d, button = %d\n", x, y, pointer, button);
						// button = 0 (left click), button = 1 (right click)
						if (statusBarInfo.getSelectedTool().equals("Pencil")) {
							switch (statusBarInfo.getSelectedBlockType()) {
							case DIRT:
								image.setDrawable(Shroomite.texturesDrawables.get(TextureName.DIRT));
								break;
							case ROCK:
								image.setDrawable(Shroomite.texturesDrawables.get(TextureName.ROCK));
								break;
							case SAND:
								image.setDrawable(Shroomite.texturesDrawables.get(TextureName.SAND));
								break;
							case WOOD:
								image.setDrawable(Shroomite.texturesDrawables.get(TextureName.WOOD));
								break;
							case WATER:
								image.setDrawable(Shroomite.texturesDrawables.get(TextureName.WATER));
								break;
							case LAVA:
								image.setDrawable(Shroomite.texturesDrawables.get(TextureName.LAVA));
								break;
							default:
								image.setDrawable(Shroomite.defaultTextureDrawable);
								break;
							}
						} else if (statusBarInfo.getSelectedTool().equals("Eraser")) {
							image.setDrawable(Shroomite.defaultTextureDrawable);
						}
						return true;
					}
					
					@Override
					public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						if (pointer == -1 || !(toActor instanceof Image)) return;
						
						Image toImage = (Image)toActor;
						if (statusBarInfo.getSelectedTool().equals("Pencil")) {
							switch (statusBarInfo.getSelectedBlockType()) {
							case DIRT:
								toImage.setDrawable(Shroomite.texturesDrawables.get(TextureName.DIRT));
								break;
							case ROCK:
								toImage.setDrawable(Shroomite.texturesDrawables.get(TextureName.ROCK));
								break;
							case SAND:
								toImage.setDrawable(Shroomite.texturesDrawables.get(TextureName.SAND));
								break;
							case WOOD:
								toImage.setDrawable(Shroomite.texturesDrawables.get(TextureName.WOOD));
								break;
							case WATER:
								toImage.setDrawable(Shroomite.texturesDrawables.get(TextureName.WATER));
								break;
							case LAVA:
								toImage.setDrawable(Shroomite.texturesDrawables.get(TextureName.LAVA));
								break;
							default:
								toImage.setDrawable(Shroomite.defaultTextureDrawable);
								break;
							}
						} else if (statusBarInfo.getSelectedTool().equals("Eraser")) {
							toImage.setDrawable(Shroomite.defaultTextureDrawable);
						}
						
//						System.out.printf("exit(): (%f, %f), pointer = %d, toImage = %s\n", x, y, pointer, toImage);
					}
				});
				gridTableImages[j][i] = image;
				gridTable.add(image).grow();
			}
			gridTable.row();
		}
		table.add(gridTable).grow();
		
		// Menu lateral de seleção de blocos
		final List<BlockType> blockTypeList = new List<BlockType>(game.skin);
		blockTypeList.setItems(BlockType.values());
		selectedBlockType = blockTypeList.getSelected();
		blockTypeList.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				selectedBlockType = blockTypeList.getSelected();
				statusBarInfo.setSelectedBlockType(selectedBlockType);
				statusBarLabel.setText(statusBarInfo.getLabelText());
			}
		});
		final ScrollPane blocksScrollPane = new ScrollPane(blockTypeList);
		table.add(blocksScrollPane).align(Align.topRight).width(100).pad(40, 0, 40, 0);
		
		// Barra de status
		statusBarInfo.setGridDimensions(16, 16);
		statusBarInfo.setSelectedBlockType(blockTypeList.getSelected());
		statusBarInfo.setSelectedTool(toolsList.getSelected());
		table.row();
		statusBarLabel = new Label(statusBarInfo.getLabelText(), game.skin);
		final HorizontalGroup statusBar = new HorizontalGroup();
		statusBar.addActor(statusBarLabel);
		table.add(statusBar).left().colspan(3);
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 0);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}
