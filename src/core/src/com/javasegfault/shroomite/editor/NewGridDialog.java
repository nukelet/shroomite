package com.javasegfault.shroomite.editor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class NewGridDialog extends Dialog {
    private WorldEditorScreen worldEditorScreen;

    public NewGridDialog(Skin skin, WorldEditorScreen worldEditorScreen) {
        super("New grid", skin);

        this.worldEditorScreen = worldEditorScreen;

        TextFieldFilter digisOnlyFilter = new TextField.TextFieldFilter.DigitsOnlyFilter();

        // Campo largura da nova grid
        final Label widthLabel = new Label("Width: ", skin);
        final TextField widthTextField = new TextField("16", skin);
        widthTextField.setTextFieldFilter(digisOnlyFilter);
        Table widthTable = new Table(skin);
        widthTable.add(widthLabel);
        widthTable.add(widthTextField).growX();
        row();
        add(widthTable).width(300).growX();

        // Campo altura da nova grid
        final Label heightLabel = new Label("Height: ", skin);
        final TextField heightTextField = new TextField("16", skin);
        heightTextField.setTextFieldFilter(digisOnlyFilter);
        Table heightTable = new Table(skin);
        heightTable.add(heightLabel);
        heightTable.add(heightTextField).growX();
        row();
        add(heightTable).width(300).growX();

        // Botões para cancelar ou criar nova grid
        final TextButton cancelNewGridButton = new TextButton("Cancel", skin);
        cancelNewGridButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        final TextButton createNewGridButton = new TextButton("Create new grid", skin);
        createNewGridButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int newGridWidth = Integer.parseInt(widthTextField.getText());
                int newGridHeight = Integer.parseInt(heightTextField.getText());
                NewGridDialog.this.worldEditorScreen.createNewGrid(newGridWidth, newGridHeight);
                hide();
            }
        });
        final HorizontalGroup buttonsGroup = new HorizontalGroup();
        buttonsGroup.addActor(cancelNewGridButton);
        buttonsGroup.addActor(createNewGridButton);
        buttonsGroup.right().space(10);
        row();
        add(buttonsGroup).width(300).growX();
    }
}
