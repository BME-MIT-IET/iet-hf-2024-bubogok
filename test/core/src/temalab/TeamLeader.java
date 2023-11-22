package temalab;
import java.util.Scanner;
import java.io.*;

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
	//TODO: más unitok seenUnits és seenFieldsjét nem kellene átadni, 
	//		vagy ki kellene venni belőle
	public void communicate() {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream), true);
		//System.out.println(team.teamMembersToString().toString());
		//out.println(team.teamMembersToString().toString());
		//String answer = sc.nextLine();
		//String[] split = answer.split(" ");
		try {
			BufferedWriter out2 = new BufferedWriter(new FileWriter("asdf.txt"));
			out2.write(team.teamMembersToString().toString());
			out2.newLine();
			out2.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String[] asdf = null;
		team.doAction(asdf);
	}
	
	public Team getTeam() {
		return team;
	}
}
