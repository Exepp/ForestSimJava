package oop;

import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.Scanner;


public class Human extends Animal {
    
    Human(World world)
    {
        super(world, 'H');
        initiative = 4;
        str = DefaultStr;
        powerUp = -AbilityExh;
    }
    
    @Override
    public void save(PrintWriter printWriter)
    {
        super.save(printWriter);
        printWriter.printf(" %d \n", powerUp);
    }
    
    @Override
    public void load(Scanner scanner)
    {
        super.load(scanner);
        powerUp = scanner.nextInt();
    }      
    
    @Override
    public void tick()
    {
        if (powerUp > -AbilityExh)
            --powerUp;
	if (powerUp > 0)
            str = DefaultStr + powerUp;
        
        if(!nextTickDirection.equals(new Vec2(0,0)))
        {
            Vec2 nextPos = new Vec2(pos.x + nextTickDirection.x, pos.y + nextTickDirection.y);
            if(nextPos.x >= 0 && nextPos.x < world.getSize().x && nextPos.y >= 0 && nextPos.y < world.getSize().y)
                attack(nextPos);
            nextTickDirection = new Vec2(0,0);
        }
    }
    
    @Override
    void onEvent(KeyEvent event)
    {
        int code = event.getKeyCode();
        Vec2 DirVec[] = { new Vec2(-1, 0), new Vec2(0, -1), new Vec2(1, 0), new Vec2(0, 1) };
        if(code >= 37 && code <= 40)
            nextTickDirection = DirVec[code-37];   
        else if(code == 83)
            if(powerUp <= -AbilityExh)
            {
                powerUp = AbilityPowerUp;
                world.addComment("Czlowiek uzyl specjalnej umiejetnosci!");
            }
    }
    
    private Vec2 nextTickDirection = new Vec2(0,0);
    private int powerUp = 0;
    private static final int DefaultStr = 5;
    private static final int AbilityExh = 5;
    private static final int AbilityPowerUp = 5;
    
}
