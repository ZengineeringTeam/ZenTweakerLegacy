package snownee.zentweaker.features;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.zentweaker.worldgen.ZenMapGenVillage;

public class BetterVillageGen
{
    @SubscribeEvent
    public void onGenerate(InitMapGenEvent event)
    {
        if (event.getType() == InitMapGenEvent.EventType.VILLAGE)
        {
            event.setNewGen(new ZenMapGenVillage());
        }
    }

    @SubscribeEvent
    public void onSpecificBlockState(BiomeEvent.GetVillageBlockID event)
    {

        if (event.getResult() != Result.DEFAULT || event.getOriginal().getBlock() != Blocks.OAK_FENCE
                || !(event.getBiome() instanceof BiomeDesert))
        {
            return;
        }
        Block block = Block.getBlockFromName("quark:sandstone_wall");
        if (block != null)
        {
            event.setReplacement(block.getDefaultState());
            event.setResult(Result.DENY);
        }
    }
}
