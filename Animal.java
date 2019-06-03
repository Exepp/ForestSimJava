package oop;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class Animal extends Entity
{    
    public Animal(World world, char displayChar)
    {
        super(world, displayChar);
    }
    @Override
    public void tick()
    {
        ArrayList<Vec2> freeIdx = new ArrayList<Vec2>();

	if (pos.x - 1 >= 0)
		freeIdx.add(new Vec2(pos.x - 1, pos.y ));
	if (pos.x + 1 < world.getSize().x)
		freeIdx.add(new Vec2( pos.x + 1, pos.y ));
	if (pos.y - 1 >= 0)
		freeIdx.add(new Vec2( pos.x, pos.y - 1 ));
	if (pos.y + 1 < world.getSize().y)
		freeIdx.add(new Vec2( pos.x, pos.y + 1 ));
        int n = Math.abs(rand.nextInt()) % freeIdx.size();
	attack(freeIdx.get(n));
    }
    @Override
    public void attack(Vec2 pos2)
    {
        Entity ePtr = world.getEntityPtr(pos2);
        if (ePtr != null)
            if (reproduced(ePtr) || ePtr.defendFrom(this))
                return;
            else if (str < ePtr.getStrength())
            {                
                world.killEntity(pos, ePtr.getClass());
                return;
            }
            else
                world.killEntity(pos2, this.getClass());
	world.swap(pos, pos2);
    }
    
    private boolean reproduced(Entity other)
    {
        Vec2 pos2 = other.getPosition();
	if (other.DisplayChar == this.DisplayChar)
	{
		Vec2 posMin = new Vec2(Math.min(pos.x, pos2.x), Math.min(pos.y, pos2.y));
		Vec2 posMax = new Vec2(Math.max(pos.x, pos2.x), Math.max(pos.y, pos2.y));
		Vec2 min = new Vec2(Math.max(0, posMin.x - 1), Math.max(0, posMin.y - 1));
		Vec2 max = new Vec2(Math.min(world.getSize().x - 1, posMax.x + 1), Math.min(world.getSize().y - 1, posMax.y + 1));

		for (int i = min.y; i < max.y; ++i)
                    for (int j = min.x; j < max.x; ++j)
			if (world.spawnEntity(new Vec2(j, i), this.getClass()))
                            return true;
		return true;
	}
        return false;
    }
}