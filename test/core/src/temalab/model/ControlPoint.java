package temalab.model;

import com.badlogic.gdx.graphics.Color;
import temalab.common.ControlPointListener;
import temalab.common.MainModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ControlPoint {
	private Position pos;
	private int size;
	private int percentage;
	private ControlPointListener listener;
	private Team controlTeam;
	private Team prevControlTeam;
	private int controlLenght;
	private MainModel mm;
	private final int id;
	private static int idCounter = 0;

	public ControlPoint(Position p, int percentage, int size, MainModel mm) {
		pos = p;
		this.size = size;
		this.percentage = percentage;
		this.mm = mm;
		this.id = idCounter++;
	}

	private Map<Team, Integer> countUnits(List<Unit> seenUnits) {
		var unitCount = new HashMap<Team, Integer>();
		for (var u : seenUnits) {
			if (unitCount.containsKey(u.team())) {
				var val = unitCount.get(u.team());
				unitCount.put(u.team(), val++);
			} else {
				unitCount.put(u.team(), 1);
			}
		}
		return unitCount;
	}

	private boolean defineControlTeam(Map<Team, Integer> unitCount) {
		boolean twoTeams = false;
		int max = Integer.MIN_VALUE;
		Set<java.util.Map.Entry<Team, Integer>> entries = unitCount.entrySet();
		for (Entry<Team, Integer> entry : entries) {
			if (entry.getValue() > max) {
				max = entry.getValue();
				controlTeam = entry.getKey();
				if (prevControlTeam == controlTeam) {
					controlLenght++;
				} else {
					prevControlTeam = controlTeam;
					controlLenght = 1;
				}
			}
			if (entry.getValue() == max && entry.getKey() != controlTeam) {
				twoTeams = true;
				prevControlTeam = null;
				controlLenght = 0;

			}
		}
		return twoTeams;
	}

	private void healUnits(boolean twoTeams, List<Unit> seenUnits) {
		if (!twoTeams && controlTeam != null) {
			if (listener != null) {
				listener.onColorChange(controlTeam.getColor());
			} //színállítás
			for (var u : seenUnits) {
				if (u.team() == controlTeam) {
					u.updateSelf(percentage);
				}
			}
		}
	}

	public void updateNearbyUnits() {
		boolean twoContolrTeams = false;
		var seenUnits = mm.requestUnits(pos, size + 0.5f);
		if (!seenUnits.isEmpty()) {
			var unitCount = countUnits(seenUnits);
			//eddig csak megszámoltuk, hogy melyik csapathoz hány egység tartozik

			twoContolrTeams = defineControlTeam(unitCount);
			// megnézzük, hogy melyik csapathoz tartozik a ControlPoint

			healUnits(twoContolrTeams, seenUnits);
			//controlTeam healelése
		} else {
			if (listener != null) {
				listener.onColorChange(Color.CYAN);
				controlTeam = null;
			}
		}
	}

	public int getControlLenght() {
		return controlLenght;
	}

	public Position pos() {
		return pos;
	}

	public int size() {
		return size;
	}

	public int getId() {
		return id;
	}

	public void registerListener(ControlPointListener cpl) {
		listener = cpl;
	}

	public String toString() {
		return this.pos.toString() + " " + this.percentage + " " + this.size + " " + this.id;
	}
}