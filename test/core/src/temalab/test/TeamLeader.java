package temalab.test;

import java.io.IOException;
import java.util.ArrayList;

public class TeamLeader {
	
	private String team;
	CommMaster cm = null;
	
	public TeamLeader(String t) {
		this.team = t;
		
		ProcessBuilder processBuilder = new ProcessBuilder("python", "python/test.py");
      Process process = null;
      try {
          process = processBuilder.start();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }

      cm = new CommMaster(process.getInputStream(),
              process.getOutputStream(),
              process.getErrorStream());

      try {
          ArrayList<String> answer = cm.getAnswer(String.format("{" +
                          "\"player_index\": %d, " +
                          "\"board_size\": [%d, %d], " +
                          "\"n_to_connect\": %d}",
                  playerIndex, boardSize[0], boardSize[1], nToConnect));
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
	}
	
	public String getTeam() {
		return this.team;
	}
	
	//TODO: move fuggvenyt kitalalni

}
