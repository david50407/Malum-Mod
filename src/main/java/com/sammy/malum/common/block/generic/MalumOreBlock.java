package com.sammy.malum.common.block.generic;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class MalumOreBlock extends OreBlock
{
    public final int minExperience;
    public final int maxExperience;

    public MalumOreBlock(Properties properties, int minExperience, int maxExperience)
    {
        super(properties);
        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
    }

    @Override
    protected int getExperience(Random rand)
    {
        return MathHelper.nextInt(rand, minExperience, maxExperience);
    }
}