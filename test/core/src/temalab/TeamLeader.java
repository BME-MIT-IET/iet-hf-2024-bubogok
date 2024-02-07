package temalab;

import java.util.Scanner;
import java.io.*;

public class TeamLeader {
	private final OutputStream outputStream;
	private final InputStream inputStream;
	private Team team;
	private Scanner sc;
	private PrintWriter out;


	//maybe itt elkapni a pythonsos stderr-t is, és aszinkron kiírni
	public TeamLeader(Team team, String fileName) {
		this.team = team;
		ProcessBuilder processBuilder = new ProcessBuilder("python3", fileName);
		Process process = null;
		try {
		process = processBuilder.start();
		} catch (IOException e) {
		throw new RuntimeException(e);
		}
		outputStream = process.getOutputStream();
		inputStream = process.getInputStream();
		// outputStream = System.out;
		// inputStream = System.in;
		sc = new Scanner(inputStream);
		out = new PrintWriter(new OutputStreamWriter(outputStream), true);
	}

	public void registerUnit() {
		System.err.print("registering");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream), true);
		out.println(team.getBudget());
		String answer = sc.nextLine();
		String[] split = answer.split(" ");
		loop:while(true) {
			switch (split[0]) {
				case "done": 
					break loop;
				case "add": {
					switch (split[1]) {
						case "tank": {
							team.addUnit(new Unit(new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])), team, Unit.Type.TANK));
						} break;
						case "scout": {
							team.addUnit(new Unit(new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])), team, Unit.Type.SCOUT));
						} break;
						case "infantry": {
							team.addUnit(new Unit(new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])), team, Unit.Type.INFANTRY));
						} break;
						default:
							break loop;
					}
				}
			}
			answer = sc.nextLine();
			split = answer.split(" ");
		}
		out.close();
		System.err.println(team.getName() + " " + "ENDregistering");
	}

	public void communicate() {
		System.err.println(team.getName() + " " + "communicating");
		// TODO: when communication will be done with python, there should be a timeout
		// value
		team.refillActionPoints();
		team.updateUnits();
		out.println(team.units().size());
		out.println(team.teamMembersToString(false).toString());
		// System.err.println(team.units().size());
		// System.err.println(team.teamMembersToString(false).toString());
		if(!sc.hasNext()) {
			System.err.println("SZAR1");
			return;
		}
		String answer = sc.nextLine();
		String[] split = answer.split(" ");
		loop: while (true) { // TODO: a true helyett kell majd egy n seces timer, hogy ne várhasson so kideig a python
			System.err.println("pytohnból jött:" + answer);
			switch (split[0]) {
				case "endTurn":
					break loop;
				case "move": {
					
					if (split.length == 4) {
						// TODO: a parseInt dobhat kivételt, ha nem int
						team.moveUnit(Integer.parseInt(split[1]),
								new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])));
					}
				}
					break;

				case "shoot": {
					if (split.length == 4) {
						// TODO: a parseInt dobhat kivételt, ha nem int
						team.fireUnit(Integer.parseInt(split[1]),
								new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])));
					}
				}
					break;

				default:
					System.err.println("message starting with: " + split[0] + " could not be interpreted");
					break loop;
			} 
			team.updateUnits();
			out.println(team.units().size());
			out.println(team.teamMembersToString(false).toString());
			if(!sc.hasNext()) {
				System.err.println("SZAR2");
				return;
			}
			answer = sc.nextLine();
			split = answer.split(" ");
		}
		System.err.println("ENDcommunicating");
	}

	public void endSimu(boolean win) {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream), true);
		//TODO: ez itt így hagy kívánni valót maga után
		out.println(team.getName() +  " " + win);
		out.close();
	}

	public Team getTeam() {
		return team;
	}
}
