package temalab.communicator;

import temalab.common.MainModel;

public class MainCommunicator {
    private boolean manualResetEvent = true;
    private Thread commThread;
    private Object waiter = new Object();
    private boolean pause;
    Communicator TL1;
	Communicator TL2;

    public MainCommunicator(MainModel mm) {
        TL1 = new Communicator(mm.t1(), "python/test1.py", "dummy");
		TL2 = new Communicator(mm.t2(), "python/test1.py", "heuristic");

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
                    mm.t1().refillActionPoints();
                    TL1.communicate();
                    mm.ControlPointsUpdate();
        
                    mm.t2().refillActionPoints();
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
