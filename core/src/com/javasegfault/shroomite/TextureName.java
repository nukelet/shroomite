package com.javasegfault.shroomite;

public enum TextureName {
    SKY_1("sky_1"),
    SKY_2("sky_2"),
    SKY_3("sky_3"),
    SKY_4("sky_4"),
    SUN("sun"),
    CLOUDS("clouds"),
    FIRE("fire"),
    WATER("water"),
    SAND("sand"),
    LAVA("lava"),
    DIRT("dirt"),
    ROCK("rock"),
    WOOD("wood"),
    SKY_WITH_CLOUDS("sky-with-clouds");

    private String assetName;

    TextureName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetName() {
        return assetName;
    }
}
