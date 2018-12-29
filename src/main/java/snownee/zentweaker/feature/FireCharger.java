package snownee.zentweaker.feature;

import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ZenTweaker;
import snownee.zentweaker.item.ItemFireCharger;

@KiwiModule(modid = ZenTweaker.MODID, name = "FireCharger", optional = true)
public class FireCharger implements IModule
{
    public static final ItemFireCharger FIRE_CHARGER = new ItemFireCharger("fire_charger");
    public static final BlockLight LIGHT = new BlockLight("light");
}
