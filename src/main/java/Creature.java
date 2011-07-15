
public class Creature
{
 
    private int identity; // species identity, needs to be unique or 
                        //  interaction will not work correctly
 
    private int x;
    private int y;
    private int old_x;
    private int old_y;
    
    // Constructors
    public Creature(int id, int init_x, int init_y)
    {
        identity    = id;
        x           = init_x;
        y           = init_y;
        old_x       = x;
        old_y       = y;
    }

    public Creature(int id)
    {
        identity    = id;
        x           = 0;
        y           = 0;
        old_x       = x;
        old_y       = y;
    }

    
    public int getX( ) { return x; }
    public int getY( ) { return y; }
    public int getOldX( ) { return old_x; }
    public int getOldY( ) { return old_y; }
    public int getIdentity ( ) { return identity; }
    
    public void setX ( int new_x )
    {
        old_x = x;
        x = new_x;
    }
    public void setY ( int new_y )
    {
        old_y = y;
        y = new_y;
    }  
    public void setbothX ( int new_x )
    {
        old_x = new_x;
        x = new_x;
    }
    public void setbothY ( int new_y )
    {
        old_y = new_y;
        y = new_y;
    }  
}
