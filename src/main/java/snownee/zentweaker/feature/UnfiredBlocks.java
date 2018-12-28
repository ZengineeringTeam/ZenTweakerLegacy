package snownee.zentweaker.feature;

import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ZenTweaker;
import snownee.zentweaker.block.BlockUnfired;

@KiwiModule(modid = ZenTweaker.MODID, name = "UnfiredBlocks", optional = true, dependency = "ceramics")
public class UnfiredBlocks implements IModule
{
    public static final BlockUnfired UNFIRED_BLOCK = new BlockUnfired();
}
