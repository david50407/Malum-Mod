package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.item.SpiritItem;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.item.ether.EtherItem;
import com.sammy.malum.common.item.tools.ModCombatItem;
import com.sammy.malum.common.item.tools.spirittools.ScytheItem;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.MalumHelper.takeAll;
import static com.sammy.malum.core.init.items.MalumItems.*;

public class MalumItemModels extends net.minecraftforge.client.model.generators.ItemModelProvider
{
    public MalumItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, MalumMod.MODID, existingFileHelper);
    }
    
    @Override
    protected void registerModels()
    {
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());

        takeAll(items, i -> i.get() instanceof ScytheItem);
        takeAll(items, i -> i.get() instanceof SpiritItem).forEach(this::spiritSplinterItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof WallBlock).forEach(this::wallBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof FenceBlock).forEach(this::fenceBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof DoorBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof TrapDoorBlock).forEach(this::trapdoorBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof PressurePlateBlock).forEach(this::pressurePlateBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof AbstractButtonBlock).forEach(this::buttonBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof BushBlock && !(((BlockItem) i.get()).getBlock() instanceof DoublePlantBlock)).forEach(this::blockGeneratedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof DoublePlantBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof EtherBrazierBlock).forEach(this::etherBrazierItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof LanternBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof EtherTorchBlock).forEach(this::etherTorchItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof TorchBlock).forEach(this::generatedItem);
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof EtherBlock).forEach(this::etherItem);
        takeAll(items, i -> i.get() instanceof SignItem).forEach(this::generatedItem);

        takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::blockItem);
        takeAll(items, i -> i.get() instanceof ToolItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof ModCombatItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof SwordItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof BowItem).forEach(this::handheldItem);
        items.forEach(this::generatedItem);
    }
    
    private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");
    
    private void handheldItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, HANDHELD).texture("layer0", prefix("item/" + name));
    }
    private void spiritSplinterItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("spirit/spirit_splinter_base"));
    }
    private void etherBrazierItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String textureName = name.substring(0, 8) + "ether_brazier";
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent)
        {
            withExistingParent(name, GENERATED).texture("layer0", prefix("item/iridescent_ether_brazier")).texture("layer1", prefix("item/" + textureName)).texture("layer2", prefix("item/iridescent_ether_brazier_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", prefix("item/ether_brazier_overlay")).texture("layer1", prefix("item/" + textureName));
    }
    private void etherTorchItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent)
        {
            withExistingParent(name, GENERATED).texture("layer0", prefix("item/iridescent_ether_torch")).texture("layer1", prefix("item/ether_torch")).texture("layer2", prefix("item/iridescent_ether_torch_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", prefix("item/ether_torch_overlay")).texture("layer1", prefix("item/ether_torch"));
    }
    private void etherItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent)
        {
            withExistingParent(name, GENERATED).texture("layer0", prefix("item/iridescent_ether")).texture("layer1", prefix("item/iridescent_ether_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", prefix("item/ether"));
    }
    private void generatedItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("item/" + name));
    }
    private void blockGeneratedItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("block/" + name));
    }
    private void blockItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name)));
    }
    private void trapdoorBlockItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name + "_bottom")));
    }
    private void fenceBlockItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceInventory(name, prefix("block/" + baseName));
    }
    private void wallBlockItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallInventory(name, prefix("block/" + baseName));
    }
    private void pressurePlateBlockItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name + "_up")));
    }
    private void buttonBlockItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name + "_inventory")));
    }
    @Override
    public String getName()
    {
        return "Malum Item Models";
    }
}