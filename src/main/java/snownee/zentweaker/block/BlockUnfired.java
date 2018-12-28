package snownee.zentweaker.block;

import java.util.Locale;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import snownee.kiwi.block.BlockModDirectional;

public class BlockUnfired extends BlockModDirectional
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.<EnumType>create("type", EnumType.class);

    public BlockUnfired()
    {
        super("unfired", Material.CLAY);
        setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.PORCELAIN).withProperty(BlockDirectional.FACING, EnumFacing.NORTH));
    }

    @Override
    public int getItemSubtypeAmount()
    {
        return EnumType.VALUES.length;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return super.getStateFromMeta(meta).withProperty(TYPE, EnumType.VALUES[Math.min(meta >> 2, getItemSubtypeAmount() - 1)]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal() << 2 | super.getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, BlockDirectional.FACING);
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
        PORCELAIN;

        public static final EnumType[] VALUES = values();

        @Override
        public String getName()
        {
            return toString().toLowerCase(Locale.ENGLISH);
        }
    }
}
