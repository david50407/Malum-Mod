package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.tile.SpiritAltarTileEntity;
import com.sammy.malum.core.mod_systems.tile.SimpleTileEntityInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class SpiritAltarRenderer extends TileEntityRenderer<SpiritAltarTileEntity>
{
    public SpiritAltarRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
    }
    
    @Override
    public void render(SpiritAltarTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        World world = Minecraft.getInstance().world;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        SimpleTileEntityInventory inventory = tileEntityIn.spiritInventory;
        for (int i = 0; i < inventory.slotCount; i++)
        {
            ItemStack item = inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                matrixStackIn.push();
                Vector3f offset = new Vector3f(SpiritAltarTileEntity.itemOffset(tileEntityIn, i));
                matrixStackIn.translate(offset.getX(), offset.getY(), offset.getZ());
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees((world.getGameTime() % 360)* 3 + partialTicks));
                matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty())
        {
            matrixStackIn.push();
            Vector3d offset = tileEntityIn.itemOffset();
            matrixStackIn.translate(offset.x, offset.y,offset.z);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((world.getGameTime() % 360)* 3 + partialTicks));
            matrixStackIn.scale(0.4f, 0.4f, 0.4f);
            itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }
}