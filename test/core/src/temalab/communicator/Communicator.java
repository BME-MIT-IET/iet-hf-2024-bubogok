package temalab.communicator;

import temalab.model.Position;
import temalab.model.Team;

import java.io.*;
import java.util.Scanner;

public class Communicator {
	private final OutputStream outputStream;
	private final InputStream inputStream;
	private Team team;
	private Scanner sc;
	private PrintWriter out;
	private int runCounter = 0;
	private int messageCounter = 0;
	private boolean simuEnded = false;
	private boolean weWon = false;
	Thread errorThread;
	Process process;

	public Communicator(Team team, String fileName, String strategy) {
		this.team = team;
		String currDir = System.getProperty("user.dir");
		ProcessBuilder processBuilder = new ProcessBuilder("python3", currDir + '/' + fileName, strategy);
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
		BufferedReader erroReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		errorThread = new Thread(() -> {
			var color = team.getName();
			if (color.equals("white")) {
				erroReader.lines().forEach(s -> System.err.println("\033[47;30mdebug from " + team.getName() + " : " + s + "\033[0m"));
			} else if (color.equals("red")) {
				erroReader.lines().forEach(s -> System.err.println("\033[41;30mdebug from " + team.getName() + " : " + s + "\033[0m"));
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		errorThread.start();
		out.println(team.getName());
		out.println(team.getMainModel().width());
	}

	public void registerUnit() {
		// System.err.print("registering");
		// out.println("regPhase");
		// out.println(team.getBudget());
		// String answer = sc.nextLine();
		// String[] split = answer.split(" ");
		// loop:while(true) {
		// 	switch (split[0]) {
		// 		case "done": 
		// 			break loop;
		// 		case "add": {
		// 			switch (split[1]) {
		// 				case "tank": {
		// 					team.addUnit(new Unit(new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])), team, Unit.Type.TANK));
		// 				} break;
		// 				case "scout": {
		// 					team.addUnit(new Unit(new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])), team, Unit.Type.SCOUT));
		// 				} break;
		// 				case "infantry": {
		// 					team.addUnit(new Unit(new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])), team, Unit.Type.INFANTRY));
		// 				} break;
		// 				default:
		// 					break loop;
		// 			}
		// 		}
		// 	}
		// 	answer = sc.nextLine();
		// 	split = answer.split(" ");
		// }
		// System.err.println(team.getName() + " " + "ENDregistering");
	}


	public void communicate() {
		team.refillActionPoints();
		runCounter++;
		System.err.println("\033[0;35mdebug from " + team.getName() + "RUN:" + runCounter + "\033[0m");
		System.err.println("\033[0;35mdebug from " + team.getName() + " " + "communicating" + "\033[0m");

		loop:
		while (true) {
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			messageCounter++;
			team.updateUnits();
			out.println(messageCounter);
			if (!simuEnded) {
				out.println("commPhase");
				out.println(team.units().size());
				out.println(team.teamMembersToString(false).toString());
			} else {
				out.println("endPhase");
				out.println(team.getName() + " " + weWon);
				simuEnded = false;
			}
			if (!sc.hasNext()) {
				throw new RuntimeException("No answer from python");
			}
			String answer = sc.nextLine();
			System.err.println("\033[0;35m pytohnból jött:" + answer + "\033[0m");
			String[] split = answer.split(" ");
			switch (split[0]) {
				case "endTurn":
					break loop;
				case "reset":
					System.out.println("reset should happen");
					team.reset();
					break loop;
				case "move": {
					unitMove(split);
				}
				break;
				case "shoot": {
					unitShoot(split);
				}
				break;
				default:
					System.err.println("\033[0;35mdebug from " + "message starting with: " + split[0] + " could not be interpreted" + "\033[0m");
					break loop;
			}
		}

		System.err.println("\033[0;35mdebug from " + "ENDcommunicating" + "\033[0m");
	}

	private void unitMove(String[] split) {
		if (split.length == 4) {
			team.moveUnit(Integer.parseInt(split[1]),
					new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])));
		}
	}

	private void unitShoot(String[] split) {
		if (split.length == 4) {
			team.fireUnit(Integer.parseInt(split[1]),
					new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3])));
		}
	}

	public void endSimu(boolean win) {
		simuEnded = true;
		weWon = win;
	}

	public void closeThread() {

		process.destroy();
		try {
			errorThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Team getTeam() {
		return team;
	}
}
