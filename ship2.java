import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    Land land;
    Ship ship;
    
    public class Ship{
        Vector2 pos = new Vector2 (0,0);
        Vector2 vel = new Vector2 (0,0);
        float angle = 0f;
        float fuel = 0f;
        int thrust = 0;
        int descentSpeed = -20;
        
        void update(){
            int R = 0;
            Vector2 desiredVel = new Vector2(0,0);
            
            //Horizontal vel
            if(land.isOverLandingPad(pos, 0.5f)){
                //if(vel.x > 15) desiredVel.x = 10;
                //else if(vel.x <-15) desiredVel.x = -10;
            }else{
                desiredVel.x = pos.x<land.landingSite.x ? 40 : -40;
            }
            
            if(pos.y < land.landingSite.y ) desiredVel.y = 50;
            else{
                desiredVel.y = descentSpeed;
            }
            
            //calculate the acceleration en outputs
            Vector2 desAcc = desiredVel.copy().subs(vel);
            
            desAcc.sum(new Vector2(0f ,3.711f)); //gravity compensation
            
            if(desAcc.y < 0) desAcc.y = 0;
            
            System.err.println(desAcc);
            
            int dAngle = (int) Math.round(Math.toDegrees(Math.atan2(-desAcc.x,desAcc.y)));
            if(dAngle > 90 ) dAngle = 90;
            else if(dAngle< -90 ) dAngle = -90; 
            
            
            int dThrust = Math.round(desAcc.length());
            if(dThrust > 4) dThrust = 4;
            else if( dThrust < 0) dThrust = 0;
            
            //Calculate if next step will be on land
            if(land.isOverLandingPad(pos, 1.0f) && pos.copy().sum(vel).y -50 <= land.landingSite.y){
                dAngle = 0;
            }
            
            if(vel.y<-38) dAngle = 0;
            
            if(Math.abs(angle- dAngle)>90){
                dThrust = 0;
            }
            
            
            System.out.println(dAngle + " " + dThrust);
        }
    }
    
    public class Land{
        Vector2 [] points;
        Vector2 landingSite, landingR, landingL;
        
        public Land(Vector2[] p){
            points = p;
            findCenterLandingSite();
        }
        public void findCenterLandingSite(){
            for(int i = 0; i< points.length-1; i++){
                if(points[i].y == points[i+1].y){
                    landingL = points[i];
                    landingR = points[i+1];
                    landingSite = new Vector2((landingL.x + landingR.x)/2f, landingL.y);
                    
                    return;
                }
            }
        }
        public boolean isOverLandingPad(Vector2 v, float percent){
            float xmin = landingSite.x - (landingSite.dist(landingL) * percent);
            float xmax = landingSite.x + (landingSite.dist(landingR) * percent);
            if(v.x > xmin && v.x< xmax) return true;
            else return false;
        }
    }

    public static void main(String args[]) {
        Player p = new Player();
        Scanner in = new Scanner(System.in);
        p.init(in);

        // game loop
        while (true) {
            p.parseContext(in);
            p.play();
            
        }
    }
    
    public void init(Scanner in){
        int N = in.nextInt(); // the number of points used to draw the surface of Mars.
        Vector2 [] ps = new Vector2[N];
        for (int i = 0; i < N; i++) {
            int LAND_X = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            int LAND_Y = in.nextInt();// Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
            ps[i] = new Vector2(LAND_X,LAND_Y);
        }
        land = new Land(ps);
        ship = new Ship();
    }
    
    public void play(){
        ship.update();
    }
    
    public void parseContext(Scanner in){
        int X = in.nextInt();
        int Y = in.nextInt();
        ship.pos.set(X,Y);
        int HS = in.nextInt(); // the horizontal speed (in m/s), can be negative.
        int VS = in.nextInt(); // the vertical speed (in m/s), can be negative.
        ship.vel.set(HS,VS);
        int F = in.nextInt(); // the quantity of remaining fuel in liters.
        ship.fuel = F;
        int R = in.nextInt(); // the rotation angle in degrees (-90 to 90).
        ship.angle = R;
        int P = in.nextInt(); // the thrust power (0 to 4).
        ship.thrust = P;
    }
    
    public class Vector2{
        float x;
        float y;
        public Vector2(float xx, float yy){
            x = xx;
            y = yy;
        }
        
        public Vector2 set(float xx, float yy){
            x = xx; y = yy;
            return this;
        }
        
        public Vector2 set(Vector2 v){
            x = v.x; y = v.y;
            return this;
        }
        
        public Vector2 sum(Vector2 v){
            x += v.x; y += v.y;
            return this;
        }
        
        public Vector2 subs(Vector2 v){
            x -= v.x; y -= v.y;
            return this;
        }
        
        public Vector2 mult(float m){
            x *= m; y *= m;
            return this;
        }
        
        public Vector2 div(float m){
            x /= m; y /= m;
            return this;
        }
        
        public String toString(){
            return ("x: " + x + " , y: " + y);
        }
        
        public Vector2 copy(){
            return new Vector2(x,y);
        }
        
        public float dist(Vector2 v1){
            return (float)(Math.sqrt( Math.pow(v1.x-x, 2) + Math.pow(v1.y-y, 2) ));
        }
        
        public float length(){
            return (float)(Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) ));
        }
    }
}
