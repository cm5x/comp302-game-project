package user_interface;

import java.util.ArrayList;

public class controller {

	static ArrayList<player> playerList = new ArrayList<>();

	public controller() {
		//a singleton class to control variables
	}
	
	public static ArrayList getPlayers() {
		return playerList;
	}
	
	public static void addPlayer(player p) {
		playerList.add(p);
	}
	
	
}
