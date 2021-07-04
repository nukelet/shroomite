package com.javasegfault.shroomite.entities;

import com.javasegfault.shroomite.blocks.IBlockVisitor;
import com.javasegfault.shroomite.blocks.DirtBlock;
import com.javasegfault.shroomite.blocks.LavaBlock;
import com.javasegfault.shroomite.blocks.RockBlock;
import com.javasegfault.shroomite.blocks.SandBlock;
import com.javasegfault.shroomite.blocks.WaterBlock;
import com.javasegfault.shroomite.blocks.WoodBlock;

public class PlayerBlockVisitor implements IBlockVisitor { 
    PlayerAgent player;

    public PlayerBlockVisitor(PlayerAgent player) {
        this.player = player;
    }

    public void visitRockBlock(RockBlock block) {

    }

    public void visitWaterBlock(WaterBlock block) {
        player.removeStatusEffects(StatusEffect.FIRE);
    }

    public void visitDirtBlock(DirtBlock block) {

    }

    public void visitLavaBlock(LavaBlock block) {
        player.addStatusEffect(StatusEffect.FIRE);
    }

    public void visitWoodBlock(WoodBlock block) {

    }

    public void visitSandBlock(SandBlock block) {

    }
}
