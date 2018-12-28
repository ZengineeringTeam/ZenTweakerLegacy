package snownee.zentweaker.feature;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
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
        regOre("logOak", "biomesoplenty:log_0");
        regOre("logOak", "biomesoplenty:log_1");
        regOre("logOak", "biomesoplenty:log_2");
        regOre("logOak", "biomesoplenty:log_3");
        regOre("logOak", "forestry:logs.0");
        regOre("logOak", "forestry:logs.1");
        regOre("logOak", "forestry:logs.2");
        regOre("logOak", "forestry:logs.3");
        regOre("logOak", "forestry:logs.4");
        regOre("logOak", "forestry:logs.5");
        regOre("logOak", "forestry:logs.6");
        regOre("logOak", "forestry:logs.7");
        regOre("logOak", "forestry:logs.fireproof.0");
        regOre("logOak", "forestry:logs.fireproof.1");
        regOre("logOak", "forestry:logs.fireproof.2");
        regOre("logOak", "forestry:logs.fireproof.3");
        regOre("logOak", "forestry:logs.fireproof.4");
        regOre("logOak", "forestry:logs.fireproof.5");
        regOre("logOak", "forestry:logs.fireproof.6");
        regOre("logOak", "forestry:logs.fireproof.7");
        regOre("dye", "inspirations:dyed_bottle");
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
