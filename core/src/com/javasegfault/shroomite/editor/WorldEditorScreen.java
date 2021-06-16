package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.javasegfault.shroomite.BlockType;
import com.javasegfault.shroomite.MainMenuScreen;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.util.Position;

public class WorldEditorScreen extends ScreenAdapter {
    private final Shroomite game;
    private Stage stage;

    private StatusBarLabel statusBarLabel;
    private int gridWidth;
    private int gridHeight;
    private GridBlockImage gridBlockImages[][];

    public WorldEditorScreen(final Shroomite game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        final List<String> toolsList = new List<String>(game.skin);
        toolsList.setItems(new String[] { "Pencil", "Eraser" });
        toolsList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                statusBarLabel.updateText();
            }
        });

        final List<BlockType> blockTypeList = new List<BlockType>(game.skin);
        blockTypeList.setItems(BlockType.values());
        blockTypeList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                statusBarLabel.updateText();
            }
        });

        gridWidth = 16;
        gridHeight = 16;
        gridBlockImages = new GridBlockImage[gridHeight][gridWidth];
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                Position gridBlockPosition = new Position(j, i);
                final GridBlockImage image = new GridBlockImage(gridBlockPosition, null);
                image.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (toolsList.getSelected().equals("Pencil")) {
                            image.setBlockType(blockTypeList.getSelected());
                        } else if (toolsList.getSelected().equals("Eraser")) {
                            image.setBlockType(null);
                        }

                        return true;
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        if (!(toActor instanceof GridBlockImage)) {
                            statusBarLabel.setMousePosition(-1, -1);
                            statusBarLabel.updateText();
                            return;
                        }

                        GridBlockImage toImage = (GridBlockImage) toActor;

                        statusBarLabel.setMousePosition(toImage.getPosition().getX(), toImage.getPosition().getY());
                        statusBarLabel.updateText();

                        if (pointer == -1)
                            return;

                        if (toolsList.getSelected().equals("Pencil")) {
                            toImage.setBlockType(blockTypeList.getSelected());
                        } else if (toolsList.getSelected().equals("Eraser")) {
                            toImage.setBlockType(null);
                        }
                    }
                });
                gridBlockImages[j][i] = image;
            }
        }

        // Tabela base da interface
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        // table.setDebug(true);

        // Barra de menu superior
        final TextButton newFileButton = new TextButton("New", game.skin);
        final TextButton openFileButton = new TextButton("Open", game.skin);
        final TextButton saveFileButton = new TextButton("Save", game.skin);
        final TextButton clearGridButton = new TextButton("Clear", game.skin);
        clearGridButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clearGridBlockImages();
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
        final ScrollPane toolbarPane = new ScrollPane(toolsList, game.skin);
        table.add(toolbarPane).align(Align.topLeft).width(100).pad(40, 0, 40, 0);

        // Área central, onde fica a grid
        Table gridTable = new Table(game.skin);
        for (int i = gridHeight - 1; i >= 0; i--) {
            for (int j = 0; j < gridWidth; j++) {
                gridTable.add(gridBlockImages[j][i]).grow();
            }
            gridTable.row();
        }
        table.add(gridTable).grow();

        // Menu lateral de seleção de blocos
        final ScrollPane blocksScrollPane = new ScrollPane(blockTypeList);
        table.add(blocksScrollPane).align(Align.topRight).width(100).pad(40, 0, 40, 0);

        // Barra de status
        table.row();
        statusBarLabel = new StatusBarLabel(game.skin, toolsList, blockTypeList, gridWidth, gridHeight, -1, -1);
        statusBarLabel.updateText();
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

    private void clearGridBlockImages() {
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                gridBlockImages[j][i].setBlockType(null);
            }
        }
    }
}
