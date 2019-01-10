package snownee.zentweaker.feature;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ModConfig;
import snownee.zentweaker.ZenTweaker;

@KiwiModule(modid = ZenTweaker.MODID, name = "RegisterWildcardOres", optional = true)
public class RegisterWildcardOres implements IModule
{
    public RegisterWildcardOres()
    {
    }

    @Override
    public void init()
    {
        for (String line : ModConfig.WildcardOres.ores)
        {
            String[] parts = line.trim().split("=");
            if (parts.length == 2)
            {
                regOre(parts[0], parts[1]);
            }
        }
    }

    private void regOre(String ore, String name)
    {
        ResourceLocation location = new ResourceLocation(name);
        if (Loader.isModLoaded(location.getNamespace()))
        {
            Item item = ForgeRegistries.ITEMS.getValue(location);
            if (item != null)
            {
                OreDictionary.registerOre(ore, new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
            }
        }
    }
}
