package snownee.zentweaker.features;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import snownee.zentweaker.ZenTweaker;
import toughasnails.potion.PotionThirst;

@GameRegistry.ObjectHolder(ZenTweaker.MODID)
@Mod.EventBusSubscriber(modid = ZenTweaker.MODID)
public final class TougherTANThirstEffect {

    @GameRegistry.ObjectHolder("toughasnails:thirst") // TODO Should we keep this..?
    public static final Potion THIRST = null;

    @SubscribeEvent
    public static void overrideTANPotion(RegistryEvent.Register<Potion> event)
    {
        // Intentionally override the registry, so that we can have our logic "hijacking" the original one
        event.getRegistry().register(new TougherTANPotionThirst().setRegistryName("toughasnails:thirst"));
    }

    static final class TougherTANPotionThirst extends PotionThirst
    {
        TougherTANPotionThirst() {
            super(-1); // The id param is legacy remain, passing -1 because it doesn't matter now
        }

        @Override
        public void performEffect(EntityLivingBase entity, int amplifier) {
            super.performEffect(entity, amplifier);

            int magicNumber = entity.getRNG().nextInt(4);
            switch (magicNumber)
            {
                case 0: default: break; // You are lucky
                case 1: entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, amplifier)); break;
                case 2: entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, amplifier)); break;
                case 3: entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, amplifier)); break;
            }
        }
    }
}
