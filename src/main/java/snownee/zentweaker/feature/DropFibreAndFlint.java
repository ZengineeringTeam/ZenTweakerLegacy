package snownee.zentweaker.feature;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTallGrass.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.item.ItemMod;
import snownee.zentweaker.ZenTweaker;

@KiwiModule(modid = ZenTweaker.MODID, name = "DropFibreAndFlint", optional = true)
public class DropFibreAndFlint implements IModule
{
    public static final ItemMod PLANT_FIBRE = new ItemMod("plant_fibre");
    public static final ItemMod PLANT_STRING = new ItemMod("plant_string");

    public DropFibreAndFlint()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event)
    {
        List<ItemStack> drops = event.getDrops();
        if (event.isSilkTouching() || drops.isEmpty() || drops instanceof ImmutableList)
        {
            return;
        }
        if (event.getWorld().rand.nextFloat() < 0.3F && event.getState().getBlock().getRegistryName().getPath().equals("dirt") && event.getWorld().rand.nextFloat() < 0.15F)
        {
            drops.clear();
            drops.add(new ItemStack(Items.FLINT));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        if (!event.getEntityPlayer().isSneaking())
        {
            return;
        }
        World world = event.getWorld();
        IBlockState state = world.getBlockState(event.getPos());
        if ((state.getBlock() instanceof BlockTallGrass && state.getValue(BlockTallGrass.TYPE) != EnumType.DEAD_BUSH) || state.getBlock().getRegistryName().toString().equals("biomesoplenty:plant_0"))
        {
            world.destroyBlock(event.getPos(), false);
            Block.spawnAsEntity(world, event.getPos(), new ItemStack(PLANT_FIBRE));
            event.setCanceled(true);
            event.setCancellationResult(EnumActionResult.SUCCESS);
        }
    }
}
