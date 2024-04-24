package temalab.communicator;

import java.util.ArrayList;
import java.util.List;

import temalab.common.MainModel;

public class MainCommunicator {
    private boolean manualResetEvent = true;
    private Thread commThread;
    private Object waiter = new Object();
    private boolean pause;
    Communicator TL1;
	Communicator TL2;
    private List<Communicator> communictors;

    public MainCommunicator(MainModel mm) {
        communictors = new ArrayList<>();
        communictors.add(new Communicator(mm.team("white"), "python/test1.py", "dummy"));
		communictors.add(new Communicator(mm.team("red"), "python/test1.py", "heuristic"));

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
                    for(var c : communictors) {
                        c.communicate();
                        mm.ControlPointsUpdate();
                        System.err.println("\033[0;35mdebug from " + "--------Egy kor lement--------" + "\033[0m");
                        if(manualResetEvent) {
                            pause = true;
                        }
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
