/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jackl.serial;

import org.jackl.gui.*;
import gnu.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.TooManyListenersException;
import javax.swing.JOptionPane;
import org.jackl.Settings;

/**
 *
 * @author Fabian
 */
public class SerialCommunicator {

    private CommPortIdentifier serialPortId;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Enumeration ports;
    private SerialPort serialPort;
    private ValuePoller poller;
    private boolean connected;
    private String lastSent;
    private GUI gui;

    public SerialCommunicator(GUI gui) {
        this.gui = gui;
        poller = new ValuePoller(this);
        poller.start();
        poller.pause(true);
    }

    public boolean openSerialPort(String portName) {
        Boolean foundPort = false;
        if (connected != false) {
            System.out.println("There is already a connection established");
            JOptionPane.showMessageDialog(null, "There is already a connection established", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        System.out.println("Opening Serial Port");
        ports = CommPortIdentifier.getPortIdentifiers();
        while (ports.hasMoreElements()) {
            serialPortId = (CommPortIdentifier) ports.nextElement();
            if (portName.contentEquals(serialPortId.getName())) {
                foundPort = true;
                break;
            }
        }
        if (foundPort != true) {
            System.out.println("Port not found: " + portName);
            JOptionPane.showMessageDialog(null, "Port not found: " + portName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            serialPort = (SerialPort) serialPortId.open("Open and send", 500);
        } catch (PortInUseException e) {
            System.out.println("Port in use");
            JOptionPane.showMessageDialog(null, "Port in use", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        try {
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
            System.out.println("No access to OutputStream");
            JOptionPane.showMessageDialog(null, "No access to OutputStream", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            System.out.println("No access to InputStream");
            JOptionPane.showMessageDialog(null, "No access to InputStream", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        try {
            serialPort.addEventListener(new serialPortEventListener());
        } catch (TooManyListenersException e) {
            System.out.println("TooManyListenersException");
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(Settings.getBaudrate(), Settings.getDataBits(), Settings.getStopBits(), Settings.getParity());
        } catch (UnsupportedCommOperationException e) {
            System.out.println("Couldn't set SerialPort Parameter");
            JOptionPane.showMessageDialog(null, "Couldn't set SerialPort Parameter", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        connected = true;
        poller.pause(false);
        return true;
    }

    public void closeSerialPort() {
        if (connected == true) {
            System.out.println("Closing Serial Port");
            poller.pause(true);
            serialPort.close();
            connected = false;
        } else {
            System.out.println("Serial Port already closed");
        }
    }

    public LinkedList<String> getSerialPorts() {
        System.out.println("Refreshing available Serial Ports");
        LinkedList<String> out = new LinkedList();
        if (connected != false) {
            System.out.println("Serial Port already opened");
            return null;
        }
        ports = CommPortIdentifier.getPortIdentifiers();
        while (ports.hasMoreElements()) {
            serialPortId = (CommPortIdentifier) ports.nextElement();
            if (serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println("Found: " + serialPortId.getName());
                out.add(serialPortId.getName());
            }
        }
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }

    public void send(String msg) {
        System.out.println("Sending: " + msg);
        if (connected != true) {
            return;
        }
        try {
            outputStream.write(msg.getBytes());
            lastSent = msg;
        } catch (IOException e) {
            System.out.println("Error while sending");
        }
    }

    public boolean isConnected() {
        return connected;
    }

    private void serialPortDataAvailable() {
        try {
            byte[] buffer = new byte[1024];
            int data;
            int len = 0;
            while ((data = inputStream.read()) > -1) {
                if (data == '\r') {
                    break;
                }
                buffer[len++] = (byte) data;
            }
            System.out.println("Received: " + new String(buffer, 0, len).trim());
            parseInput(new String(buffer, 0, len).trim());
        } catch (IOException e) {
            System.out.println("Error reading received Data");
        }
    }

    private void parseInput(String txt) {
        if (txt.equals("(ack)")) {
        } else if (txt.equals("(err)")) {
            System.out.println("There was something wrong with this Command: " + lastSent);
        } else if (txt.startsWith("(") && txt.length() == 16) {
            System.out.println("Status Response");
            parseStatusResponse(txt);
        } else {
            System.out.println("Cant be sorted out: " + txt);
        }
    }

    private void parseStatusResponse(String txt) {
        txt = txt.trim();
        if (Integer.parseInt(txt.substring(1, 2)) == 1) // Check if Output Index == 1
        {
            gui.setOutput(txt.substring(3, 4).equals("1"));
            gui.setVoltage(Double.parseDouble(txt.substring(5, 10)));
            gui.setCurrent(Double.parseDouble(txt.substring(11, 15)));
        }
    }

    private class serialPortEventListener implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            switch (event.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE:
                    serialPortDataAvailable();
                    break;
                case SerialPortEvent.BI:
                case SerialPortEvent.CD:
                case SerialPortEvent.CTS:
                case SerialPortEvent.DSR:
                case SerialPortEvent.FE:
                case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                case SerialPortEvent.PE:
                case SerialPortEvent.RI:
                default:
            }
        }
    }
}
