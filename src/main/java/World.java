
public class World {
 
 public static int  ID_RED  = 1,
                    ID_BLUE = 2,
                    MAX_CREATURES   = 100;
    
    
 protected int RedAmount;
 protected int BlueAmount;
 public double RedSpeed;
 public double BlueSpeed;
 private int RedVision;
 private int BlueVision;
 
 
 /* TRANSITION MATRICES */
 /* | Toward , Away  | 
    | Left   , Right | <- Relative to other creature
 */
 /* Cohesion and Avoidance are intraspecies interactions
    Interaction is interspecies interaction */
 
 public double RedCohesion[][]= {
    {1.0, 0.0},
    {0.5, 0.5}
 };
 public double RedAvoidance[][]= {
    {0.0, 1.0},
    {0.5, 0.5}
 };
 public double RedInteraction[][]= {
    {1.0, 0.0},
    {0.5, 0.5}
 };
 public double BlueCohesion[][]= {
    {1.0, 0.0},
    {0.5, 0.5}
 };
 public double BlueAvoidance[][]= {
    {0.0, 1.0},
    {0.5, 0.5}
 };
 public double BlueInteraction[][]= {
    {0.0, 1.0},
    {0.5, 0.5}
 };
 
 public Creature Red[] = new Creature[MAX_CREATURES];//(ID_RED);
 public Creature Blue[] = new Creature[MAX_CREATURES];//(ID_BLUE);
 
     public World ( ) 
     {
        for(int i = 1; i < MAX_CREATURES; i++){
            int randrx = (int) (Math.random()*450);
            int randry = (int) (Math.random()*200);
            int randbx = (int) (Math.random()*450);
            int randby = (int) (Math.random()*200);
            Red[i]  = new Creature(ID_RED, randrx, randry);
            Blue[i] = new Creature(ID_BLUE, randbx, randby);
           
        }
        
        RedAmount   = 10;
        BlueAmount  = 20;
        RedSpeed    = 20;
        BlueSpeed   = 20;
        RedVision   = 100;
        BlueVision  = 75;
        
     }

    public void teststep () {
     
        for(int i = 1; i <= RedAmount; i++)
        {
            int x = Red[i].getX()+1;
            int y = Red[i].getY()+1;
            Red[i].setX(x);
            Red[i].setY(y);
        }
        
        for(int i = 1; i <= BlueAmount; i++)
        {
            int x = Blue[i].getX()+1;
            int y = Blue[i].getY()+1;
            Blue[i].setX(x);
            Blue[i].setY(y);
        }
        
        
    }

    public void changeRedAmount(int a)
    {
        RedAmount = a;
    }
    
    public void changeBlueAmount(int a)
    {
        BlueAmount = a;
    }
    
    
    public void step( ) {
        
        int curr_x = 0, curr_y = 0, buff_x, buff_y, i,j,k;
        int diff_x = 0, diff_y = 0;
        double temp_x = 0, temp_y = 0;
        int dist_x, dist_y;
        double sumx, sumy;
        double redx[], redy[], bluex[], bluey[];
        int cohesion_seed, avoid_seed, interaction_seed;
        double dist, align_x, align_y, vect_x, vect_y;
        boolean seed, clear_old;
        
        redx = new double[MAX_CREATURES];
        redy = new double[MAX_CREATURES];
        bluex = new double[MAX_CREATURES];
        bluey = new double[MAX_CREATURES];
        
        
        // For each Red creature...
        for(i = 1; i <= RedAmount; i++)
        {
            
            
            // Move for each other Red creature
            for(j = 1; j <= RedAmount; j++)
            {
                // Don't move for self
                if (i != j) {
                  
                    // Get current position
                    if( i < j ) {
                        curr_x = Red[i].getX();
                        curr_y = Red[i].getY();
                    }else{
                        curr_x = Red[i].getX();
                        curr_y = Red[i].getY();
                    }
                  
                    
                    
                    // Figure out distance
                    buff_x = Red[j].getX();
                    buff_y = Red[j].getY();
                    
                    if( (Math.abs(curr_x - buff_x) < RedVision) &&
                        (Math.abs(curr_y - buff_y) < RedVision) ) {
                            
                            // Find Alignment Vector(unit vector in direction of other creature)
                            dist_x  = buff_x - curr_x;
                            dist_y  = buff_y - curr_y;
                            dist    = Math.sqrt(Math.pow(dist_x,2)+Math.pow(dist_y,2));
                            align_x = dist_x/dist;
                            align_y = dist_y/dist;
                            
                            if(dist != 0 ){
                            // Find the new vector
                            seed = (Math.random() < RedCohesion[0][0]);
                            cohesion_seed = seed?1:-1;
                            seed = (Math.random() < RedAvoidance[0][0]);
                            avoid_seed = seed?1:-1;
                            vect_y = RedSpeed*0.01*(cohesion_seed*dist + avoid_seed/Math.pow(dist,2));
                            vect_x = 0;
                            
                            // Realign vector to old coordinate
                            temp_x = (align_x * vect_y);
                            temp_y = (align_y * vect_y);
                            }else{
                                temp_x = 1;
                                temp_y = 1;                        
                            }
                        }
                                    
                    redx[j] = temp_x;
                    redy[j] = temp_y;
                    
                }
                else
                {
                 redx[j] = 0;
                 redy[j] = 0;                    
                }// end if !=
            }// end for j

            sumx = 0;
            sumy = 0;
 
            for(int q = 1; q <= RedAmount; q++)
            {
                sumx = redx[q] + sumx;
                sumy = redy[q] + sumy;
            }
            
            diff_x = (int) (sumx/RedAmount);
            diff_y = (int) (sumy/RedAmount);

            // Move for each Blue creature
            for(k = 1; k <= BlueAmount; k++)
            {
               // Get current position
               curr_x = Red[i].getX();//OldX();
               curr_y = Red[i].getY();//OldY();                    
                    
               // Figure out distance
               buff_x = Blue[k].getX();
                buff_y = Blue[k].getY();
                                
                        if( (Math.abs(curr_x - buff_x) < RedVision) &&
                            (Math.abs(curr_y - buff_y) < RedVision) ) {
                                        
                                // Find Alignment Vector(unit vector in direction of other creature)
                                dist_x  = curr_x - buff_x;
                                dist_y  = curr_y - buff_y;
                                dist    = Math.sqrt(Math.pow(dist_x,2)+Math.pow(dist_y,2));
                                align_x = dist_x/dist;
                            align_y = dist_y/dist;
                            
                            if(dist != 0 ){
                            // Find the new vector
                            seed = (Math.random() < RedInteraction[0][1]);
                            interaction_seed = seed?1:-1;
                            vect_y = RedSpeed*0.01*(interaction_seed*dist);
                            vect_x = 0;
                            
                            // Realign vector to old coordinate
                            temp_x = (align_x * vect_y);
                            temp_y = (align_y * vect_y);
                            }else{
                                temp_x = 1;
                                temp_y = 1;                        
                            }
                            
                                    
                    redx[k] = temp_x;
                    redy[k] = temp_y;
                    
                }
                else
                {
                 redx[j] = 0;
                 redy[j] = 0;                    
                }// end if !=
            }// end for j

            sumx = 0;
            sumy = 0;
            
            for(int p = 1; p <= BlueAmount; p++)
            {
                sumx = redx[p] + sumx;
                sumy = redy[p] + sumy;
            }
            
            diff_x = (int) ((sumx/BlueAmount) + diff_x)/2;
            diff_y = (int) ((sumy/BlueAmount) + diff_y)/2;
            
                    
                    
                    
                    
                    
            if(((curr_x + diff_x) < 20) || ((curr_x + diff_x) > 430) ||
               ((curr_y + diff_y) < 20) || ((curr_y + diff_y) > 180) )
               {   clear_old = true;
               }else{
                    clear_old = false;
               }

            
            curr_x = (curr_x + diff_x)%450;
            curr_y = (curr_y + diff_y)%200;
            if(curr_x < 0) curr_x = curr_x + 450;
            if(curr_y < 0) curr_y = curr_y + 200;
           
           double rand = Math.random();
           if(rand > 0.0 && rand < 0.25)curr_x++;
           else if(rand >= 0.25 && rand < 0.5) curr_x--;
           else if(rand >= 0.5 && rand < 0.75) curr_y++;
           else curr_y--;
           
           
            if(clear_old) {
                Red[i].setbothX(curr_x);
                Red[i].setbothY(curr_y);                
            }else{
                Red[i].setX(curr_x);
                Red[i].setY(curr_y);
            }
            
        }// end for i
        
    


        // For each Blue creature..
        for(i = 1; i <= BlueAmount; i++)
        {
            
            
            // Move for each other Blue creature
            for(j = 1; j <= BlueAmount; j++)
            {
                // Don't move for self
                if (i != j) {
                  
                    // Get current position
                    if( i < j ) {
                        curr_x = Blue[i].getX();//OldX();
                        curr_y = Blue[i].getY();//OldY();
                    }else{
                        curr_x = Blue[i].getX();
                        curr_y = Blue[i].getY();
                    }
                  
                    
                    
                    // Figure out distance
                    buff_x = Blue[j].getX();
                    buff_y = Blue[j].getY();
                    
                    if( (Math.abs(curr_x - buff_x) < BlueVision) &&
                        (Math.abs(curr_y - buff_y) < BlueVision) ) {
                            
                            // Find Alignment Vector(unit vector in direction of other creature)
                            dist_x  = buff_x - curr_x;
                            dist_y  = buff_y - curr_y;
                            dist    = Math.sqrt(Math.pow(dist_x,2)+Math.pow(dist_y,2));
                            align_x = dist_x/dist;
                            align_y = dist_y/dist;
                            
                            if(dist != 0 ){
                            // Find the new vector
                            seed = (Math.random() < BlueCohesion[0][0]);
                            cohesion_seed = seed?1:-1;
                            seed = (Math.random() < BlueAvoidance[0][0]);
                            avoid_seed = seed?1:-1;
                            vect_y = BlueSpeed*0.01*(cohesion_seed*dist + avoid_seed/Math.pow(dist,2));
                            vect_x = 0;
                            
                            // Realign vector to old coordinate
                            temp_x = (align_x * vect_y);
                            temp_y = (align_y * vect_y);
                            }else{
                                temp_x = 1;
                                temp_y = 1;                        
                            }
                        }
                                    
                    bluex[j] = temp_x;
                    bluey[j] = temp_y;
                    
                }
                else
                {
                 bluex[j] = 0;
                 bluey[j] = 0;                    
                }// end if !=
            }// end for j

            sumx = 0;
            sumy = 0;
            
            for(int q = 1; q <= BlueAmount; q++)
            {
                sumx = bluex[q] + sumx;
                sumy = bluey[q] + sumy;
            }
            
            diff_x = (int) (sumx/BlueAmount);
            diff_y = (int) (sumy/BlueAmount);
            
            // Calculate evasion here
            
            // Move for each Red creature
            for(k = 1; k <= RedAmount; k++)
            {
               // Get current position
               curr_x = Blue[i].getX();//OldX();
               curr_y = Blue[i].getY();//OldY();                    
                    
               // Figure out distance
               buff_x = Red[k].getX();
                buff_y = Red[k].getY();
                                
                        if( (Math.abs(curr_x - buff_x) < BlueVision) &&
                            (Math.abs(curr_y - buff_y) < BlueVision) ) {
                                        
                                // Find Alignment Vector(unit vector in direction of other creature)
                                dist_x  = curr_x - buff_x;
                                dist_y  = curr_y - buff_y;
                                dist    = Math.sqrt(Math.pow(dist_x,2)+Math.pow(dist_y,2));
                                align_x = dist_x/dist;
                            align_y = dist_y/dist;
                            
                            if(dist != 0 ){
                            // Find the new vector
                            seed = (Math.random() < BlueInteraction[0][1]);
                            interaction_seed = seed?1:-1;
                            vect_y = BlueSpeed*0.01*(interaction_seed*dist);
                            vect_x = 0;
                            
                            // Realign vector to old coordinate
                            temp_x = (align_x * vect_y);
                            temp_y = (align_y * vect_y);
                            }else{
                                temp_x = 1;
                                temp_y = 1;                        
                            }
                            
                                    
                    redx[k] = temp_x;
                    redy[k] = temp_y;
                    
                }
                else
                {
                 redx[k] = 0;
                 redy[k] = 0;                    
                }// end if !=
            }// end for j

            sumx = 0;
            sumy = 0;
            
            for(int p = 1; p <= RedAmount; p++)
            {
                sumx = redx[p] + sumx;
                sumy = redy[p] + sumy;
            }
            
            diff_x = (int) (((sumx/RedAmount) + diff_x)/2);
            diff_y = (int) (((sumy/RedAmount) + diff_y)/2);
            
            
            
                   
            if(((curr_x + diff_x) < 20) || ((curr_x + diff_x) > 430) ||
               ((curr_y + diff_y) < 20) || ((curr_y + diff_y) > 180) )
               {   clear_old = true;
               }else{
                    clear_old = false;
               }
                    
                    curr_x = (curr_x + diff_x)%450;
                    curr_y = (curr_y + diff_y)%200;
                    if(curr_x < 0) curr_x = curr_x + 450;
                    if(curr_y < 0) curr_y = curr_y + 200;

                
           double rand = Math.random();
           if(rand > 0.0 && rand < 0.25)curr_x++;
           else if(rand >= 0.25 && rand < 0.5) curr_x--;
           else if(rand >= 0.5 && rand < 0.75) curr_y++;
           else curr_y--;
           
           
            if(clear_old) {
                Blue[i].setbothX(curr_x);
                Blue[i].setbothY(curr_y);                
            }else{
                Blue[i].setX(curr_x);
                Blue[i].setY(curr_y);
            }
            
        }// end for i
        
    }

}

