package temalab;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.*;
import com.google.gson.Gson;


public class TeamLeader {
    private final OutputStream outputStream;
    private final InputStream inputStream;
	private Team team;
	private Scanner sc;
	
	public TeamLeader(Team team) {
		this.team = team;
		ProcessBuilder processBuilder = new ProcessBuilder("python3", "test.py");
		Process process = null;
		try {
		    process = processBuilder.start();
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
		outputStream = process.getOutputStream();
		inputStream = process.getInputStream();
		sc = new Scanner(inputStream);
		
	}
	//TODO: Itt kellene parsolni
	public void communicate() {
		Gson gson = new Gson();
		var ids = team.unitIDs();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream), true);
		out.println(ids.toString());
		System.out.println(gson.toJson(team));
		System.out.println();
		String answer = sc.nextLine();
		String[] split = answer.split(" ");
		team.doAction(split);
	}
	
	public Team getTeam() {
		return team;
	}
}
