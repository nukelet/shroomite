package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.javasegfault.shroomite.blocks.BlockType;
import com.javasegfault.shroomite.Shroomite;
import com.javasegfault.shroomite.util.Position;

public class GridTableCell extends Image {
    private final WorldEditorScreen worldEditorScreen;
    final private GridCell gridCell;

    public GridTableCell(final WorldEditorScreen worldEditorScreen, final GridCell gridCell) {
        super(Shroomite.texturesDrawables.get(gridCell.getBlockTypeTextureName()));
        this.worldEditorScreen = worldEditorScreen;
        this.gridCell = gridCell;
        addMainClickListener();
    }

    public Position getGridCellPosition() {
        return gridCell.getPosition();
    }

    public void setGridCellBlockType(BlockType blockType) {
        gridCell.setBlockType(blockType);
        setDrawable(Shroomite.texturesDrawables.get(blockType.getTextureName()));
    }

    public void updateDrawable() {
        setDrawable(Shroomite.texturesDrawables.get(gridCell.getBlockTypeTextureName()));
    }

    private void addMainClickListener() {
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String selectedTool = worldEditorScreen.getSelectedTool();
                if (selectedTool.equals("Pencil")) {
                    gridCell.setBlockType(worldEditorScreen.getSelectedBlockType());
                    updateDrawable();
                } else if (selectedTool.equals("Eraser")) {
                    gridCell.setBlockType(BlockType.AIR);
                    updateDrawable();
                }

                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (!(toActor instanceof GridTableCell)) {
                    worldEditorScreen.setLastMousePosition(WorldEditorScreen.OUT_OF_GRID_POSITION);
                    worldEditorScreen.updateStatusBarLabelText();
                    return;
                }

                GridTableCell toGridTableCell = (GridTableCell) toActor;

                worldEditorScreen.setLastMousePosition(toGridTableCell.getGridCellPosition());
                worldEditorScreen.updateStatusBarLabelText();

                if (pointer == -1)
                    return;

                String selectedTool = worldEditorScreen.getSelectedTool();
                if (selectedTool.equals("Pencil")) {
                    toGridTableCell.setGridCellBlockType(worldEditorScreen.getSelectedBlockType());
                } else if (selectedTool.equals("Eraser")) {
                    toGridTableCell.setGridCellBlockType(BlockType.AIR);
                }
            }
        });
    }
}
