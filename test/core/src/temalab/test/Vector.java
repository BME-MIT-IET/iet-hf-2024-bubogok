package temalab.test;

import java.io.Serializable;


public class Vector implements Serializable{
	private static final long serialVersionUID = -9084081602801636546L;
	public final float x;
	public final float y;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean nearlyEquals(Vector other) {
		
		return true;
	}
}
