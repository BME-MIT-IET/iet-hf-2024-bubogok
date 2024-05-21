package temalab.communicator;

import java.util.ArrayList;
import java.util.List;

import temalab.common.MainModel;
import temalab.common.MainModelCommunicatorListener;

public class MainCommunicator implements MainModelCommunicatorListener {
    private boolean manualResetEvent = false;
    private Thread commThread;
    private Object waiter = new Object();
    private boolean pause;
    private List<Communicator> communictors;
    private boolean runCommThread;
    private Thread shutdownHook;

    public MainCommunicator(MainModel mm) {
        communictors = new ArrayList<>();
        var teams = mm.getTeams();
        for(var t : teams) {
            communictors.add(new Communicator(t, "python/test1.py", t.getStrategy()));
        }
        runCommThread = true;
        commThread = new Thread() {
			public void run() {
				outer: while (runCommThread) {
                    synchronized (waiter) {
                        while (pause) {
                            try {
                                waiter.wait();
                            } catch (InterruptedException e) {
                                if (!runCommThread) {
                                    break outer;
                                } else {
                                    // ha más forrásból jött az interrupt,
                                    // akkor legyen valami nyoma
                                    e.printStackTrace();
                                }
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
		shutdownHook = new Thread(this::stop);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public void stop() {
        synchronized(shutdownHook) {
            if (!Runtime.getRuntime().removeShutdownHook(shutdownHook)) {
                return;
            }
        }
        runCommThread = false;
        commThread.interrupt();
        try {
            commThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(var c : communictors) {
            c.closeThread();
        }
    }

    public void setSteppability(Boolean b) {
        manualResetEvent = b;
    }

    public void change() {
        synchronized (waiter) {
            if (pause) {
                pause = false;
                waiter.notifyAll();
                System.out.println("RESUMED");
            } else {
                pause = true;
                System.out.println("PAUSED");
            }
        }
        System.out.println("paused = " + pause);
    }

    @Override
    public void teamLost(String name) {
        for (var c : communictors) {
            if (c.getTeam().getName() == name) {
                c.endSimu(false);
            } else {
                c.endSimu(true);
            }
        }
    }
}