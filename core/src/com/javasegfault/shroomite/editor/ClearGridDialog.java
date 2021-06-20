package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ClearGridDialog extends Dialog {
    private WorldEditorScreen editor;

    public ClearGridDialog(Skin skin, WorldEditorScreen worldEditorScreen) {
        super("Clear grid", skin);
        this.editor = worldEditorScreen;

        Label messageLabel = new Label("Clear Grid permanently clears the current grid, discarding any changes made. Are you sure you want  to permanently clear it?", skin);
        messageLabel.setWidth(300);
        messageLabel.setWrap(true);
        row();
        add(messageLabel).width(300);

        // Botões para cancelar ou limpar grid
        final TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        final TextButton clearGridButton = new TextButton("Clear Grid", skin);
        clearGridButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ClearGridDialog.this.editor.clearGrid();
                hide();
            }
        });
        final HorizontalGroup buttonsGroup = new HorizontalGroup();
        buttonsGroup.addActor(cancelButton);
        buttonsGroup.addActor(clearGridButton);
        buttonsGroup.right().space(10);
        row();
        add(buttonsGroup).width(300).growX();


    }
}
