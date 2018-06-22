package snownee.zentweaker.features;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import snownee.zentweaker.ZenTweaker;

@GameRegistry.ObjectHolder(ZenTweaker.MODID)
@Mod.EventBusSubscriber(modid = ZenTweaker.MODID)
public final class KeepMapOnDeath
{

    @GameRegistry.ObjectHolder("antiqueatlas:antique_atlas")
    public static final Item ANTIQUE_ATLAS = Items.AIR;

    @GameRegistry.ObjectHolder("antiqueatlas:empty_antique_atlas")
    public static final Item EMPTY_ATLAS = Items.AIR;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerDeath(PlayerEvent.Clone event)
    {
        if (event.isWasDeath() && !event.getOriginal().world.getGameRules().getBoolean("keepInventory"))
        {
            for (ItemStack item : event.getOriginal().inventory.mainInventory)
            {
                if (!item.isEmpty() && isMap(item))
                {
                    event.getEntityPlayer().addItemStackToInventory(item);
                }
            }
            ItemStack heldInOffHand = event.getOriginal().inventory.offHandInventory.get(0);
            if (!heldInOffHand.isEmpty() && isMap(heldInOffHand))
            {
                event.getEntityPlayer().setHeldItem(EnumHand.OFF_HAND, heldInOffHand);
            }
        }
    }

    private static boolean isMap(ItemStack stack)
    {
        return stack.getItem().isMap() || stack.getItem() == ANTIQUE_ATLAS || stack.getItem() == EMPTY_ATLAS;
    }

}
