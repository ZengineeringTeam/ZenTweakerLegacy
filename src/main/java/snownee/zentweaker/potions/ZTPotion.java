package snownee.zentweaker.potions;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import snownee.zentweaker.ZenTweaker;

public class ZTPotion extends Potion
{

    public ZTPotion(boolean isBadEffectIn, int liquidColorIn)
    {
        super(isBadEffectIn, liquidColorIn);
    }

    public static Potion BRISKNESS;
    public static Potion HEAVINESS;

    public static void init()
    {
        BRISKNESS = registerPotion("briskness",
                (new ZTPotion(false, -1)).setPotionName("effect.briskness")
                        .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED,
                                "11AEAA56-376B-4498-935B-2F7F68070635", 0.15D, 2)
                        .setBeneficial());
        HEAVINESS = registerPotion("heaviness",
                (new ZTPotion(true, -1)).setPotionName("effect.heaviness").registerPotionAttributeModifier(
                        SharedMonsterAttributes.MOVEMENT_SPEED, "1107DE5E-7CE8-4030-940E-514C1F160890", -0.15D, 2));
    }

    public static Potion registerPotion(String name, Potion potion)
    {
        ResourceLocation location = new ResourceLocation(ZenTweaker.MODID, name);
        potion.setRegistryName(location);
        ForgeRegistries.POTIONS.register(potion);
        return potion;
    }

    @Override
    public boolean shouldRender(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(PotionEffect effect)
    {
        return false;
    }

    @Override
    public List<ItemStack> getCurativeItems()
    {
        return new ArrayList<>();
    }

}
