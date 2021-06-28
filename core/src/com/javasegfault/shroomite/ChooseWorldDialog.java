package com.javasegfault.shroomite;

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

public class ChooseWorldDialog extends Dialog {
    private MainMenuScreen mainMenuScreen;

    public ChooseWorldDialog(Skin skin, MainMenuScreen mainMenuScreen) {
        super("Choose world", skin);

        this.mainMenuScreen = mainMenuScreen;

        String directory = System.getProperty("user.dir");
        FileHandle worldsDir = Gdx.files.absolute(directory+"/worlds");
        System.out.println(worldsDir.path());

        FileHandle worlds[] = worldsDir.list();
        Array<String> worldsNames = new Array<String>();
        for (FileHandle world : worlds) {
            worldsNames.add(world.name().substring(0, world.name().lastIndexOf(".")));
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
        final TextButton openGridFromFileButton = new TextButton("Play", skin);
        openGridFromFileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ChooseWorldDialog.this.mainMenuScreen.startGame(gridFileNames.getSelected() + ".grid");
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
