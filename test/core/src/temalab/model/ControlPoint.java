package temalab.model;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Color;

import temalab.common.ControlPointListener;
import temalab.common.MainModel;

public class ControlPoint {
    //TODO: pos helyett field kellene
    private Position pos;
    private int size;
    private int percentage;
    private ControlPointListener listener;
    private Team controlTeam;
    private Team prevControlTeam;
    private int controlLenght;
    private MainModel mm;

    public ControlPoint(Position p, int percentage, int size, MainModel mm) {
        pos = p;
        this.size = size;
        this.percentage = percentage;
        this.mm = mm;
    }

    public void updateNearbyUnits() {
        boolean twoTeams = false;
        var seenUnits = mm.requestUnits(pos, size + 0.5f);
        if (seenUnits.size() != 0) {
            var unitCount = new HashMap<Team, Integer>();
            for (var u : seenUnits) {
                if (unitCount.containsKey(u.team())) {
                    var val = unitCount.get(u.team());
                    unitCount.put(u.team(), val++);
                } else {
                    unitCount.put(u.team(), 1);
                }
            } //eddig csak megszámoltuk, hogy melyik csapathoz hány egység tartozik

            int max = Integer.MIN_VALUE;
            Set<java.util.Map.Entry<Team, Integer>> entries = unitCount.entrySet();
            for (Entry<Team, Integer> entry : entries) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    controlTeam = entry.getKey();
                    if(prevControlTeam == controlTeam) {
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
            } // megnézzük, hogy melyik csapathoz tartozik a ControlPoint

            if (!twoTeams && controlTeam != null) {
                if(listener != null) {
                    listener.onColorChange(controlTeam.getColor());
                } //színállítás
                for (var u : seenUnits) {
                    if (u.team() == controlTeam) {
                        u.updateSelf(percentage);
                    }
                } //controlTeam healelése
            }
        } else {
            if(listener != null) {
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

    public void registerListener(ControlPointListener cpl) {
		listener = cpl;
	}

    public String toString() {
		return this.pos.toString() + " " + this.percentage + " " + this.size;
	}
}