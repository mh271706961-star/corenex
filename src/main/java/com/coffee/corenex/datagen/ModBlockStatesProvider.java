package com.coffee.corenex.datagen;

import com.coffee.corenex.Corenex;
import com.coffee.corenex.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Corenex.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.DRAGON_SCALE_BLOCK.get(),cubeAll(ModBlocks.DRAGON_SCALE_BLOCK.get()));

    }
}
