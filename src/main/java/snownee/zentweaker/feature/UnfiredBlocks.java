package snownee.zentweaker.feature;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ZenTweaker;
import snownee.zentweaker.block.BlockUnfired;

@KiwiModule(modid = ZenTweaker.MODID, name = "UnfiredBlocks", optional = true, dependency = "ceramics")
public class UnfiredBlocks implements IModule
{
    public static final BlockUnfired UNFIRED_BLOCK = new BlockUnfired();

    public static ItemStack UNFIRED_CLAY = ItemStack.EMPTY;

    @Override
    public void init()
    {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("ceramics", "unfired_clay"));
        if (item != null)
        {
            UNFIRED_CLAY = new ItemStack(item, 1, 4);
        }
    }
}
