package com.javasegfault.shroomite;

import java.util.EnumMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Shroomite extends Game {
	// public static final int BLOCK_WIDTH = 10;
	// public static final int BLOCK_HEIGHT = 10;
	// public static final int GRID_WIDTH = 80;
	// public static final int GRID_HEIGHT = 60;
	public static final int BLOCK_WIDTH = 32;
	public static final int BLOCK_HEIGHT = 32;
	public static final int GRID_WIDTH = 16;
	public static final int GRID_HEIGHT = 16;
	
	public SpriteBatch batch;
	
	public static EnumMap<TextureName, Texture> textures = new EnumMap<TextureName, Texture>(TextureName.class);
	public static EnumMap<TextureName, TextureRegionDrawable> texturesDrawables = new EnumMap<TextureName, TextureRegionDrawable>(TextureName.class);
	public static TextureRegionDrawable defaultTextureDrawable = null;
	
	public Skin skin;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		loadTextures();
		
		skin = new Skin(Gdx.files.internal("libgdxtests/uiskin.json"));

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for (Texture texture : textures.values()) {
			texture.dispose();
		}
		skin.dispose();
	}
	
	public void drawBlockRegion(Texture texture, int gridPosX, int gridPosY) {
		batch.draw(texture, gridPosX*BLOCK_WIDTH, gridPosY*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
	}
	
	private void loadTextures() {
		for (TextureName textureName : TextureName.values()) {
			Texture texture = new Texture(textureName.getAssetName() + ".png");
			textures.put(textureName, texture);
			texturesDrawables.put(textureName, new TextureRegionDrawable(texture));
		}
		defaultTextureDrawable = texturesDrawables.get(TextureName.SKY_4);
	}
}
