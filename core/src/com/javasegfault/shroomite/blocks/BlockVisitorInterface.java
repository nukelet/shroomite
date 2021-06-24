package com.javasegfault.shroomite.blocks;

public interface BlockVisitorInterface {
    public void visitRockBlock(RockBlock block);
    public void visitWaterBlock(WaterBlock block);
    public void visitDirtBlock(DirtBlock block);
    public void visitLavaBlock(LavaBlock block);
    public void visitWoodBlock(WoodBlock block);
    public void visitSandBlock(SandBlock block);
}
