package snownee.zentweaker.feature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import mezz.jei.gui.ingredients.GuiIngredient;
import mezz.jei.gui.recipes.IRecipeGuiLogic;
import mezz.jei.gui.recipes.RecipeLayout;
import mezz.jei.gui.recipes.RecipesGui;
import mezz.jei.input.MouseHelper;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;
import mezz.jei.plugins.vanilla.furnace.FurnaceSmeltingCategory;
import mezz.jei.plugins.vanilla.furnace.SmeltingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ZenTweaker;

@SideOnly(Side.CLIENT)
@KiwiModule(modid = ZenTweaker.MODID, name = "RecipeRemoval", optional = true, dependency = "jei")
public class RecipeRemoval implements IModule
{
    @Override
    public void init()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyPress(KeyboardInputEvent.Post event)
    {
        if (!Keyboard.isRepeatEvent() && Keyboard.isKeyDown(Keyboard.KEY_DELETE))
        {
            if (event.getGui() instanceof RecipesGui)
            {
                RecipesGui recipesGui = (RecipesGui) event.getGui();
                IRecipeGuiLogic logic = ReflectionHelper.<IRecipeGuiLogic, RecipesGui>getPrivateValue(RecipesGui.class, recipesGui, "logic");
                if (logic == null)
                {
                    return;
                }

                List<RecipeLayout> recipeLayouts = ReflectionHelper.<List<RecipeLayout>, RecipesGui>getPrivateValue(RecipesGui.class, recipesGui, "recipeLayouts");
                int mouseX = MouseHelper.getX();
                int mouseY = MouseHelper.getY();

                RecipeLayout hoveredLayout = null;
                Object hoveredIngredient = null;

                if (recipesGui.isMouseOver(mouseX, mouseY))
                {
                    for (RecipeLayout recipeLayout : recipeLayouts)
                    {
                        GuiIngredient<?> clicked = recipeLayout.getGuiIngredientUnderMouse(mouseX, mouseY);
                        if (clicked != null)
                        {
                            Object displayedIngredient = clicked.getDisplayedIngredient();
                            if (displayedIngredient != null)
                            {
                                hoveredLayout = recipeLayout;
                                hoveredIngredient = displayedIngredient;
                            }
                        }
                    }
                }
                if (hoveredIngredient == null || hoveredIngredient.getClass() != ItemStack.class)
                {
                    return;
                }
                IRecipeWrapper recipeWrapper = ReflectionHelper.<IRecipeWrapper, RecipeLayout>getPrivateValue(RecipeLayout.class, hoveredLayout, "recipeWrapper");

                IRecipeCategory category = logic.getSelectedRecipeCategory();
                if (category.getClass() == CraftingRecipeCategory.class)
                {
                    if (!(recipeWrapper instanceof ICraftingRecipeWrapper))
                    {
                        return;
                    }

                    ResourceLocation name = ((ICraftingRecipeWrapper) recipeWrapper).getRegistryName();
                    if (name == null)
                    {
                        return;
                    }
                    addRemoveLine(String.format("recipes.removeByRecipeName(\"%s\");", name));
                }
                else if (category.getClass() == FurnaceSmeltingCategory.class)
                {
                    if (!(recipeWrapper instanceof SmeltingRecipe))
                    {
                        return;
                    }

                    ItemStack output = ReflectionHelper.<ItemStack, SmeltingRecipe>getPrivateValue(SmeltingRecipe.class, (SmeltingRecipe) recipeWrapper, "output");
                    if (output == null || output.isEmpty())
                    {
                        return;
                    }
                    addRemoveLine(String.format("furnace.remove(%s);", getBracket(output)));
                }
                else
                {
                    return;
                }

                EntityPlayer player = Minecraft.getMinecraft().player;
                PositionedSoundRecord record = new PositionedSoundRecord(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 0.8F, 1, (float) player.posX, (float) player.posY, (float) player.posZ);
                Minecraft.getMinecraft().getSoundHandler().playSound(record);
            }
        }
    }

    public String getBracket(ItemStack stack)
    {
        if (stack.getHasSubtypes())
        {
            return String.format("<%s:%s>", stack.getItem().getRegistryName(), stack.getMetadata());
        }
        else
        {
            return String.format("<%s>", stack.getItem().getRegistryName());
        }
    }

    public void addRemoveLine(String str)
    {
        File scriptFile = new File(new File("scripts"), String.format("/%s.zs", "recipes"));
        if (!scriptFile.exists())
        {
            generateFile(scriptFile);
        }
        try
        {
            List<String> lines = new LinkedList<>();
            BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
            String line;
            while ((line = reader.readLine()) != null)
            {
                lines.add(line);
            }
            if (lines.isEmpty())
            {
                generateFile(scriptFile);
                while ((line = reader.readLine()) != null)
                {
                    lines.add(line);
                }
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter(scriptFile));
            for (int i = 0; i < lines.size(); i++)
            {
                String beforeLine = "";
                if (i > 0)
                    beforeLine = lines.get(i - 1);

                String lined = lines.get(i);
                if (beforeLine.trim().equals("//#Remove"))
                {
                    writer.println(str);
                }
                if (!lined.isEmpty())
                {
                    writer.println(lined);
                }
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void generateFile(File f)
    {
        try
        {
            f.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(f));
            writer.println("//This file was created via CT-GUI! Editing it is not advised!");
            writer.println("//Don't touch me!");
            writer.println("//#Remove");
            writer.println();
            writer.println("//Don't touch me!");
            writer.println("//#Add");
            writer.println();
            writer.println("//File End");
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
