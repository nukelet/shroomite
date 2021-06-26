package com.javasegfault.shroomite.agents;

import com.javasegfault.shroomite.blocks.BlockVisitorInterface;
import com.javasegfault.shroomite.blocks.DirtBlock;
import com.javasegfault.shroomite.blocks.LavaBlock;
import com.javasegfault.shroomite.blocks.RockBlock;
import com.javasegfault.shroomite.blocks.SandBlock;
import com.javasegfault.shroomite.blocks.WaterBlock;
import com.javasegfault.shroomite.blocks.WoodBlock;

public class PlayerBlockVisitor implements BlockVisitorInterface { 
    public void visitRockBlock(RockBlock block) {

    }

    public void visitWaterBlock(WaterBlock block) {
        // block.destroySelf();
    }

    public void visitDirtBlock(DirtBlock block) {

    }

    public void visitLavaBlock(LavaBlock block) {

    }

    public void visitWoodBlock(WoodBlock block) {

    }

    public void visitSandBlock(SandBlock block) {

    }
}
