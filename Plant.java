package oop;

import java.util.ArrayList;

public abstract class Plant extends Entity 
{
    public Plant(World world, char displayChar, int reproduceChance)
    {
        super(world, displayChar);
        initiative = str = 0;
        ReproduceChance = reproduceChance; 
    }
    
    @Override
    public void tick()
    {
        ArrayList<Vec2> freeIdx = new ArrayList<>();

	Vec2 min = new Vec2(Math.max(0, pos.x - 1), Math.max(0, pos.y - 1));
	Vec2 max = new Vec2(Math.min(world.getSize().x - 1, pos.x + 1), Math.min(world.getSize().y - 1, pos.y + 1));

	for (int i = min.x; i <= max.x; ++i)
        {
            Vec2 mPos = new Vec2(i, pos.y);
            if(!mPos.equals(pos) && !world.isOccupied(mPos))
		freeIdx.add(mPos);
        }

	for (int i = min.y; i <= max.y; ++i)
        {
            Vec2 mPos = new Vec2(pos.x, i);
            if(!mPos.equals(pos) && !world.isOccupied(mPos))
		freeIdx.add(mPos);
        }
        if(freeIdx.size() != 0)
            attack(freeIdx.get(Math.abs(rand.nextInt()) % freeIdx.size()));
    }

    @Override
    public void attack(Vec2 pos2)
    {
        if(Math.abs(rand.nextInt()) % 100 < ReproduceChance)
            world.spawnEntity(pos2, this.getClass());
    }


    private final int ReproduceChance; // in %
}
