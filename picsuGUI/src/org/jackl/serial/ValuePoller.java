/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jackl.serial;

/**
 *
 * @author Fabian
 */
public class ValuePoller extends Thread {

    private boolean finished = false;

    public ValuePoller() {
    }

    public void terminate() {
        finished = true;
    }

    @Override
    public void run() {
        System.out.println("Thread started");
        while (!finished) {
            System.out.println("Thread running");
        }
        System.out.println("Thread finished");
    }
}
