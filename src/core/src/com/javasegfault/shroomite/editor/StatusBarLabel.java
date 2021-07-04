package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StatusBarLabel extends Label {
    private WorldEditorScreen worldEditorScreen;

    public StatusBarLabel(Skin skin, WorldEditorScreen worldEditorScreen) {
        super(null, skin);
        this.worldEditorScreen = worldEditorScreen;
    }

    public void updateText() {
        setText(String.format("Selected tool: %s | Selected block type: %s | Mouse: %d, %d | Grid: %d x %d",
                worldEditorScreen.getSelectedTool(), worldEditorScreen.getSelectedBlockType(),
                worldEditorScreen.getLastMousePosition().getX(), worldEditorScreen.getLastMousePosition().getY(),
                worldEditorScreen.getGridWidth(), worldEditorScreen.getGridHeight()));
    }
}
