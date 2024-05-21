package temalab.common;

import temalab.model.Position;

public interface UnitListener {
    public void onShoot(Position p);
    public void unitDied();
    public void unitReseted();
}
