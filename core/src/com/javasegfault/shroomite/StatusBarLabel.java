package com.javasegfault.shroomite;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StatusBarLabel extends Label {
    private List<String> toolsList;
    private List<BlockType> blockTypeList;
    private int gridWidth;
    private int gridHeight;
    private int mouseX;
    private int mouseY;

    public StatusBarLabel(Skin skin, List<String> toolsList, List<BlockType> blockTypeList, int gridWidth,
            int gridHeight, int mouseX, int mouseY) {
        super(null, skin);
        this.toolsList = toolsList;
        this.blockTypeList = blockTypeList;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void setGridDimensions(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void updateText() {
        setText(String.format("Selected tool: %s | Selected block type: %s | Mouse: %d, %d | Grid: %d x %d",
                toolsList.getSelected(), blockTypeList.getSelected(), mouseX, mouseY, gridWidth, gridHeight));
    }
}
