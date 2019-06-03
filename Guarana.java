package oop;

public class Guarana extends Plant
{
    public Guarana(World world)
    {
        super(world, 'G', 10);
    }
    
    @Override
    public boolean defendFrom(Entity entity)
    {
        entity.setStrength(entity.getStrength() + StrBoost);
        return false;
    }
    
    private static final int StrBoost = 3;
}
