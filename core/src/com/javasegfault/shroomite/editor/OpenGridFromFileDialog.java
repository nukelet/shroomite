package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

public class OpenGridFromFileDialog extends Dialog {
    private WorldEditorScreen editor;

    public OpenGridFromFileDialog(Skin skin, WorldEditorScreen worldEditorScreen) {
        super("Open grid", skin);
        this.editor = worldEditorScreen;

        FileHandle worldsDir = Gdx.files.internal("worlds");
        FileHandle worlds[] = worldsDir.list();
        Array<String> worldsNames = new Array<String>();
        for (FileHandle world : worlds) {
            worldsNames.add(world.name());
        }

        final List<String> gridFileNames = new List<String>(skin);
        gridFileNames.setItems(worldsNames);
        final ScrollPane gridFileNamesScrollPane = new ScrollPane(gridFileNames, skin);
        gridFileNamesScrollPane.setFadeScrollBars(false);
        row();
        add(gridFileNamesScrollPane).width(300).height(200);

        // Botões para cancelar ou abrir grid
        final TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        final TextButton openGridFromFileButton = new TextButton("Open grid from file", skin);
        openGridFromFileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                OpenGridFromFileDialog.this.editor.loadGrid("worlds/" + gridFileNames.getSelected());
                hide();
            }
        });
        final HorizontalGroup buttonsGroup = new HorizontalGroup();
        buttonsGroup.addActor(cancelButton);
        buttonsGroup.addActor(openGridFromFileButton);
        buttonsGroup.right().space(10);
        row();
        add(buttonsGroup).width(300).growX();
    }
}
