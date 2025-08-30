package com.coffee.corenex.block;

import com.coffee.corenex.Corenex;
import com.coffee.corenex.item.Moditems;
import com.coffee.corenex.item.Moditems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Corenex.MOD_ID);

    public static final DeferredBlock<Block> DRAGON_SCALE_BLOCK =
            registerBlocks("dragon_scale_block", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F)
            .requiresCorrectToolForDrops()));


    private static <T extends Block> void registerBlockItems(String name, DeferredBlock<T> block) {
        Moditems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static  <T extends Block> DeferredBlock<T> registerBlocks(String name, Supplier<T> block) {
        DeferredBlock<T> blocks = BLOCKS.register(name, block);
        registerBlockItems(name, blocks);
        return blocks;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}