package temalab.gui.view;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import temalab.model.Team;

public class TeamView {
    private Team team;

    public TeamView(Team t) {
        this.team = t;
    }

    public void render(SpriteBatch batch, BitmapFont font, int x, int inity) {
        batch.begin();
        int y = inity;
        ArrayList<String> teamInfo = team.teamMembersToString(true);
        for(var s : teamInfo) {
            font.draw(batch, s, x, y);
            y -= 120;
        }
        batch.end();
    }
}
