package oop;

public class Vec2 {
    public int x = 0;
    public int y = 0;
    
    public Vec2(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vec2(final Vec2 other)
    {
        this.x = other.x;
        this.y = other.y;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(other != null && this.getClass().equals(other.getClass()))
        {
            Vec2 oth = (Vec2)other;
            return (x == oth.x && y == oth.y);
        }
        return false;
    }
}
