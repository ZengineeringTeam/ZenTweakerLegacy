package snownee.zentweaker.feature;

import net.minecraft.entity.SharedMonsterAttributes;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.potion.PotionMod;
import snownee.zentweaker.ZenTweaker;

@KiwiModule(modid = ZenTweaker.MODID, name = "AlternativeSpeedPotions", optional = true)
public class AlternativeSpeedPotions implements IModule
{
    public static final PotionMod BRISKNESS = new PotionMod("briskness", false, 0, false, 0, -1, false, false);
    public static final PotionMod HEAVINESS = new PotionMod("heaviness", false, 1, true, 0, -1, false, false);

    @Override
    public void init()
    {
        BRISKNESS.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "11AEAA56-376B-4498-935B-2F7F68070635", 0.15D, 2);
        HEAVINESS.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "1107DE5E-7CE8-4030-940E-514C1F160890", -0.15D, 2);
    }

}
