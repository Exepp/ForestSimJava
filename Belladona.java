package oop;

public class Belladona extends Plant
{
    public Belladona(World world)
    {
        super(world, 'B', 10);
        str = 99;
    }
    
    @Override
    public boolean defendFrom(Entity entity)
    {
        world.killEntity(entity.getPosition(), this.getClass());
        return false;
    }
}
