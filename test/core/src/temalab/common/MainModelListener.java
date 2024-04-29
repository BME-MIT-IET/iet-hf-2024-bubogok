package temalab.common;

import temalab.model.*;

public interface MainModelListener {
    public void unitCreated(Unit u);
    public void unitDestoryed(Unit u);

    public void controlPointCreated(ControlPoint cp);
    public void controlPointDestoryed(ControlPoint cp);

    public void fieldCreated(Field f);
    public void fieldDestroyed(Field f);

    public void teamCreated(Team t);
    public void teamDestroyed(Team t);
}
