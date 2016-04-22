package com.example;

import org.apache.log4j.PropertyConfigurator;

public class Application {

    public static void main(String[] args) {
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        if (0 < args.length) {
            String filename = args[0];
            Algorithm al = new Algorithm();
            al.runAlgorithm(filename);
        }

    }

}
