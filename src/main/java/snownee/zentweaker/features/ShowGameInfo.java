package snownee.zentweaker.features;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import snownee.zentweaker.items.ItemRegistry;

@EventBusSubscriber
public class ShowGameInfo
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onItemRightClick(PlayerInteractEvent.RightClickItem event)
    {
        if (event.getSide() == Side.CLIENT && event.getItemStack().getItem() == ItemRegistry.itemDepthMeter)
        {
            EntityPlayer player = event.getEntityPlayer();
            player.sendStatusMessage(new TextComponentTranslation("gui.zentweaker.depth",
                    player.world.getSeaLevel() - (int) player.posY), true);
            event.setCancellationResult(EnumActionResult.SUCCESS);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public static void onDebugScreen(RenderGameOverlayEvent.Text event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (!mc.gameSettings.showDebugInfo || player.isSpectator() || player.isCreative())
        {
            return;
        }

        ArrayList<String> right = event.getRight();
        for (int i = right.size() - 1; i > 2; i--)
        {
            right.remove(i);
        }

        boolean showXZ = false, showY = false, showHeat, showDay;
        for (int i = 0; i <= 10; i++)
        {
            ItemStack stack;
            switch (i) // Baubles?
            {
            case 10:
                stack = player.getHeldItemOffhand();
                break;
            default:
                stack = player.inventory.getStackInSlot(i);
                break;
            }

            if (stack.getItem() == Items.COMPASS)
            {
                showXZ = true;
            }
            else if (stack.getItem() == ItemRegistry.itemDepthMeter)
            {
                showY = true;
            }
        }

        ArrayList<String> left = event.getLeft();
        for (int i = left.size() - 1; i > 1; i--)
        {
            String s = left.get(i);
            if ((showXZ || showY) && s.startsWith("Block:"))
            {
                if (!showXZ && showY)
                {
                    left.set(i, String.format("Block: ? %d ?", (int) player.posY));
                }
                else if (showXZ && !showY)
                {
                    left.set(i, String.format("Block: %d ? %d", (int) player.posX, (int) player.posZ));
                }
                continue;
            }
            else if ((showXZ || showY) && s.startsWith("Chunk:"))
            {
                BlockPos blockpos = player.getPosition();
                if (!showXZ && showY)
                {
                    left.set(i, String.format("Chunk: ? %d ? in ? %d ?", blockpos.getY() & 15, blockpos.getY() >> 4));
                }
                else if (showXZ && !showY)
                {
                    left.set(i, String.format("Chunk: %d ? %d in %d ? %d", blockpos.getX() & 15, blockpos.getZ() & 15,
                            blockpos.getX() >> 4, blockpos.getZ() >> 4));
                }
                continue;
            }
            else if (showXZ && s.startsWith("Facing:"))
            {
                continue;
            }
            left.remove(i);
        }
    }
}
