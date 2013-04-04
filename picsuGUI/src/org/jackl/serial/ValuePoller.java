/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jackl.serial;

import org.jackl.Settings;

/**
 *
 * @author Fabian
 */
public class ValuePoller extends Thread {

    private boolean finished = false;
    private boolean pause = false;
    private SerialCommunicator serial;

    public ValuePoller(SerialCommunicator serial) {
        this.serial = serial;
    }

    public void terminate() {
        finished = true;
    }

    public void pause(boolean enable) {
        pause = enable;
    }

    @Override
    public void run() {
        System.out.println("Thread started");
        while (!finished) {
            while (pause) {
            }
            serial.send("[i]\r");
            try {
                Thread.sleep(Settings.getRefreshDelay());
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
        System.out.println("Thread finished");
    }
}
