package snownee.zentweaker.feature;
//package snownee.zentweaker.features;
//
//import blusunrize.immersiveengineering.common.blocks.multiblocks.MultiblockAlloySmelter;
//import blusunrize.immersiveengineering.common.util.advancements.IEAdvancements;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.item.ItemStack;
//import net.minecraft.world.World;
//import net.minecraftforge.common.util.FakePlayer;
//import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class AutoStructureAlloySmelter
//{
//    @SubscribeEvent
//    public void onPlaceBlock(PlaceEvent event)
//    {
//        EntityPlayer player = event.getPlayer();
//        World world = event.getWorld();
//        IBlockState state = world.getBlockState(event.getPos());
//        if (event.isCanceled() || player instanceof FakePlayer || !MultiblockAlloySmelter.instance.isBlockTrigger(event.getState()))
//        {
//            return;
//        }
//        if (MultiblockAlloySmelter.instance.createStructure(world, event.getPos(), player.getAdjustedHorizontalFacing(), player) && player instanceof EntityPlayerMP)
//        {
//            IEAdvancements.TRIGGER_MULTIBLOCK.trigger((EntityPlayerMP) player, MultiblockAlloySmelter.instance, ItemStack.EMPTY);
//        }
//    }
//
//}
