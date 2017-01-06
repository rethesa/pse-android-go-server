package com.goapp.client;

/**
 * When activated, the GoStatus keeps requesting updates of the target group.
 * Also it will send GPS-Broadcasts in certain intervals.
 * @author tarek
 *
 */
public class GoStatus implements Runnable {
	private boolean status;
	private GroupClient targetGroup;
	
	public GoStatus(GroupClient targetGroup) {
		this.targetGroup = targetGroup;
	}

	/**
	 * Begin broadcasting GPS-Data and requesting updates.
	 */
	public void activate() {
		status = true;
		run();
	}
	
	/**
	 * Stop sending GPS-Data and requesting updates for this group.
	 */
	public void deactivate() {
		status = false;
	}
	
	@Override
	public void run() {
		while(status) {
		// TODO keep the thread busy by sending requests to the server.
			// targetGroup.broadcastGps();
			// take GPS-data from response and draw on the map
			
			// Sleep(5); TBD...
		}
	}

}
