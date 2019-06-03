package oop;

import java.util.ArrayList;
import static oop.Entity.rand;

public class Fox extends Animal 
{
    
    public Fox(World world)
    {
        super(world, 'F');
        initiative = 7;
	str = 3;
    }

    @Override
    public void tick()
    {
        ArrayList<Vec2> freeIdx = new ArrayList<Vec2>();

	Vec2 min = new Vec2(Math.max(0, pos.x - 1), Math.max(0, pos.y - 1));
	Vec2 max = new Vec2(Math.min(world.getSize().x - 1, pos.x + 1), Math.min(world.getSize().y - 1, pos.y + 1));

	for (int i = min.x; i <= max.x; ++i)
        {
            Vec2 mPos = new Vec2(i, pos.y);
            if(!mPos.equals(pos) && (!world.isOccupied(mPos) || (world.getEntityPtr(mPos).getStrength() <= str || this.DisplayChar == world.getEntityPtr(mPos).DisplayChar)))
		freeIdx.add(mPos);
        }

	for (int i = min.y; i <= max.y; ++i)
        {
            Vec2 mPos = new Vec2(pos.x, i);
            if(!mPos.equals(pos))
		freeIdx.add(mPos);
        }
        if(!freeIdx.isEmpty())
            attack(freeIdx.get(Math.abs(rand.nextInt()) % freeIdx.size()));
    }    
}
