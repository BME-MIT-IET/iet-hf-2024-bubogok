package temalab.communicator;

import temalab.common.MainModel;
import temalab.common.MainModelCommunicatorListener;
import temalab.logger.Log;

import java.util.ArrayList;
import java.util.List;

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
		for (var t : teams) {
			communictors.add(new Communicator(t, "python/test1.py", t.getStrategy()));
		}
		runCommThread = true;
		commThreadStart(mm);
		shutdownHook = new Thread(this::stop);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}

	private void commThreadStart(MainModel mm) {
		commThread = new Thread(() -> {
			while (runCommThread) {
				synchronized (waiter) {
					while (pause) {
						try {
							waiter.wait();
						} catch (InterruptedException e) {
							if (!runCommThread) {
								return;
							} else {
								e.printStackTrace();
							}
						}
					}
				}
				communicate(mm);
			}
		});
		commThread.start();
	}

	private void communicate(MainModel mm) {
		for (var c : communictors) {
			c.communicate();
			mm.controlPointsUpdate();
			Log.d("Communication", "--------Egy kor lement--------");
			if (manualResetEvent) {
				pause = true;
			}
		}
	}

	public void stop() {
		synchronized (shutdownHook) {
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
		for (var c : communictors) {
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
				Log.d("Communication", "RESUMED");
			} else {
				pause = true;
				System.out.println("PAUSED");
				Log.d("Communication", "PAUSED");
			}
		}
		Log.d("Communication", "paused = " + pause);
	}

	@Override
	public void teamLost(String name) {
		for (var c : communictors) {
			if (c.getTeam().getName().equals(name)) {
				c.endSimu(false);
			} else {
				c.endSimu(true);
			}
		}
	}
}