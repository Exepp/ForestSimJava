package oop;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.Scanner;


public abstract class Entity 
{
    public Entity(World world, char displayChar)
    {
        this.DisplayChar = displayChar;
        this.world = world;
        
    }
    
    
    abstract void tick();

    abstract void attack(Vec2 pos);

    boolean defendFrom(Entity entity)
    {
        return false;
    }

    void save(PrintWriter printWriter)
    {
        printWriter.printf("%d %d\n", age, str);
    }

    void load(Scanner scanner)
    {
        age = scanner.nextInt();
        str = scanner.nextInt();
    }
    
    void onEvent(KeyEvent e)
    {}

    void draw(JButton arr[][])
    {
        int g = Math.abs(("Green" + this.getClass().getName() + DisplayChar).hashCode()) % 255;
        int r = Math.abs(("Red" + this.getClass().getName() + DisplayChar).hashCode()) % 255;
        int b = Math.abs(("Blue" + this.getClass().getName() + DisplayChar).hashCode()) % 255;
        arr[pos.y][pos.x].setBackground(new Color(r, g, b));
        arr[pos.y][pos.x].setText(DisplayChar + "");
    }


    void setPosition(final Vec2 pos) { this.pos = new Vec2(pos); }

    void setStrength(int str) { this.str = str; }

    void addAge() { ++age; }


    Vec2 getPosition() { return new Vec2(pos); }

    int getInitiative() { return initiative; }

    int getStrength() { return str; }

    int getAge() { return age; }

    
    
    
    protected Vec2 pos = new Vec2(0,0);

    protected int initiative;

    protected int age;

    protected int str;

    protected char DisplayChar = '?';
   
    protected World world;
        
    protected static Random rand = new Random();
}