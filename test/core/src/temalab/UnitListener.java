package temalab;

import temalab.model.Position;

public interface UnitListener {
    public void onShoot(Position p);
    public void unitDied();
}
