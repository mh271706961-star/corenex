package com.coffee.corenex.datagen;

import com.coffee.corenex.Corenex;
import com.coffee.corenex.block.ModBlocks;
import com.coffee.corenex.effect.ModEffects;
import com.coffee.corenex.item.Moditems;
import com.coffee.corenex.potions.ModPotions;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, Corenex.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(Moditems.DRAGON_SCALE.get(), "末影龙鳞");
        add(ModBlocks.DRAGON_SCALE_BLOCK.get(), "末影龙鳞块");
        add(Moditems.WITHER_BONE.get(), "凋零之骨");
        add(Moditems.CURSE_GOLD_INGOT.get(), "被诅咒的金锭");
        add(Moditems.CURSE_SOUL.get(), "被诅咒的灵魂");
        add(Moditems.CURSE_STAR.get(), "诅咒之星");
        add(Moditems.DRAGON_INGOT.get(), "龙鳞合金锭");
        add(Moditems.DRAGON_SCALE_HELMET.get(), "末影龙鳞头盔");
        add(Moditems.DRAGON_SCALE_CHESTPLATE.get(), "末影龙鳞胸甲");
        add(Moditems.DRAGON_SCALE_LEGGINGS.get(), "末影龙鳞护腿");
        add(Moditems.DRAGON_SCALE_BOOTS.get(), "末影龙鳞靴子");

        add(ModEffects.CURSE.get(), "诅咒");
        add("item.minecraft.potion.effect.curse_potion", "诅咒药水");
        add("item.minecraft.splash_potion.effect.curse_potion", "喷溅型诅咒药水");
        add("item.minecraft.lingering_potion.effect.curse_potion", "滞留型诅咒药水");
        add("item.minecraft.tipped_arrow.effect.curse_potion", "诅咒之箭");



        add("itemgroup.corenex_tab", "界核");

    }
}
