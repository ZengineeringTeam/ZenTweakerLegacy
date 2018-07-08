package snownee.zentweaker.features;

import java.util.ListIterator;

import javax.annotation.Nonnull;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.zentweaker.ZenTweaker;

@Mod.EventBusSubscriber(modid = ZenTweaker.MODID)
public final class KeepMapOnDeath
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerDeath(PlayerEvent.Clone event)
    {
        if (!event.isWasDeath() || event.getOriginal() == null || event.getEntityPlayer() == null
                || event.getEntityPlayer() instanceof FakePlayer
                || event.getOriginal().world.getGameRules().getBoolean("keepInventory"))
        {
            return;
        }
        for (int i = 0; i < event.getOriginal().inventory.mainInventory.size(); i++)
        {
            ItemStack item = event.getOriginal().inventory.mainInventory.get(i);
            if (!item.isEmpty() && isMap(item))
            {
                event.getEntityPlayer().inventory.addItemStackToInventory(item);
                event.getOriginal().inventory.mainInventory.set(i, ItemStack.EMPTY);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void itemDrop(PlayerDropsEvent event)
    {
        if (event.getEntityPlayer() == null || event.getEntityPlayer() instanceof FakePlayer
                || event.getEntityPlayer().world.getGameRules().getBoolean("keepInventory"))
        {
            return;
        }
        ListIterator<EntityItem> iter = event.getDrops().listIterator();
        while (iter.hasNext())
        {
            EntityItem e = iter.next();
            if (!e.getItem().isEmpty() && isMap(e.getItem()))
            {
                event.getEntityPlayer().inventory.addItemStackToInventory(e.getItem());
                iter.remove();
            }
        }
    }

    private static boolean isMap(@Nonnull ItemStack stack)
    {
        return stack.getItem().isMap() || stack.getItem().getRegistryName().getResourceDomain().equals("antiqueatlas");
    }

}
