package com.javasegfault.shroomite;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class CompletedWorldDialog extends Dialog {
    private GameScreen gameScreen;

    public CompletedWorldDialog(Skin skin, GameScreen gameScreen) {
        super("Congratulations!", skin);

        this.gameScreen = gameScreen;

        final Label messageLabel = new Label("You completed " + gameScreen.getWorldNameWithoutFileExtension() + "!", skin);
        row();
        add(messageLabel).width(300).growX();

        final Label hpLeftLabel = new Label("HP left: " + gameScreen.getPlayerHp(), skin);
        row();
        add(hpLeftLabel).width(300).growX();

        final TextButton backToMainMenuButton = new TextButton("Back to main menu", skin);
        backToMainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CompletedWorldDialog.this.gameScreen.goToMainMenu();
                hide();
            }
        });
        row();
        add(backToMainMenuButton).right();
    }
}
