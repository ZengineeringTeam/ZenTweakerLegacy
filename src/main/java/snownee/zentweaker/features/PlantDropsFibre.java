package snownee.zentweaker.features;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTallGrass.EnumType;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.zentweaker.items.ItemRegistry;

@EventBusSubscriber
public class PlantDropsFibre
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onBlockDrops(BlockEvent.HarvestDropsEvent event)
    {
        List<ItemStack> drops = event.getDrops();
        if (event.isSilkTouching() || !drops.isEmpty() || drops instanceof ImmutableList)
        {
            return;
        }
        IBlockState state = event.getState();
        if ((state.getBlock() instanceof BlockTallGrass && state.getValue(BlockTallGrass.TYPE) != EnumType.DEAD_BUSH)
                || state.getBlock() == Blocks.DOUBLE_PLANT || state.getBlock() instanceof BlockVine
                || state.getBlock().getRegistryName().toString().startsWith("biomesoplenty:plant_"))
        {
            if (event.getWorld().rand.nextFloat() < 0.4F)
            {
                drops.add(new ItemStack(ItemRegistry.itemPlantFibre));
            }
        }
    }
}
