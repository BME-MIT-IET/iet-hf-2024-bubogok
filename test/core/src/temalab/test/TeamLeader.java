package temalab.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class TeamLeader {
    private final OutputStream outputStream;
    private final InputStream inputStream;
	private Team team;
	private Scanner sc;
	
	public TeamLeader(Team t) {
		this.team = t;
		ProcessBuilder processBuilder = new ProcessBuilder("python3", "../test.py");
//		processBuilder.redirectInput();
//		processBuilder.redirectOutput();
		Process process = null;
		try {
		    process = processBuilder.start();
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
		outputStream = process.getOutputStream();
		inputStream = process.getInputStream();
		try {
			System.out.println(process.isAlive());
			System.out.println(inputStream.available());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc = new Scanner(inputStream);
	}
	
	public String[] getAnswer(List<Integer> ids) {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream/*, UTF_8*/), true);
		out.println(ids.toString());
		if (!sc.hasNext()) {
			System.out.println("helo");
		}
		String answer = sc.nextLine();
		String[] split = answer.split(" ");
		return split;
	}
	
	public Team getTeam() {
		return this.team;
	}
}