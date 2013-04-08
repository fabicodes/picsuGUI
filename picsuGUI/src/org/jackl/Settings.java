/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jackl;

import gnu.io.SerialPort;
import org.jackl.gui.GUI;

/**
 *
 * @author Fabian
 */
public class Settings {

    public static final String vers = "<tr><td><b>Version:</b></td><td>0.985 Another Test Build</td></tr>";
    public static final String aboutMessage = "<html><h1>Picsu GUI</h1><table><tr><td><b>Created by:"
            + "</b></td><td>Fabian Jackl</td></tr><tr><td><b>Contact:</b></td><td>fabian@jackl.org</td>"
            + "</tr><tr><td><b>Website:</b></td><td><a href='http://fabian.jackl.org'>"
            + "http://fabian.jackl.org</a></td></tr>" + vers + "</table></html>";
    private static int refreshDelay = 100;
    private static int baudrate = 9600;
    private static int dataBits = SerialPort.DATABITS_8;
    private static int stopBits = SerialPort.STOPBITS_1;
    private static int parity = SerialPort.PARITY_ODD;
    private static int outputIndex = 1;
    private static int howManyValues = 10;
    private static boolean displayAverage = false;

    public static boolean displayAverage() {
        return displayAverage;
    }

    public static void displayAverage(boolean b) {
        Settings.displayAverage = b;
    }

    public static int getHowManyValues() {
        return howManyValues;
    }

    public static void setHowManyValues(GUI gui ,int howManyValues) {
        Settings.howManyValues = howManyValues;
        double[] lcv = new double[howManyValues];
        for (int i = 0; i < lcv.length; i++) {
            lcv[i] = 0;
        }
        gui.setLastCurrentValues(lcv);
    }

    public static int getOutputIndex() {
        return outputIndex;
    }

    public static void setOutputIndex(int outputIndex) {
        Settings.outputIndex = outputIndex;
    }

    public static int getParity() {
        return parity;
    }

    public static void setParity(int parity) {
        Settings.parity = parity;
    }

    public static int getStopBits() {
        return stopBits;
    }

    public static void setStopBits(int stopBits) {
        Settings.stopBits = stopBits;
    }

    public static int getDataBits() {
        return dataBits;
    }

    public static void setDataBits(int dataBits) {
        Settings.dataBits = dataBits;
    }

    public static int getBaudrate() {
        return baudrate;
    }

    public static void setBaudrate(int baudrate) {
        Settings.baudrate = baudrate;
    }

    public static int getRefreshDelay() {
        return refreshDelay;
    }

    public static void setRefreshDelay(int refreshDelay) {
        Settings.refreshDelay = refreshDelay;
    }
}
