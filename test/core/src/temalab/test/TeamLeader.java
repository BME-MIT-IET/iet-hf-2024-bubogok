package temalab.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TeamLeader {
	
	private Team team;
	CommMaster cm = null;
	private Scanner input;
	
	public TeamLeader(Team t) {
		this.team = t;
//		ProcessBuilder processBuil      
//      try {
//          process = processBuilder.start();
//      } catch (IOException e) {
//          throw new RuntimeException(e);
//      }
//
//      cm = new CommMaster(process.getInputStream(),
//              process.getOutputStream(),
//              process.getErrorStream());

      //    	  ArrayList<String> answer = cm.getAnswer(); 
	}
	
	public void sendMessage(List<Integer> ids) {
		System.out.println(ids.toString());
	}
	public String[] getInput() {
		input = new Scanner(System.in);
		String answer = input.nextLine();
		String[] split = answer.split(" ");
		return split;
	}
	
	public Team getTeam() {
		return this.team;
	}
}