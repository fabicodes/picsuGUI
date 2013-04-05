/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jackl.gui;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jackl.Settings;
import org.jackl.serial.*;

/**
 *
 * @author Fabian
 */
public class GUI extends javax.swing.JFrame {

    private DecimalFormat f;
    private DecimalFormat s;
    private boolean voltageChanged;
    private SerialCommunicator serial;
    private double[] lastCurrentValues;

    public GUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex);
        }
        initAttributes();
        initComponents();
        Color[] colors = {Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
        float[] distrib = {0.5f, 0.75f, 0.825f, 1.0f};
        loadBar.setUI(new GradientPalletProgressBarUI(colors, distrib));
        refreshCOMPorts();
        enableComponents(false);
    }

    private void initAttributes() {
        DecimalFormatSymbols decimalSymbol = new DecimalFormatSymbols(Locale.getDefault());
        decimalSymbol.setDecimalSeparator('.');
        decimalSymbol.setGroupingSeparator(',');
        f = new DecimalFormat("#0.00", decimalSymbol);
        s = new DecimalFormat("#00.00", decimalSymbol);
        voltageChanged = true;
        serial = new SerialCommunicator(this);
        lastCurrentValues = new double[Settings.getHowManyValues()];
        for (int i = 0; i < lastCurrentValues.length; i++) {
            lastCurrentValues[i] = 0;
        }
    }

    private int setVoltageSlider(double volt) {
        if (volt >= 0 && volt <= 12) //Filter valid Values
        {
            int value = (int) (volt * 100) / 5;
            voltageSlider.setValue(value);
            voltageChanged = true;
            return value;
        }
        return -1;
    }

    public boolean hasVoltageChanged() {
        if (voltageChanged) {
            voltageChanged = false;
            return true;
        } else {
            return false;
        }
    }

    public void setVoltage(double volt) {
        if (volt >= 0 && volt <= 12) {
            if (!voltageSlider.isFocusOwner()) {
                setVoltageSlider(volt);
            }
            voltageTextField.setText(f.format(volt));
            voltageChanged = true;
        }
    }

    public double getVoltage() {
        if (voltageSlider.getValue() * 5 / 100 == Double.parseDouble(voltageTextField.getText())) {
            return voltageSlider.getValue() * 5 / 100;
        }
        return -1;
    }

    public void setCurrent(double current) {
        if (Settings.displayAverage()) {
            for (int i = lastCurrentValues.length - 1; i >= 1; i--) {
                lastCurrentValues[i] = lastCurrentValues[i - 1];
            }
            lastCurrentValues[0] = current;
            current = 0;
            for (double d : lastCurrentValues) {
                current += d;
            }
            current = current / lastCurrentValues.length;
        }
        currentTextField.setText(f.format(current));
        loadBar.setValue((int) (current * 100));
    }

    public void setOutput(boolean on) {
        if (on) {
            jTextField1.setBackground(Color.green);
        } else {
            jTextField1.setBackground(Color.red);
        }
    }

    private void refreshCOMPorts() {
        LinkedList<String> coms = serial.getSerialPorts();
        while (comButtonGroup.getElements().hasMoreElements()) {
            javax.swing.AbstractButton b = comButtonGroup.getElements().nextElement();
            comButtonGroup.remove(b);
            comSelectMenu.remove(b);
        }
        comButtonGroup.clearSelection();
        if (coms != null && !coms.isEmpty()) {
            for (String c : coms) {
                JRadioButtonMenuItem tmp = new JRadioButtonMenuItem(c);
                tmp.setActionCommand(c);
                comButtonGroup.add(tmp);
                comSelectMenu.add(tmp);
            }
        } else {
            JRadioButtonMenuItem tmp = new JRadioButtonMenuItem("No COM Port available");
            tmp.setActionCommand("null");
            comButtonGroup.add(tmp);
            comSelectMenu.add(tmp);
        }
    }

    private boolean connect() {
        if (comButtonGroup.getSelection() != null) {
            System.out.println(comButtonGroup.getSelection().getActionCommand());
            boolean out = serial.openSerialPort(comButtonGroup.getSelection().getActionCommand());
            return out;
        }
        JOptionPane.showMessageDialog(this, "Can't connect because no COM Port is selected", "ERROR", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    private void enableComponents(boolean enable) {
        onOffToggleButton.setEnabled(enable);
        voltageSlider.setEnabled(enable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comButtonGroup = new javax.swing.ButtonGroup();
        settingsDialog = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox();
        voltageTextField = new javax.swing.JTextField();
        currentTextField = new javax.swing.JTextField();
        aLabel = new javax.swing.JLabel();
        voltLabel = new javax.swing.JLabel();
        voltageLabel = new javax.swing.JLabel();
        currentLabel = new javax.swing.JLabel();
        voltageSlider = new javax.swing.JSlider();
        onOffToggleButton = new javax.swing.JToggleButton();
        jTextField1 = new javax.swing.JTextField();
        loadBar = new javax.swing.JProgressBar();
        menuBar = new javax.swing.JMenuBar();
        settingsMenu = new javax.swing.JMenu();
        connectMenuItem = new javax.swing.JMenuItem();
        comSelectMenu = new javax.swing.JMenu();
        refreshCOMPortsMenuItem = new javax.swing.JMenuItem();
        saveCOMPortMenuItem = new javax.swing.JMenuItem();
        comSelectSeparator = new javax.swing.JPopupMenu.Separator();
        jRadioButtonMenuItem4 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem5 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem6 = new javax.swing.JRadioButtonMenuItem();
        comSettingsSeparator = new javax.swing.JPopupMenu.Separator();
        averageCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        advancedSettingsMenuItem = new javax.swing.JMenuItem();
        aboutMenu = new javax.swing.JMenu();
        infoMenuItem = new javax.swing.JMenuItem();

        settingsDialog.setModal(true);

        jButton1.setText("Übernehmen");

        jButton2.setText("Abbrechen");

        jButton3.setText("OK");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("howManyValues");

        jLabel2.setText("baudrate");

        jLabel1.setText("refreshDelay");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("parity");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("outputIndex");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("dataBits");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("stopBits");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout settingsDialogLayout = new javax.swing.GroupLayout(settingsDialog.getContentPane());
        settingsDialog.getContentPane().setLayout(settingsDialogLayout);
        settingsDialogLayout.setHorizontalGroup(
            settingsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsDialogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        settingsDialogLayout.setVerticalGroup(
            settingsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(settingsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PICSU GUI");
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 0, 150));
        setMaximumSize(new java.awt.Dimension(2147483647, 150));
        setMinimumSize(new java.awt.Dimension(326, 150));

        voltageTextField.setEditable(false);
        voltageTextField.setBackground(new java.awt.Color(255, 255, 153));
        voltageTextField.setColumns(4);
        voltageTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        voltageTextField.setText("6.00");
        voltageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voltageTextFieldActionPerformed(evt);
            }
        });

        currentTextField.setEditable(false);
        currentTextField.setBackground(new java.awt.Color(255, 255, 153));
        currentTextField.setColumns(4);
        currentTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        currentTextField.setText("2.00");

        aLabel.setLabelFor(currentTextField);
        aLabel.setText("A");

        voltLabel.setLabelFor(voltageTextField);
        voltLabel.setText("V");

        voltageLabel.setLabelFor(voltageTextField);
        voltageLabel.setText("Voltage");

        currentLabel.setLabelFor(currentTextField);
        currentLabel.setText("Current");

        voltageSlider.setMajorTickSpacing(20);
        voltageSlider.setMaximum(240);
        voltageSlider.setMinimum(20);
        voltageSlider.setMinorTickSpacing(5);
        voltageSlider.setPaintTicks(true);
        voltageSlider.setToolTipText("");
        voltageSlider.setValue(120);
        voltageSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                voltageSliderStateChanged(evt);
            }
        });

        onOffToggleButton.setBackground(new java.awt.Color(255, 255, 255));
        onOffToggleButton.setText("Off");
        onOffToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOffToggleButtonActionPerformed(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 0, 0));
        jTextField1.setColumns(2);
        jTextField1.setToolTipText("");

        loadBar.setMaximum(200);
        loadBar.setOrientation(1);
        loadBar.setToolTipText("");
        loadBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        settingsMenu.setText("Settings");
        settingsMenu.setName(""); // NOI18N

        connectMenuItem.setText("Connect");
        connectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectMenuItemActionPerformed(evt);
            }
        });
        settingsMenu.add(connectMenuItem);

        comSelectMenu.setText("Select COM");

        refreshCOMPortsMenuItem.setText("Refresh Ports");
        refreshCOMPortsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshCOMPortsMenuItemActionPerformed(evt);
            }
        });
        comSelectMenu.add(refreshCOMPortsMenuItem);

        saveCOMPortMenuItem.setText("jMenuItem1");
        comSelectMenu.add(saveCOMPortMenuItem);
        comSelectMenu.add(comSelectSeparator);

        comButtonGroup.add(jRadioButtonMenuItem4);
        jRadioButtonMenuItem4.setSelected(true);
        jRadioButtonMenuItem4.setText("COM2808");
        jRadioButtonMenuItem4.setToolTipText("");
        jRadioButtonMenuItem4.setName(""); // NOI18N
        comSelectMenu.add(jRadioButtonMenuItem4);

        comButtonGroup.add(jRadioButtonMenuItem5);
        jRadioButtonMenuItem5.setText("COM0893");
        comSelectMenu.add(jRadioButtonMenuItem5);

        comButtonGroup.add(jRadioButtonMenuItem6);
        jRadioButtonMenuItem6.setText("COM1993");
        comSelectMenu.add(jRadioButtonMenuItem6);

        settingsMenu.add(comSelectMenu);
        settingsMenu.add(comSettingsSeparator);

        averageCheckBoxMenuItem.setText("Display Average");
        averageCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                averageCheckBoxMenuItemActionPerformed(evt);
            }
        });
        settingsMenu.add(averageCheckBoxMenuItem);

        advancedSettingsMenuItem.setText("Advanced Settings");
        advancedSettingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advancedSettingsMenuItemActionPerformed(evt);
            }
        });
        settingsMenu.add(advancedSettingsMenuItem);

        menuBar.add(settingsMenu);

        aboutMenu.setText("About");

        infoMenuItem.setText("Info");
        infoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoMenuItemActionPerformed(evt);
            }
        });
        aboutMenu.add(infoMenuItem);

        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(voltageSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(onOffToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(currentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(voltageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(voltageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aLabel)
                            .addComponent(voltLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loadBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(loadBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(voltageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(voltLabel)
                                    .addComponent(voltageLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(currentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(aLabel)
                                    .addComponent(currentLabel)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(onOffToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(voltageSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void voltageSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_voltageSliderStateChanged
        double value = (voltageSlider.getValue() * 5) / 100.;
        voltageTextField.setText(f.format(value));
        voltageChanged = true;
        if (!voltageSlider.getValueIsAdjusting()) {
            serial.send("[v:" + Settings.getOutputIndex() + ":" + s.format(value) + "]\r");
        }
    }//GEN-LAST:event_voltageSliderStateChanged

    private void onOffToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onOffToggleButtonActionPerformed
        if (onOffToggleButton.isSelected()) {
            onOffToggleButton.setText("On ");
            serial.send("[r:" + Settings.getOutputIndex() + "]\r");
        } else {
            onOffToggleButton.setText("Off");
            serial.send("[s:" + Settings.getOutputIndex() + "]\r");
        }
    }//GEN-LAST:event_onOffToggleButtonActionPerformed

    private void voltageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voltageTextFieldActionPerformed
        String text = voltageTextField.getText().trim().replace(",", "."); // Replace , with . to fix compatibility with EU Comma
        if (text.contains(".")) {
            if (text.indexOf(".") + 1 <= text.length() - 3) {
                text = text.substring(0, text.length() - 1); //Trim Text if it's too long (eg. 6.255)
            }
        }
        voltageTextField.setText(f.format(setVoltageSlider(Float.parseFloat(text)) * 0.05));
        voltageChanged = true;
    }//GEN-LAST:event_voltageTextFieldActionPerformed

    private void refreshCOMPortsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshCOMPortsMenuItemActionPerformed
        refreshCOMPorts();
    }//GEN-LAST:event_refreshCOMPortsMenuItemActionPerformed

    private void infoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, Settings.aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_infoMenuItemActionPerformed

    private void connectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectMenuItemActionPerformed
        if (!serial.isConnected()) {
            if (connect()) {
                connectMenuItem.setText("Disconnect");
                enableComponents(true);
                serial.send("*\r");
            }
        } else {
            serial.send("*\r");
            serial.closeSerialPort();
            connectMenuItem.setText("Connect");
            enableComponents(false);
        }
    }//GEN-LAST:event_connectMenuItemActionPerformed

    private void averageCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_averageCheckBoxMenuItemActionPerformed
        Settings.displayAverage(averageCheckBoxMenuItem.getState());
    }//GEN-LAST:event_averageCheckBoxMenuItemActionPerformed

    private void advancedSettingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_advancedSettingsMenuItemActionPerformed
        settingsDialog.setVisible(true);
    }//GEN-LAST:event_advancedSettingsMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aLabel;
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JMenuItem advancedSettingsMenuItem;
    private javax.swing.JCheckBoxMenuItem averageCheckBoxMenuItem;
    private javax.swing.ButtonGroup comButtonGroup;
    private javax.swing.JMenu comSelectMenu;
    private javax.swing.JPopupMenu.Separator comSelectSeparator;
    private javax.swing.JPopupMenu.Separator comSettingsSeparator;
    private javax.swing.JMenuItem connectMenuItem;
    private javax.swing.JLabel currentLabel;
    private javax.swing.JTextField currentTextField;
    private javax.swing.JMenuItem infoMenuItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem4;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem5;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JProgressBar loadBar;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JToggleButton onOffToggleButton;
    private javax.swing.JMenuItem refreshCOMPortsMenuItem;
    private javax.swing.JMenuItem saveCOMPortMenuItem;
    private javax.swing.JDialog settingsDialog;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JLabel voltLabel;
    private javax.swing.JLabel voltageLabel;
    private javax.swing.JSlider voltageSlider;
    private javax.swing.JTextField voltageTextField;
    // End of variables declaration//GEN-END:variables
}
