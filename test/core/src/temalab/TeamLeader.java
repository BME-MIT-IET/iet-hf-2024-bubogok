package temalab;
import java.util.Scanner;
import java.io.*;

public class TeamLeader {
    private final OutputStream outputStream;
    private final InputStream inputStream;
	private Team team;
	private Scanner sc;
	
	public TeamLeader(Team team, String fileName) {
		this.team = team;
		// ProcessBuilder processBuilder = new ProcessBuilder("python3", fileName);
		// Process process = null;
		// try {
		//     process = processBuilder.start();
		// } catch (IOException e) {
		//     throw new RuntimeException(e);
		// }
		// outputStream = process.getOutputStream();
		// inputStream = process.getInputStream();
		outputStream = System.out;
		inputStream = System.in;
		sc = new Scanner(inputStream);
	}

	public void communicate() {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream), true);
		
		//TODO: when communication will be done with python, there should be a timeout value
		
		team.refillActionPoints();
		
		team.updateUnits();
		out.println(team.teamMembersToString().toString());
		String answer = sc.nextLine();
		String[] split = answer.split(" ");
		loop:while(true) { //TODO: a true helyett kell majd egy n seces timer, hogy ne várhasson so kideig a python
			switch (split[0]) {
				case "endTurn": 
					break loop;

				case "move": {
					if(split.length == 4) {
						//TODO: a parseInt dobhat kivételt, ha nem int
						team.moveUnit(Integer.parseInt(split[1]), new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])));
					}
				} break;

				case "shoot": {
					if(split.length == 4) {
						//TODO: a parseInt dobhat kivételt, ha nem int
						team.fireUnit(Integer.parseInt(split[1]), new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])));
					}
				} break;

				default:
					break;
			}
			team.updateUnits();
			out.println(team.teamMembersToString().toString());
			answer = sc.nextLine();
			split = answer.split(" ");
		}
	}
	
	public Team getTeam() {
		return team;
	}
}
