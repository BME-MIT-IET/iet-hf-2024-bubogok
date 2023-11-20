package temalab;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class ControlPoint {
    private Position pos;
    private int size;
    private Vector2 center;
    private Color c;
    private int percentage;

    public ControlPoint(Position p, int percentage) {
        pos = p;
        size = 2;
        center = pos.screenCoords();
        c = new Color(Color.CYAN);
        this.percentage = percentage;
    }

    public void render(ShapeRenderer sr, SpriteBatch sb, BitmapFont bf) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(c);
        float sqareSize = Map.instance().squareSize() * (size + 1.5f);
        sr.circle(center.x, center.y, sqareSize);
        sr.end();
    }

    public void updateNearbyUnits() {
        boolean twoTeams = false;
        var seenUnits = Map.instance().requestUnits(pos, size);
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
                if (entry.getValue() == max) {
                    twoTeams = true;
                }
            }
            if (!twoTeams) {
                this.c = maxTeam.getColor();
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
}
