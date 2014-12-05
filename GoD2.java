import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.Point;

class Player {

	Team[] teams; // all the team of drones. Array index = team's ID
	int myTeamIdx; // index of my team in the array of teams
	Zone[] zones; // all game zones

	class Team {
		Drone[] drones; // position of the drones
	}
	
	class Drone{
	    Point pos = new Point();
	    
	    void update(){
	        
	    }
	    
	    public String toString(){
	        return pos;
	    }
	}

	class Zone {
		Point pos = new Point(); // center of this zone (circle radius = 100 units)
		int owner = -1; // ID of the team which owns this zone, -1 otherwise
		public String toString(){
		    return pos + "  owner: " + owner;
		}
		public void update(){
		    
		    
		}
		
	}

	/**
	 * Compute logic here. This method is called for each game round.
	 */
	void play() {
		Team me = teams[myTeamIdx];
		
		
		
		
		
	}

	// program entry point
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		Player p = new Player();
		p.parseInit(in);
        System.err.println("INIT");
		while (true) {
		    System.err.println("PLAYING");
			p.parseContext(in);
			p.play();
		}
	}

	// parse games data (one time at the beginning of the game: P I D Z...)
	void parseInit(Scanner in) {
		teams = new Team[in.nextInt()]; // P
		myTeamIdx = in.nextInt(); // I

		int cntDronesPerTeam = in.nextInt(); // D
		for (int i = 0; i < teams.length; i++) {
			Team t = new Team();
			t.drones = new Drone[cntDronesPerTeam];
			for(int p = 0 ; p < cntDronesPerTeam; p++){
			    t.drones[p] = new Drone();
			} 
			teams[i] = t;
		}

		zones = new Zone[in.nextInt()]; // Z

		for (int i = 0; i < zones.length; i++) {
			Zone z = new Zone();
			z.pos.x = in.nextInt();
			z.pos.y = in.nextInt();
			zones[i] = z;
		}
	}

	// parse contextual data to update the game's state (called for each game round)
	void parseContext(Scanner in) {
		for (Zone z : zones) {
			z.owner = in.nextInt(); // update zones owner
		}

		for (int i = 0; i < teams.length; i++) {
			Team t = teams[i];
			for (int j = 0; j < t.drones.length; j++) {
				Point pp = new Point(in.nextInt(), in.nextInt()); // update drones position
			    t.drones[j].pos = pp;
			    
			}
		}
	}
	
	public Zone findClosestEnemyZone(Point p){
	    Zone zr = null;
	    double dist = -1;
	    for(Zone z: zones){
	        if(z.owner!= myTeamIdx){
	            if(dist == -1){
	                zr = z;
	                dist = p.distance(z.pos);
	            }else if(dist > p.distance(z.pos)){
	                zr = z;
	                dist = p.distance(z.pos);
	            }
	        }
	    }
	    System.err.println(zr);
	    return zr;
	}
	
	public Drone findClosestDrone(Team team, Point p){
	    Drone d = null;
	    double dist = 5000000;
	    for(Drone dro:team.drones){
	        if(dro.zone == null){
	            if(dist > dro.position.distance(p)){
	                dist = dro.position.distance(p);
	                d = dro;
	            }
	        }
	    }
	    return d;
	}
	
	Zone[] attackSequence;
	public void createListOfZones(){
	    attackSequence = new Zone[zones.size];
	    ArrayList<Zone> temp = new ArrayList<Zone>(zones);
	    attackSequence[0] = temp.get(0);
	    temp.remove(0);
	    
	    temp.at
	    
	}
	
}
