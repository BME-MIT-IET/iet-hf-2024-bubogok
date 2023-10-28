package temalab.test;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.*;


public class TeamLeader {
    private final OutputStream outputStream;
    private final InputStream inputStream;
	private Team team;
	private Scanner sc;
	
	public TeamLeader(Team t) {
		this.team = t;
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
	//TODO: ki kellene javítani, ez így végtelen bohóckodás
	public String[] getAnswer() {
		var ids = team.unitIDs();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream), true);
		out.println(ids.toString());
		String answer = sc.nextLine();
		String[] split = answer.split(" ");
		return split;
	}
	//TODO: ki kellene javítani, ez így végtelen bohóckodás
	public void doMagic() {
		this.team.doAction(getAnswer());
	}
	
	public Team getTeam() {
		return this.team;
	}
}
