package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SaveGridToFileDialog extends Dialog {
    private WorldEditorScreen editor;

    public SaveGridToFileDialog(Skin skin, WorldEditorScreen worldEditorScreen) {
        super("Save grid", skin);
        this.editor = worldEditorScreen;

        final Label fileNameLabel = new Label("File name: ", skin);
        String fileName = editor.getReferencedFileName().substring(editor.getReferencedFileName().lastIndexOf("/") + 1);
        final TextField fileNameTextField = new TextField(fileName, skin);
        Table fileNameTable = new Table(skin);
        fileNameTable.add(fileNameLabel);
        fileNameTable.add(fileNameTextField).growX();
        row();
        add(fileNameTable).width(300).growX();

        // Botões para cancelar ou salvar grid
        final TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        final TextButton saveGridToFileButton = new TextButton("Save grid to file", skin);
        saveGridToFileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String fileName = "worlds/" + fileNameTextField.getText();
                SaveGridToFileDialog.this.editor.saveGrid(fileName);
                hide();
            }
        });
        final HorizontalGroup buttonsGroup = new HorizontalGroup();
        buttonsGroup.addActor(cancelButton);
        buttonsGroup.addActor(saveGridToFileButton);
        buttonsGroup.right().space(10);
        row();
        add(buttonsGroup).width(300).growX();
    }
}
