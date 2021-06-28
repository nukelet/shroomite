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

    public void removeGridCellBlockType() {
        gridCell.removeBlockType();
        setDrawable(Shroomite.defaultTextureDrawable);
    }

    public void setGridCellEntityType(String entityType) {
        gridCell.setEntityType(entityType);
        setDrawable(Shroomite.texturesDrawables.get(gridCell.getEntityTypeTextureName()));
    }

    public void removeGridCellEntityType() {
        gridCell.removeEntityType();
        setDrawable(Shroomite.defaultTextureDrawable);
    }

    public void updateDrawable() {
        if (gridCell.hasEntity()) {
            setDrawable(Shroomite.texturesDrawables.get(gridCell.getEntityTypeTextureName()));
        } else if (gridCell.hasBlockType()) {
            setDrawable(Shroomite.texturesDrawables.get(gridCell.getBlockTypeTextureName()));
        } else {
            setDrawable(Shroomite.defaultTextureDrawable);
        }
    }

    private void setDefaultDrawable() {
        setDrawable(Shroomite.defaultTextureDrawable);
    }

    private void setBlockTypeDrawable() {
        setDrawable(Shroomite.texturesDrawables.get(gridCell.getBlockTypeTextureName()));
    }

    private void setEntityTypeDrawable() {
        setDrawable(Shroomite.texturesDrawables.get(gridCell.getEntityTypeTextureName()));
    }

    private void addMainClickListener() {
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String selectedTool = worldEditorScreen.getSelectedTool();
                if (selectedTool.equals("Block")) {
                    gridCell.removeEntityType();
                    gridCell.setBlockType(worldEditorScreen.getSelectedBlockType());
                    setBlockTypeDrawable();
                } else if (selectedTool.equals("Entity")) {
                    gridCell.removeBlockType();
                    gridCell.setEntityType(worldEditorScreen.getSelectedEntity());
                    setEntityTypeDrawable();
                } else if (selectedTool.equals("Eraser")) {
                    gridCell.removeBlockType();
                    gridCell.removeEntityType();
                    setDefaultDrawable();
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
                if (selectedTool.equals("Block")) {
                    toGridTableCell.removeGridCellEntityType();
                    toGridTableCell.setGridCellBlockType(worldEditorScreen.getSelectedBlockType());
                } else if (selectedTool.equals("Entity")) {
                    toGridTableCell.removeGridCellBlockType();
                    toGridTableCell.setGridCellEntityType(worldEditorScreen.getSelectedEntity());
                } else if (selectedTool.equals("Eraser")) {
                    toGridTableCell.removeGridCellBlockType();
                    toGridTableCell.removeGridCellEntityType();
                }
            }
        });
    }
}
