package snownee.zentweaker.blocks;

import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import snownee.zentweaker.ZenTweaker;

public class BlockUnfired extends Block
{
    public static final PropertyBool FACING = PropertyBool.create("facing");
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.<EnumType>create("type", EnumType.class);

    public BlockUnfired()
    {
        super(Material.CLAY);
        setRegistryName(ZenTweaker.MODID, "unfired");
        setUnlocalizedName(ZenTweaker.MODID + ".unfired");
        setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.CLAY).withProperty(FACING, false));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return state.getValue(TYPE).getItem();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(state.getValue(TYPE).getItem());
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, meta > 8).withProperty(TYPE, EnumType.values()[meta % 8]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal() | (state.getValue(FACING) ? 8 : 0);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, FACING);
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public static enum EnumType implements IStringSerializable
    {
        CLAY(new ItemStack(Items.CLAY_BALL)), PORCELAIN(BlockRegistry.CLAY);

        private final ItemStack item;

        private EnumType(ItemStack item)
        {
            this.item = item == null ? ItemStack.EMPTY : item;
        }

        @Override
        public String getName()
        {
            return toString().toLowerCase(Locale.ENGLISH);
        }

        public ItemStack getItem()
        {
            return item;
        }
    }
}
