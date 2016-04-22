package com.example;

import org.apache.log4j.PropertyConfigurator;

public class Application {

    public static void main(String[] args) {
        if (0 < args.length) {
            String filename = args[0];
            Algorithm al = new Algorithm();
            al.runAlgorithm(filename);
        }else{
            System.out.println("Usage: java -jar target\\Dependency-jar-with-dependencies.jar dataFileName");
        }
    }
}
