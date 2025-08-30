package com.coffee.corenex.datagen;

import com.coffee.corenex.Corenex;
import com.coffee.corenex.item.Moditems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Corenex.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(Moditems.DRAGON_SCALE.get());
        basicItem(Moditems.WITHER_BONE.get());
        basicItem(Moditems.CURSE_GOLD_INGOT.get());
        basicItem(Moditems.DRAGON_SCALE_HELMET.get());
        basicItem(Moditems.DRAGON_SCALE_CHESTPLATE.get());
        basicItem(Moditems.DRAGON_SCALE_LEGGINGS.get());
        basicItem(Moditems.DRAGON_SCALE_BOOTS.get());



//        basicItem(ModItems.CARDBOARD.get());
    }
}