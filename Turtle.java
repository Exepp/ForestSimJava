package oop;

import static oop.Entity.rand;

public class Turtle extends Animal 
{
    
    public Turtle(World world)
    {
        super(world, 'T');
        initiative = 1;
	str = 2;
    }

    @Override
    public void tick()
    {
	if (Math.abs(rand.nextInt()) % 100 < MoveChance)
		super.tick();
    }

    @Override
    public boolean defendFrom(Entity entity)
    {
        return (entity.getStrength() <= Defence);
    }

    private static final int Defence = 4;

    private static final int MoveChance = 75;
    
}
