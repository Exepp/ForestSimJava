package oop;

import java.util.ArrayList;
import static oop.Entity.rand;

public class Antelope extends Animal 
{
    
    public Antelope(World world)
    {
        super(world, 'A');
        initiative = 4;
	str = 4;
    }

    @Override
    public void tick()
    {
        ArrayList<Vec2> freeIdx = new ArrayList<>();

	Vec2 min = new Vec2(Math.max(0, pos.x - MaxMoveDist), Math.max(0, pos.y - MaxMoveDist));
	Vec2 max = new Vec2(Math.min(world.getSize().x - 1, pos.x + MaxMoveDist), Math.min(world.getSize().y - 1, pos.y + MaxMoveDist));

	for (int i = min.x; i <= max.x; ++i)
        {
            Vec2 mPos = new Vec2(i, pos.y);
            if(!mPos.equals(pos))
		freeIdx.add(mPos);
        }

	for (int i = min.y; i <= max.y; ++i)
        {
            Vec2 mPos = new Vec2(pos.x, i);
            if(!mPos.equals(pos))
		freeIdx.add(mPos);
        }

	attack(freeIdx.get(Math.abs(rand.nextInt()) % freeIdx.size()));
    }

    @Override
    public boolean defendFrom(Entity entity)
    {
        return (Math.abs(rand.nextInt()) % 100 < ChanceToRun);
    }

    private static final int ChanceToRun = 50; // in %

    private static final int MaxMoveDist = 2;
    
}
