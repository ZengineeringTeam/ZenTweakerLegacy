package snownee.zentweaker.worldgen;

import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;

public class ZenMapGenVillage extends MapGenVillage
{
    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        try
        {
            return new Start(this.world, this.rand, chunkX, chunkZ, this.size);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.getStructureStart(chunkX, chunkZ);
    }

    // Test seed: 3465711404790626791
    // -160 -240
    public static class Start extends StructureStart
    {
        /** well ... thats what it does */
        private boolean hasMoreThanTwoComponents;

        public Start()
        {
        }

        public Start(World worldIn, Random rand, int x, int z, int size)
        {
            super(x, z);
            List<PieceWeight> list = ZenStructureVillagePieces.getStructureVillageWeightedPieceList(rand, size);
            ZenStructureVillagePieces.Start start = new ZenStructureVillagePieces.Start(worldIn.getBiomeProvider(), 0,
                    rand, (x << 4) + 2, (z << 4) + 2, list, size);
            this.components.add(start);
            start.buildComponent(start, this.components, rand);
            List<StructureComponent> list1 = start.pendingRoads;
            List<StructureComponent> list2 = start.pendingHouses;

            while (!list1.isEmpty() || !list2.isEmpty())
            {
                if (list1.isEmpty())
                {
                    int i = rand.nextInt(list2.size());
                    StructureComponent structurecomponent = list2.remove(i);
                    structurecomponent.buildComponent(start, this.components, rand);
                }
                else
                {
                    int j = rand.nextInt(list1.size());
                    StructureComponent structurecomponent2 = list1.remove(j);
                    structurecomponent2.buildComponent(start, this.components, rand);
                }
            }

            this.updateBoundingBox();
            int k = 0;

            for (StructureComponent structurecomponent1 : this.components)
            {
                if (!(structurecomponent1 instanceof ZenStructureVillagePieces.Road))
                {
                    ++k;
                }
            }

            this.hasMoreThanTwoComponents = k > 2;
        }

        /**
         * currently only defined for Villages, returns true if Village has more than 2
         * non-road components
         */
        @Override
        public boolean isSizeableStructure()
        {
            return this.hasMoreThanTwoComponents;
        }

        @Override
        public void writeToNBT(NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);
            tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
        }

        @Override
        public void readFromNBT(NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);
            this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
        }
    }
}
