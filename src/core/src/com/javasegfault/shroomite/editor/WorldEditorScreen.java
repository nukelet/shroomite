package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.MainMenuScreen;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.util.Position;

public class WorldEditorScreen extends ScreenAdapter {
    public static final Position OUT_OF_GRID_POSITION = new Position(-1, -1);

    private final Shroomite game;
    private Grid grid;
    private String referencedFileName;
    private Position lastMousePosition;
    private final Stage stage;
    private final Label referencedFileNameLabel;
    private final List<String> toolsList;
    private final GridTable gridTable;
    private final List<BlockType> blockTypeList;
    private final StatusBarLabel statusBarLabel;
    private final List<String> entitiesList;

    public WorldEditorScreen(final Shroomite game) {
        this.game = game;

        FitViewport viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        grid = new Grid();
        gridTable = new GridTable(game.skin, this, grid);
        gridTable.updateCellsDrawables();

        toolsList = new List<String>(game.skin);
        toolsList.setItems(new String[] { "Block", "Entity", "Eraser" });
        toolsList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                statusBarLabel.updateText();
            }
        });

        blockTypeList = new List<BlockType>(game.skin);
        blockTypeList.setItems(BlockType.values());
        blockTypeList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                statusBarLabel.updateText();
            }
        });

        entitiesList = new List<String>(game.skin);
        entitiesList.setItems(new String[] { "PLAYER", "LEVER", "LEVEL_EXIT" });

        lastMousePosition = OUT_OF_GRID_POSITION;

        statusBarLabel = new StatusBarLabel(game.skin, this);

        referencedFileNameLabel = new Label(null, game.skin);
        setReferencedFileName("worlds/untitled.grid");

        createNewGrid(16, 16);

        // Tabela base da interface
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Barra de menu superior
        final TextButton newGridButton = new TextButton("New", game.skin);
        newGridButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                NewGridDialog newGridDialog = new NewGridDialog(game.skin, WorldEditorScreen.this);
                newGridDialog.show(stage);
            }
        });
        final TextButton openFileButton = new TextButton("Open", game.skin);
        openFileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                OpenGridFromFileDialog openGridFromFileDialog = new OpenGridFromFileDialog(game.skin, WorldEditorScreen.this);
                openGridFromFileDialog.show(stage);
            }
        });
        final TextButton saveFileButton = new TextButton("Save", game.skin);
        saveFileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveGridToFileDialog saveDialog = new SaveGridToFileDialog(game.skin, WorldEditorScreen.this);
                saveDialog.show(stage);
            }
        });
        final TextButton clearGridButton = new TextButton("Clear", game.skin);
        clearGridButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ClearGridDialog clearGridDialog = new ClearGridDialog(game.skin, WorldEditorScreen.this);
                clearGridDialog.show(stage);
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
        menuBarGroup.addActor(newGridButton);
        menuBarGroup.addActor(openFileButton);
        menuBarGroup.addActor(saveFileButton);
        menuBarGroup.addActor(clearGridButton);
        menuBarGroup.addActor(exitButton);
        menuBarGroup.addActor(referencedFileNameLabel);
        menuBarGroup.space(10);
        table.add(menuBarGroup).colspan(3).left();

        // Barra de ferramentas lateral
        table.row();

        final Label toolbarLabel = new Label("Tools", game.skin);
        final ScrollPane toolbarPane = new ScrollPane(toolsList, game.skin);
        final VerticalGroup toolbarGroup = new VerticalGroup();
        toolbarGroup.addActor(toolbarLabel);
        toolbarGroup.addActor(toolbarPane);
        toolbarGroup.grow();
        table.add(toolbarGroup).align(Align.topLeft).width(100);

        // Área central, onde fica a grid
        table.add(gridTable).grow();

        // Menu lateral de seleção de blocos
        final Label blocksLabel = new Label("Blocks", game.skin);
        final ScrollPane blocksScrollPane = new ScrollPane(blockTypeList, game.skin);
        final VerticalGroup blocksGroup = new VerticalGroup();
        blocksGroup.addActor(blocksLabel);
        blocksGroup.addActor(blocksScrollPane);
        blocksGroup.grow();
        final Label entityLabel = new Label("Entities", game.skin);
        final ScrollPane entityPane = new ScrollPane(entitiesList, game.skin);
        final VerticalGroup entityGroup = new VerticalGroup();
        entityGroup.addActor(entityLabel);
        entityGroup.addActor(entityPane);
        entityGroup.grow();
        final Table rightMenuTable = new Table(game.skin);
        rightMenuTable.add(blocksGroup).growX().pad(20, 0, 20, 0);
        rightMenuTable.row();
        rightMenuTable.add(entityGroup).growX();
        table.add(rightMenuTable).align(Align.topRight).width(100).pad(20, 0, 20, 0);

        // Barra de status
        table.row();
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

    public int getGridWidth() {
        return grid.getWidth();
    }

    public int getGridHeight() {
        return grid.getHeight();
    }

    public Position getLastMousePosition() {
        return lastMousePosition;
    }

    public void setLastMousePosition(Position mousePosition) {
        this.lastMousePosition = mousePosition;
    }

    public void updateStatusBarLabelText() {
        statusBarLabel.updateText();
    }

    public String getSelectedTool() {
        return toolsList.getSelected();
    }

    public BlockType getSelectedBlockType() {
        return blockTypeList.getSelected();
    }

    public String getSelectedEntity() {
        return entitiesList.getSelected();
    }

    public String getReferencedFileName() {
        return referencedFileName;
    }

    public void setReferencedFileName(String referencedFileName) {
        this.referencedFileName = referencedFileName;
        updateReferencedFileNameLabelText();
    }

    public void createNewGrid(int gridWidth, int gridHeight) {
        grid = new Grid(gridWidth, gridHeight);
        gridTable.setGrid(grid);
        gridTable.updateCellsDrawables();
        statusBarLabel.updateText();
        setReferencedFileName("worlds/untitled.grid");
    }

    public void loadGrid(String fileName) {
        grid.loadState(fileName);
        gridTable.setGrid(grid);
        gridTable.updateCellsDrawables();
        statusBarLabel.updateText();
        setReferencedFileName(fileName);
    }

    public void saveGrid(String fileName) {
        grid.saveState(fileName);
        setReferencedFileName(fileName);
    }

    public void clearGrid() {
        grid.clear();
        gridTable.updateCellsDrawables();
    }

    private void updateReferencedFileNameLabelText() {
        referencedFileNameLabel.setText(referencedFileName.substring(referencedFileName.lastIndexOf("/") + 1));
    }
}
