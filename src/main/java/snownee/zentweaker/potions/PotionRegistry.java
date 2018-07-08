package snownee.zentweaker.potions;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.zentweaker.ZenTweaker;

@Mod.EventBusSubscriber(modid = ZenTweaker.MODID)
public class PotionRegistry
{

    private PotionRegistry()
    {
        // No instance for you
    }

    public static Potion BRISKNESS;
    public static Potion HEAVINESS;

    @SubscribeEvent
    public static void onPotionRegistry(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().registerAll(
                (BRISKNESS = new ZTPotion(false, -1)
                        .setPotionName("effect.briskness")
                        .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "11AEAA56-376B-4498-935B-2F7F68070635", 0.15D, 2)
                        .setBeneficial()
                        .setRegistryName("briskness")),
                (HEAVINESS = new ZTPotion(true, -1)
                        .setPotionName("effect.heaviness")
                        .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "1107DE5E-7CE8-4030-940E-514C1F160890", -0.15D, 2)
                        .setRegistryName("heaviness"))
        );
    }
}
