package temalab.communicator;

import temalab.logger.Log;
import temalab.model.Position;
import temalab.model.Team;
import temalab.logger.Label;

import java.io.*;
import java.util.Scanner;

public class Communicator {
	private final OutputStream outputStream;
	private final InputStream inputStream;
	private final Team team;
	private Scanner sc;
	private PrintWriter out;
	private int runCounter = 0;
	private int messageCounter = 0;
	private boolean simuEnded = false;
	private boolean weWon = false;
	Thread errorThread;
	Process process;

	private final Label javaLogLabel;
	private final Label pythonLogLabel;

	public Communicator(Team team, String fileName, String strategy) {
		this.team = team;
		javaLogLabel = new Label(
			team.getName() + " java",
			Label.Color.BLACK,
			team.getName().equals("red") ? Label.Color.RED : Label.Color.WHITE
		);
		pythonLogLabel = new Label(
			team.getName() + " python",
			Label.Color.BLACK,
			team.getName().equals("red") ? Label.Color.RED : Label.Color.WHITE
		);
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
			erroReader.lines().forEach(s -> Log.d(pythonLogLabel, s));
		});
		errorThread.start();
		out.println(team.getName());
		out.println(team.getMainModel().width());
	}

	public void registerUnit() {
		// Log.d(logLabel, "registering");
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
		// Log.d(logLabel, "ENDregistering");
	}


	public void communicate() {
		team.refillActionPoints();
		runCounter++;
		Log.d(javaLogLabel, "RUN:" + runCounter);
		Log.d(javaLogLabel, "communicating");

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
			Log.d(javaLogLabel, "anwser from python: " + answer);
			String[] split = answer.split(" ");
			switch (split[0]) {
				case "endTurn":
					break loop;
				case "reset":
					Log.d(javaLogLabel, "reset shuold happen");
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
					Log.w(javaLogLabel, "anwser starting with: " + split[0] + " could not be interpreted");
					break loop;
			}
		}
		Log.d(javaLogLabel, "ENDcommunicating");
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
