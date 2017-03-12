package edu.kit.pse.bdhkw.client.model.objectStructure;


/**
 * When activated, the GoService keeps requesting updates of the target group.
 * Also it will send GPS-Broadcasts in certain intervals.
 * @author tarek
 */

public class GoService /*implements Runnable*/ {

    private boolean goStatus;
    private GroupClient targetGroupClient;

    public GoService(GroupClient targetGroupClient) {
        this.targetGroupClient = targetGroupClient;
        goStatus = false;
    }

    /**
     * update gps coords of groupmembers every 5 seconds.
     */
    /*@Override
    public void run() {
        while(goStatus) {
            // TODO keep the thread busy by sending requests to the server.
            // targetGroupClient.broadcastGps();
            // take GPS-data from response and draw on the map

            // Sleep(5); TBD...
        }
    }*/

    /**
     * Begin broadcasting GPS-Data and resting updates when user presses his go button.
     */
    public void activateGoStatus(){
        goStatus = true;
        //run();
    }

    /**
     * Stop sending GPS-Data and requesting updates for this group.
     */
    public void deactivateGoStatus() {
        goStatus = false;
    }


    public boolean getGoStatus() {
        return goStatus;
    }
}


