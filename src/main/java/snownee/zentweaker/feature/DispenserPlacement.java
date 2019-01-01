package snownee.zentweaker.feature;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ZenTweaker;

@KiwiModule(modid = ZenTweaker.MODID, name = "DispenserPlacement", optional = true)
public class DispenserPlacement implements IModule
{

    @Override
    public void postInit()
    {
        for (ResourceLocation r : Block.REGISTRY.getKeys())
        {
            Block block = Block.REGISTRY.getObject(r);

            if (block instanceof IFluidBlock)
                continue;

            Item item = Item.getItemFromBlock(block);

            if (block == null || item == null || !(item instanceof ItemBlock))
                continue;

            if (BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.containsKey(item))
                continue;

            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, BEHAVIOR);
        }
    }

    public static final BehaviorBlock BEHAVIOR = new BehaviorBlock();

    private static class BehaviorBlock implements IBehaviorDispenseItem
    {
        @Override
        public ItemStack dispense(IBlockSource source, ItemStack stack)
        {
            EnumFacing facing = source.getBlockState().getValue(BlockDispenser.FACING);
            System.out.println(facing);
            Block block = Block.getBlockFromItem(stack.getItem());

            BlockPos pos = source.getBlockPos().offset(facing);
            World world = source.getWorld();

            Material mat = world.getBlockState(pos).getMaterial();

            if ((mat == Material.AIR || mat == Material.FIRE || mat == Material.WATER || mat == Material.LAVA) && block.canPlaceBlockAt(world, pos))
            {
                int meta = stack.getMetadata();
                IBlockState state;
                if (!(block instanceof BlockPistonBase))
                    state = block.getStateFromMeta(meta);
                else
                    state = block.getDefaultState();

                if (setBlockRotated(world, state, pos, facing))
                {
                    SoundType soundtype = block.getSoundType();
                    world.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    stack.shrink(1);
                }
            }

            return stack;
        }

        private boolean setBlockRotated(World world, IBlockState state, BlockPos pos, EnumFacing face)
        {
            IBlockState setState = state;
            ImmutableMap<IProperty<?>, Comparable<?>> props = state.getProperties();
            Block block = state.getBlock();

            // General Facing
            if (props.containsKey(BlockDirectional.FACING))
                setState = state.withProperty(BlockDirectional.FACING, face);

            // Horizontal Facing
            else if (props.containsKey(BlockHorizontal.FACING) && face.getAxis() != Axis.Y)
            {
                if (block instanceof BlockStairs)
                    setState = state.withProperty(BlockHorizontal.FACING, face.getOpposite());
                else
                    setState = state.withProperty(BlockHorizontal.FACING, face);
            }

            // Pillar Axis
            else if (props.containsKey(BlockRotatedPillar.AXIS))
                setState = state.withProperty(BlockRotatedPillar.AXIS, face.getAxis());

            // Log Axis
            else if (props.containsKey(BlockLog.LOG_AXIS))
                setState = state.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(face.getAxis()));

            else if (props.containsKey(BlockLever.FACING))
            {
                for (BlockLever.EnumOrientation orientation : BlockLever.EnumOrientation.values())
                {
                    if (face == orientation.getFacing())
                    {
                        setState = state.withProperty(BlockLever.FACING, orientation);
                    }
                }
            }

            // Quartz Variant/Axis
            else if (props.containsKey(BlockQuartz.VARIANT))
            {
                BlockQuartz.EnumType type = state.getValue(BlockQuartz.VARIANT);
                if (ImmutableSet.of(BlockQuartz.EnumType.LINES_X, BlockQuartz.EnumType.LINES_Y, BlockQuartz.EnumType.LINES_Z).contains(type))
                    setState = state.withProperty(BlockQuartz.VARIANT, BlockQuartz.VARIANT.parseValue("lines_" + face.getAxis().getName()).or(BlockQuartz.EnumType.LINES_Y));
            }

            // Hopper Facing
            else if (props.containsKey(BlockHopper.FACING))
                setState = state.withProperty(BlockHopper.FACING, face == EnumFacing.UP ? face.getOpposite() : face);

            //            if (props.containsKey(BlockStairs.HALF))
            //                setState = setState.withProperty(BlockStairs.HALF, half == 1 ? BlockStairs.EnumHalf.TOP : BlockStairs.EnumHalf.BOTTOM);
            //            else if (props.containsKey(BlockSlab.HALF))
            //                setState = setState.withProperty(BlockSlab.HALF, half == 1 ? BlockSlab.EnumBlockHalf.TOP : BlockSlab.EnumBlockHalf.BOTTOM);

            else
            {
                for (IProperty<?> prop : state.getProperties().keySet())
                {
                    if ((prop.getName().equals("facing") || prop.getName().equals("rotation")) && prop.getValueClass() == EnumFacing.class)
                    {
                        if (!(block instanceof BlockBed) && !(block instanceof BlockPistonExtension))
                        {
                            //noinspection unchecked
                            IProperty<EnumFacing> facingProperty = (IProperty<EnumFacing>) prop;
                            java.util.Collection<EnumFacing> validFacings = facingProperty.getAllowedValues();

                            if (validFacings.contains(face))
                            {
                                setState = state.withProperty(facingProperty, face);
                            }
                        }
                    }
                }
            }

            return world.setBlockState(pos, setState);
        }

    }
}
