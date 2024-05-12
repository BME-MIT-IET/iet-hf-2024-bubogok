package temalab.communicator;

import java.util.ArrayList;
import java.util.List;

import temalab.common.MainModel;

public class MainCommunicator{
    private boolean manualResetEvent = false;
    private Thread commThread;
    private Object waiter = new Object();
    private boolean pause;
    private List<Communicator> communictors;

    public MainCommunicator(MainModel mm) {
        communictors = new ArrayList<>();
        var teams = mm.getTeams();
        for(var t : teams) {
            communictors.add(new Communicator(t, "python/test1.py", t.getStrategy()));
        }
        commThread = new Thread() {
			public void run() {
				while (true) {
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
                        try {
                            c.communicate();
                        } catch (RuntimeException e) {
                            mm.winEvent(c.getTeam().getName());
                        }
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
}