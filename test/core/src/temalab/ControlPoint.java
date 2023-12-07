package temalab;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

public class ControlPoint {
    private Position pos;
    private int size;
    private int percentage;
    private ControlPointListener listener;

    public ControlPoint(Position p, int percentage, int size) {
        pos = p;
        this.size = size;
        this.percentage = percentage;
    }

    public void updateNearbyUnits() {
        boolean twoTeams = false;
        var seenUnits = Map.instance().requestUnits(pos, size + 0.5f);
        if (seenUnits.size() != 0) {
            var unitCount = new HashMap<Team, Integer>();
            for (var u : seenUnits) {
                if (unitCount.containsKey(u.team())) {
                    var val = unitCount.get(u.team());
                    unitCount.put(u.team(), val++);
                } else {
                    unitCount.put(u.team(), 1);
                }
            }
            int max = Integer.MIN_VALUE;
            Team maxTeam = null;
            Set<java.util.Map.Entry<Team, Integer>> entries = unitCount.entrySet();
            for (Entry<Team, Integer> entry : entries) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    maxTeam = entry.getKey();
                }
                if (entry.getValue() == max && entry.getKey() != maxTeam) {
                    twoTeams = true;
                }
            }
            if (!twoTeams && maxTeam != null) {
                listener.onColorChange(maxTeam.getColor());
                for (var u : seenUnits) {
                    if (u.team() == maxTeam) {
                        u.updateSelf(percentage);
                    }
                }
            }
        }
    }

    public Position pos() {
        return pos;
    }

    public int size() {
        return size;
    }

    public void registerListener(ControlPointListener cpl) {
		listener = cpl;
	}
}