package temalab.communicator;

import temalab.common.MainModel;
import temalab.model.Map;

public class MainCommunicator {
    private boolean manualResetEvent = true;
    private Thread commThread;
    private Object waiter = new Object();
    private boolean pause;
    Communicator TL1;
	Communicator TL2;

    public MainCommunicator(MainModel m) {
        TL1 = new Communicator(m.t1(), "python/test1.py", "dummy");
		TL2 = new Communicator(m.t2(), "python/test1.py", "heuristic");

        commThread = new Thread() {
			public void run() {
				while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (waiter) {
                        while (pause) {
                            try {
                                waiter.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    m.t1().refillActionPoints();
                    TL1.communicate();
                    Map.instance().ControlPointsUpdate();
        
                    m.t2().refillActionPoints();
                    TL2.communicate();
                    System.err.println("\033[0;35mdebug from " + "--------Egy kor lement--------" + "\033[0m");
                    if(manualResetEvent) {
                        pause = true;
                    }
                }
			}
		};
		commThread.start();
    }

    public void stop() {
        TL1.closeThread();
		TL2.closeThread();
    }
}
