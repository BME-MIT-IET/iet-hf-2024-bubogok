package temalab.test;

import com.badlogic.gdx.graphics.Color;

public final class Team {
	private Color color;
	
	public Team(String name) {
		if(name == "green") {
			this.color = new Color(0, 1, 0, 1);
		} else if(name == "red") {
			this.color = new Color(1, 0, 0, 1);
		} else {
			this.color = new Color(.8f, .8f, .8f, 1);
		}
	}
	
	public Color getColor() {
		return this.color;
	}
}
