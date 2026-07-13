package com.titammods.quantumcubes.datagen;

import com.titammods.quantumcubes.registry.ModBlocks;
import com.titammods.quantumcubes.registry.ModItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> items = this.registries.lookupOrThrow(Registries.ITEM);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIER_1.get())
                .pattern("COC")
                .pattern("OAO")
                .pattern("COC")
                .define('C', Blocks.COPPER_BLOCK)
                .define('O', Blocks.OBSIDIAN)
                .define('A', Items.AMETHYST_SHARD)
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIER_2.get())
                .pattern("IOI")
                .pattern("OQO")
                .pattern("IOI")
                .define('I', Blocks.IRON_BLOCK)
                .define('O', Blocks.OBSIDIAN)
                .define('Q', ModBlocks.TIER_1.get())
                .unlockedBy("has_tier_1", has(ModBlocks.TIER_1.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIER_3.get())
                .pattern("GOG")
                .pattern("OQO")
                .pattern("GOG")
                .define('G', Blocks.GOLD_BLOCK)
                .define('O', Blocks.OBSIDIAN)
                .define('Q', ModBlocks.TIER_2.get())
                .unlockedBy("has_tier_2", has(ModBlocks.TIER_2.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIER_4.get())
                .pattern("DSD")
                .pattern("CQC")
                .pattern("DPD")
                .define('D', Blocks.DIAMOND_BLOCK)
                .define('S', Blocks.SCULK_SHRIEKER)
                .define('C', Blocks.CRYING_OBSIDIAN)
                .define('P', Items.POISONOUS_POTATO)
                .define('Q', ModBlocks.TIER_3.get())
                .unlockedBy("has_tier_3", has(ModBlocks.TIER_3.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIER_5.get())
                .pattern("BWN")
                .pattern("SQS")
                .pattern("NPH")
                .define('B', Items.DRAGON_BREATH)
                .define('W', Items.WIND_CHARGE)
                .define('N', Blocks.NETHERITE_BLOCK)
                .define('S', Items.NETHER_STAR)
                .define('P', Blocks.POINTED_DRIPSTONE)
                .define('H', Blocks.HEAVY_CORE)
                .define('Q', ModBlocks.TIER_4.get())
                .unlockedBy("has_tier_4", has(ModBlocks.TIER_4.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ModItems.QUANTUM_REMOTE.get())
                .pattern(" IA")
                .pattern("IRI")
                .pattern("II ")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('A', Items.AMETHYST_SHARD)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(this.output);
    }

    public static final class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "QuantumCubes Recipes";
        }
    }
}