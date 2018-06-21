package snownee.zentweaker.features;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.zentweaker.ZenTweaker;

@Mod.EventBusSubscriber(modid = ZenTweaker.MODID)
public final class KeepMapOnDeath {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            for (ItemStack item : event.getOriginal().inventory.mainInventory) {
                if (!item.isEmpty() && (item.getItem() == Items.FILLED_MAP)) {
                    event.getEntityPlayer().addItemStackToInventory(item);
                }
            }
        }
    }

}
