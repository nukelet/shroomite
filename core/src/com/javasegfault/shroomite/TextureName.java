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
    WATER_FULL("water_full"),
    WATER_THREE_QUARTERS("water_three_quarters"),
    WATER_HALF("water_half"),
    WATER_ONE_QUARTER("water_one_quarter"),
    WATER_CRITICAL("water_critical"),
    SAND("sand"),
    LAVA("lava"),
    DIRT("dirt"),
    ROCK("rock"),
    WOOD("wood"),
    SKY_WITH_CLOUDS("sky-with-clouds"),
    ADVENTURER_IDLE("adventurer-idle-00"),
    LEVEL_EXIT("level_exit"),
    LEVER("lever");

    private String assetName;

    TextureName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetName() {
        return assetName;
    }
}
