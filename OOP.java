package oop;


public class OOP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       	World world = new World(new Vec2(10,10));

        world.spawnEntity(new Vec2(0,0),Human.class);
        world.spawnEntity(new Vec2(10,10),Wolf.class);
        world.spawnEntity(new Vec2(10,11),Wolf.class);
        world.spawnEntity(new Vec2(2,2),Sheep.class);
        world.spawnEntity(new Vec2(2,3),Sheep.class);
        world.spawnEntity(new Vec2(19,7),Fox.class);
        world.spawnEntity(new Vec2(18,6),Fox.class);
        world.spawnEntity(new Vec2(6,19),Turtle.class);
        world.spawnEntity(new Vec2(7,18),Turtle.class);
        world.spawnEntity(new Vec2(13,19),Antelope.class);
        world.spawnEntity(new Vec2(14,18),Antelope.class);
        world.spawnEntity(new Vec2(19,2),Belladona.class);
        world.spawnEntity(new Vec2(18,3),Grass.class);
        world.spawnEntity(new Vec2(2,19),Guarana.class);
        world.spawnEntity(new Vec2(3,18),Hogweed.class);
	world.spawnEntity(new Vec2(18,19),SowThisle.class);
        

        world.draw();

        while(true)
        {
            world.input();
            try{
            Thread.sleep(6);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
    
}
