package com.javasegfault.shroomite;

public enum BlockType {
    DIRT(TextureName.DIRT),
    ROCK(TextureName.ROCK),
    SAND(TextureName.SAND),
    WOOD(TextureName.WOOD),
    WATER(TextureName.WATER),
    LAVA(TextureName.LAVA);

    private TextureName textureName;

    BlockType(TextureName textureName) {
        this.textureName = textureName;
    }

    TextureName getTextureName() {
        return textureName;
    }
}
