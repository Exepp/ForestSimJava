package oop;

import java.util.ArrayList;
import static oop.Entity.rand;

public class Hogweed extends Plant
{
    public Hogweed(World world)
    {
        super(world, '#', 10);
        str = 10;
    }
    
    @Override
    public void tick()
    {
        super.tick();
        
	Vec2 min = new Vec2(Math.max(0, pos.x - 1), Math.max(0, pos.y - 1));
	Vec2 max = new Vec2(Math.min(world.getSize().x - 1, pos.x + 1), Math.min(world.getSize().y - 1, pos.y + 1));

	for (int i = min.x; i <= max.x; ++i)
        {
            Vec2 mPos = new Vec2(i, pos.y);
            if(!mPos.equals(pos) && world.isOccupied(mPos) && world.getEntityPtr(mPos) instanceof Animal)
		world.killEntity(mPos, this.getClass());
        }

	for (int i = min.y; i <= max.y; ++i)
        {
            Vec2 mPos = new Vec2(pos.x, i);
            if(!mPos.equals(pos) && world.isOccupied(mPos) && world.getEntityPtr(mPos) instanceof Animal)
		world.killEntity(mPos, this.getClass());
        }
    }
    
    @Override
    public boolean defendFrom(Entity entity)
    {
        world.killEntity(entity.getPosition(), this.getClass());
        return false;
    }
}
