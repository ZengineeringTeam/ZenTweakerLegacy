package snownee.zentweaker.features;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@EventBusSubscriber
public class NoTreePunching
{

    private static final int ORE_LOG_WOOD = OreDictionary.getOreID("logWood");

    @SubscribeEvent
    public static void onPlayerBreaking(PlayerEvent.BreakSpeed event)
    {
        if (isLog(event.getState()) && !canPunchTree(event.getEntityPlayer()))
        {
            event.setNewSpeed(event.getOriginalSpeed() / 4);
        }
    }

    @SubscribeEvent
    public static void onHarvestCheck(PlayerEvent.HarvestCheck event)
    {
        if (isLog(event.getTargetBlock()))
        {
            event.setCanHarvest(canPunchTree(event.getEntityPlayer()));
        }
    }

    @SubscribeEvent
    public static void onBlockDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (event.getHarvester() == null || !isLog(event.getState()))
        {
            return;
        }
        if (!canPunchTree(event.getHarvester()))
        {
            event.getDrops().clear();
        }
    }

    public static boolean canPunchTree(EntityPlayer player)
    {
        if (player == null)
        {
            return false;
        }
        ItemStack tool = player.getHeldItemMainhand();
        return !tool.isEmpty() && tool.getItem().getToolClasses(tool).contains("axe");
    }

    public static boolean isLog(IBlockState state)
    {
        ItemStack itemblock = new ItemStack(state.getBlock());
        if (itemblock.isEmpty())
        {
            return false;
        }

        for (int id : OreDictionary.getOreIDs(itemblock))
        {
            if (ORE_LOG_WOOD == id)
            {
                return true;
            }
        }
        return false;
    }
}
