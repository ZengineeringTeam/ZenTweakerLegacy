package snownee.zentweaker.plugin.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategory;

@JEIPlugin
public class CategoryHidingPlugin implements IModPlugin
{
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
    {
        IRecipeRegistry registry = jeiRuntime.getRecipeRegistry();
        for (IRecipeCategory category : registry.getRecipeCategories())
        {
            if (registry.getRecipeCatalysts(category).isEmpty())
            {
                registry.hideRecipeCategory(category.getUid());
            }
        }
    }
}
