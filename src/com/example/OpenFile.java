package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;


public class OpenFile {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    File file = null;

    public OpenFile(){
        prepareGUI();
    }

    private void prepareGUI(){
        mainFrame = new JFrame("Dependency Project");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2, dim.height / 2 - mainFrame.getSize().height/2);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("",JLabel.CENTER);

        statusLabel.setSize(350,100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);

    }

    public void fileChooser(){

        headerLabel.setText("Select the data file : ");

        final JFileChooser  fileDialog = new JFileChooser();
        fileDialog.setCurrentDirectory(new File("."));

        fileDialog.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".txt")
                        || f.isDirectory();
            }

            public String getDescription() {
                return "txt";
            }
        });

        JButton showFileDialogButton = new JButton("Open File");
        showFileDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileDialog.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileDialog.getSelectedFile();

                    mainFrame.setVisible(false);
                    mainFrame.dispose();
                    if(file.getName() != null) {
                        Algorithm al = new Algorithm();
                        al.runAlgorithm(file.getName());
                    }

                }
                else{
                    statusLabel.setText("Open command cancelled by user." );
                }
            }
        });
        controlPanel.add(showFileDialogButton);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }

}
