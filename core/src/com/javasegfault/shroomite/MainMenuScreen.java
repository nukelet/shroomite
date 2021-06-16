package com.javasegfault.shroomite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen extends ScreenAdapter {
    private final Shroomite game;
    private OrthographicCamera camera;
    private Stage stage;

    public MainMenuScreen(final Shroomite game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        final TextButton playGameButton = new TextButton("Play", game.skin);
        playGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Fechar esta janela e iniciar o jogo
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });
        rootTable.add(playGameButton).width(200).space(10);

        final TextButton worldEditorButton = new TextButton("World Editor", game.skin);
        worldEditorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Fechar esta janela e abrir a do editor de mundos
                dispose();
                game.setScreen(new WorldEditorScreen(game));
            }
        });
        rootTable.row();
        rootTable.add(worldEditorButton).width(200).space(10);

        final TextButton quitGameButton = new TextButton("Quit", game.skin);
        quitGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose(); // É necessário?
                Gdx.app.exit();
            }
        });
        rootTable.row();
        rootTable.add(quitGameButton).width(200).space(10);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 0);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
