package oop;

public class SowThisle extends Plant
{
    public SowThisle(World world)
    {
        super(world, '9', 10);
    }
    
    @Override
    public void tick()
    {
        for (int i = 0; i < ReproduceAttempts; ++i)
            super.tick();
    }
    
    private static final int ReproduceAttempts = 3;
}
