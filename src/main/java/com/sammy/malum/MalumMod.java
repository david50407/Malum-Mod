package com.sammy.malum;

import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.data.*;
import com.sammy.malum.core.init.MalumContainers;
import com.sammy.malum.core.mod_systems.particle.ParticleRendering;
import net.minecraft.data.BlockTagsProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.sammy.malum.core.init.MalumContainers.CONTAINERS;
import static com.sammy.malum.core.init.MalumEffects.EFFECTS;
import static com.sammy.malum.core.init.MalumEntities.ENTITY_TYPES;
import static com.sammy.malum.core.init.MalumSounds.SOUNDS;
import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.init.block.MalumTileEntities.TILE_ENTITIES;
import static com.sammy.malum.core.init.enchantment.MalumEnchantments.ENCHANTMENTS;
import static com.sammy.malum.core.init.items.MalumItems.ITEMS;
import static com.sammy.malum.core.init.particles.MalumParticles.PARTICLES;
import static com.sammy.malum.core.init.worldgen.MalumFeatures.FEATURES;

@SuppressWarnings("unused")
@Mod(MalumMod.MODID)
public class MalumMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "malum";
    public static final Random RANDOM = new Random();

    public MalumMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

        ENCHANTMENTS.register(modBus);
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TILE_ENTITIES.register(modBus);
        ENTITY_TYPES.register(modBus);
        EFFECTS.register(modBus);
        PARTICLES.register(modBus);
        SOUNDS.register(modBus);
        FEATURES.register(modBus);
        CONTAINERS.register(modBus);
        modBus.addListener(this::gatherData);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () ->
        {
            MinecraftForge.EVENT_BUS.register(new ParticleRendering());
            return new Object();
        });
    }


    public void gatherData(GatherDataEvent event)
    {
        BlockTagsProvider provider = new MalumBlockTags(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(new MalumBlockStates(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumItemModels(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumLang(event.getGenerator()));
        event.getGenerator().addProvider(provider);
        event.getGenerator().addProvider(new MalumBlockLootTables(event.getGenerator()));
        event.getGenerator().addProvider(new MalumItemTags(event.getGenerator(), provider, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new MalumRecipes(event.getGenerator()));
        event.getGenerator().addProvider(new MalumSpiritInfusionRecipes(event.getGenerator()));
    }
}