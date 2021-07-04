package com.javasegfault.shroomite.blocks;

public interface IBlockVisitor {
    void visitRockBlock(RockBlock block);
    void visitWaterBlock(WaterBlock block);
    void visitDirtBlock(DirtBlock block);
    void visitLavaBlock(LavaBlock block);
    void visitWoodBlock(WoodBlock block);
    void visitSandBlock(SandBlock block);
}
