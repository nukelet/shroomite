package com.javasegfault.shroomite;

public enum BlockType {
    DIRT(TextureName.DIRT, "DIRT"),
    ROCK(TextureName.ROCK, "ROCK"),
    SAND(TextureName.SAND, "SAND"),
    WOOD(TextureName.WOOD, "WOOD"),
    WATER(TextureName.WATER, "WATER"),
    LAVA(TextureName.LAVA, "LAVA"),
    AIR(TextureName.SKY_4, "AIR");

    private TextureName textureName;
    private String value;

    BlockType(TextureName textureName, String value) {
        this.textureName = textureName;
        this.value = value;
    }

    public TextureName getTextureName() {
        return textureName;
    }

    public String toValue() {
        return value;
    }

    public static BlockType fromValue(String value) {
        if (value != null) {
            for (BlockType blockType : BlockType.values()) {
                if (blockType.toValue().equals(value)) {
                    return blockType;
                }
            }
        }

        return null;
    }
}
