package ua.edu.knu.portscanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PortScanner {
    private static final int poolSize = 10;
    private static final int timeOut = 200;

    public String runPortScan(String ip, int startPort, int endPort) throws IOException {
        StringBuilder result = new StringBuilder();
        final String EOF = System.lineSeparator();

        ConcurrentLinkedQueue openPorts = new ConcurrentLinkedQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        AtomicInteger port = new AtomicInteger(startPort);
        while (port.get() <=  endPort) {
            final int currentPort = port.getAndIncrement();
            executorService.submit(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, currentPort), timeOut);
                    socket.close();
                    openPorts.add(currentPort);
                } catch (IOException e) {
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List openPortList = new ArrayList<>();
        while (!openPorts.isEmpty()) {
            openPortList.add(openPorts.poll());
        }

        openPortList.forEach(p -> result.append("port " + p + " is open" + EOF));

        return result.toString();
    }
}

