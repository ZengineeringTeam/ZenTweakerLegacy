package snownee.zentweaker.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import snownee.kiwi.item.ItemMod;
import snownee.zentweaker.feature.FireCharger;

public class ItemFireCharger extends ItemMod
{

    public ItemFireCharger(String name)
    {
        super(name);
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        pos = pos.offset(facing);
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos, facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }

        worldIn.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);

        if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }

        if (worldIn.rand.nextFloat() < 0.15F && worldIn.isAirBlock(pos) && worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11))
        {
            if (player instanceof EntityPlayerMP)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemstack);
            }

            if (!player.isCreative())
            {
                itemstack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        else
        {
            if (worldIn.isAirBlock(pos))
            {
                IBlockState state = FireCharger.LIGHT.getDefaultState();
                worldIn.setBlockState(pos, state, 11);
                FireCharger.LIGHT.onBlockAdded(worldIn, pos, state);
            }
            return EnumActionResult.FAIL;
        }
    }

}
