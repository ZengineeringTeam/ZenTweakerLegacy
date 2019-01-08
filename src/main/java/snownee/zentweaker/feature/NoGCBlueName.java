package snownee.zentweaker.feature;

import net.minecraft.item.EnumRarity;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ZenTweaker;

@KiwiModule(modid = ZenTweaker.MODID, name = "NoGCBlueName", optional = true, dependency = "galacticraftcore")
public class NoGCBlueName implements IModule
{
    public NoGCBlueName()
    {
        try
        {
            Class clazz = Class.forName("micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore");
            ReflectionHelper.setPrivateValue(clazz, null, EnumRarity.COMMON, "galacticraftItem");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
