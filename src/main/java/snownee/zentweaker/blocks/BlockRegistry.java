package snownee.zentweaker.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ItemStackHolder;
import snownee.zentweaker.blocks.BlockUnfired.EnumType;

@EventBusSubscriber
public class BlockRegistry
{
    @ItemStackHolder(value = "ceramics:unfired_clay", meta = 4)
    public static final ItemStack CLAY = ItemStack.EMPTY;

    private static Block blockUnfired = Blocks.AIR;

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(blockUnfired = new BlockUnfired());
    }

    // TODO: dispenser behaviors
    @SubscribeEvent
    public static void onItemUse(PlayerInteractEvent.RightClickBlock event)
    {
        if (event.isCanceled())
        {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        ItemStack held = player.getHeldItem(event.getHand());
        if (held.isEmpty() || (held.getItem() != Items.CLAY_BALL && !held.isItemEqual(CLAY)))
        {
            return;
        }
        BlockPos pos = event.getPos().offset(event.getFace());
        World world = event.getWorld();
        if (!world.isBlockModifiable(player, pos)
                || !player.canPlayerEdit(pos, player.getAdjustedHorizontalFacing(), held))
        {
            return;
        }
        IBlockState state = blockUnfired.getStateForPlacement(world, pos, player.getAdjustedHorizontalFacing(), 0, 0, 0,
                held.getMetadata(), player, event.getHand());
        for (EnumType type : EnumType.values())
        {
            if (held.isItemEqual(held))
            {
                state = state.withProperty(BlockUnfired.TYPE, type);
                break;
            }
        }
        held.shrink(1);
        player.swingArm(event.getHand());
        world.setBlockState(pos, state);
    }
}
