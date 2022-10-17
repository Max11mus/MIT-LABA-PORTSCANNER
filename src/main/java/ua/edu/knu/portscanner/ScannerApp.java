package ua.edu.knu.portscanner;

import java.io.IOException;

public class ScannerApp {

    public static void main(String[] args) throws IOException {
        PortScanner portScanner = new PortScanner();
        String host = "maxcloud.sytes.net";
        int startPort = 1;
        int endPort = 10_000;
        int singlePort = 22;

        long startTimeMillis, elapsedTimeMillis;

        startTimeMillis = System.currentTimeMillis();
        System.out.println();
        System.out.println("Scaning host " + host + " - single port " + singlePort);
        System.out.println(portScanner.runPortScan(host, singlePort, singlePort));

        elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
        System.out.println("Elapsed " + ((double) elapsedTimeMillis / 1000) + " seconds.");

        startTimeMillis = System.currentTimeMillis();
        System.out.println();
        System.out.println("Scaning host " + host + " - range  " + startPort + " - " + endPort);
        System.out.println(portScanner.runPortScan(host, startPort, endPort));

        elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
        System.out.println("Elapsed " + ((double) elapsedTimeMillis / 1000) + " seconds.");

    }
}
