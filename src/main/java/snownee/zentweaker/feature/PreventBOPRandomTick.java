package snownee.zentweaker.feature;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ZenTweaker;

@KiwiModule(modid = ZenTweaker.MODID, name = "PreventBOPRandomTick", optional = true, dependency = "biomesoplenty")
public class PreventBOPRandomTick implements IModule
{
    @Override
    public void postInit()
    {
        disableTick(new ResourceLocation("biomesoplenty", "plant_0"));
        disableTick(new ResourceLocation("biomesoplenty", "plant_1"));
        disableTick(new ResourceLocation("biomesoplenty", "flower_0"));
        disableTick(new ResourceLocation("biomesoplenty", "flower_1"));
        disableTick(new ResourceLocation("biomesoplenty", "coral"));
        disableTick(new ResourceLocation("biomesoplenty", "seaweed"));
        disableTick(new ResourceLocation("biomesoplenty", "mushroom"));
    }

    private static void disableTick(ResourceLocation name)
    {
        Block block = ForgeRegistries.BLOCKS.getValue(name);
        if (block != null)
        {
            block.setTickRandomly(false);
        }
    }
}
