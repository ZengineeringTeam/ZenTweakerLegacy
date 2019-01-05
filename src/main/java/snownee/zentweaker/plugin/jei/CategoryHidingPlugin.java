package snownee.zentweaker.plugin.jei;

import java.util.Arrays;
import java.util.List;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategory;
import snownee.zentweaker.ModConfig;

@JEIPlugin
public class CategoryHidingPlugin implements IModPlugin
{
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
    {
        List<String> uids = Arrays.asList(ModConfig.JeiPlugin.HidenJeiCategories);
        if (uids.isEmpty() && !ModConfig.JeiPlugin.PrintJeiCategories)
        {
            return;
        }
        IRecipeRegistry registry = jeiRuntime.getRecipeRegistry();
        for (IRecipeCategory category : registry.getRecipeCategories())
        {
            if (ModConfig.JeiPlugin.PrintJeiCategories)
            {
                System.out.println(category.getTitle() + ": " + category.getUid());
            }
            if (uids.contains(category.getUid()))
            {
                registry.hideRecipeCategory(category.getUid());
            }
        }
    }
}
