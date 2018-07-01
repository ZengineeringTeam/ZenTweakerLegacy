package snownee.zentweaker.features;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.zentweaker.network.PlayerDrinkPacket;
import snownee.zentweaker.network.ZTNetworkChannel;
import toughasnails.api.TANPotions;
import toughasnails.api.config.GameplayOption;
import toughasnails.api.config.SyncedConfig;
import toughasnails.api.stat.capability.IThirst;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.api.thirst.WaterType;

public class EmptyHandToDrink
{
    @SubscribeEvent
    public void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (event.isCanceled() || event.getHand() != EnumHand.MAIN_HAND || player == null
                || player instanceof FakePlayer || player.isSneaking() || !player.onGround || player.isOnLadder()
                || !player.getHeldItemMainhand().isEmpty() || !player.getHeldItemOffhand().isEmpty())
        {
            return;
        }
        if (isFacingToWater(event.getWorld(), player))
        {
            ZTNetworkChannel.INSTANCE.sendToServer(new PlayerDrinkPacket());
            event.setCancellationResult(EnumActionResult.SUCCESS);
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (event.isCanceled() || event.getHand() != EnumHand.MAIN_HAND || player == null
                || player instanceof FakePlayer || player.isSneaking() || !player.onGround || player.isOnLadder()
                || !player.getHeldItemMainhand().isEmpty() || !player.getHeldItemOffhand().isEmpty())
        {
            return;
        }

        World world = event.getWorld();
        if (isFacingToWater(world, player))
        {
            doDrink(world, player);
            event.setCanceled(true);
            event.setCancellationResult(EnumActionResult.SUCCESS);
        }
    }

    public static boolean isFacingToWater(World world, EntityPlayer player)
    {
        // From Item#rayTrace
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        double d0 = player.posX;
        double d1 = player.posY + player.getEyeHeight();
        double d2 = player.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() * 0.5D;
        Vec3d vec3d1 = vec3d.addVector(f6 * d3, f5 * d3, f7 * d3);
        RayTraceResult rayTraceResult = world.rayTraceBlocks(vec3d, vec3d1, true);

        if (rayTraceResult == null || rayTraceResult.typeOfHit != Type.BLOCK)
        {
            return false;
        }
        Block block = world.getBlockState(rayTraceResult.getBlockPos()).getBlock();
        return block == Blocks.WATER || block == Blocks.FLOWING_WATER;
    }

    public static void doDrink(World world, EntityPlayer player)
    {
        IThirst iThirst = ThirstHelper.getThirstData(player);
        if (iThirst.getThirst() >= 20)
        {
            return;
        }
        world.playSound(null, player.posX, player.posY
                + player.getEyeHeight(), player.posZ, SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.NEUTRAL, 0.5F, world.rand.nextFloat()
                        * 0.1F + 0.9F);
        iThirst.addStats(WaterType.NORMAL.getThirst(), WaterType.NORMAL.getHydration());
        if (!world.isRemote && world.rand.nextFloat() < WaterType.NORMAL.getPoisonChance()
                && SyncedConfig.getBooleanValue(GameplayOption.ENABLE_THIRST))
        {
            player.addPotionEffect(new PotionEffect(TANPotions.thirst, 600));
        }
    }
}
