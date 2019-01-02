package snownee.zentweaker.block;

import java.util.Locale;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import snownee.kiwi.block.BlockMod;

public class BlockUnfired extends BlockMod
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);
    private static final AxisAlignedBB BRICK_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.0625D, 0.75D, 0.375D, 0.9375D);

    public BlockUnfired()
    {
        super("unfired", Material.CLAY);
        setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.PORCELAIN));
    }

    @Override
    public int getItemSubtypeAmount()
    {
        return EnumType.VALUES.length;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(TYPE, EnumType.VALUES[Math.min(meta >> 2, getItemSubtypeAmount() - 1)]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
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

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BRICK_AABB;
    }

    public static enum EnumType implements IStringSerializable
    {
        PORCELAIN;

        public static final EnumType[] VALUES = values();

        @Override
        public String getName()
        {
            return toString().toLowerCase(Locale.ENGLISH);
        }
    }
}
