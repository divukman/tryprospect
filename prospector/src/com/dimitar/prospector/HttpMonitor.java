package com.dimitar.prospector;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class HttpMonitor {

    //https://stackoverflow.com/questions/355089/difference-between-stringbuilder-and-stringbuffer
    //public static StringBuilder log = new StringBuilder();
    public static StringBuffer log = new StringBuffer();

    private static boolean isReachable(String host, int port) {
        // Ping the provided IP and port
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 1000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void check(String domain) {
        String ip = null;

        // Get the domain's IP so we can ping it
        try {
            ip = InetAddress.getByName(domain).getHostAddress();
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Invalid domain provided");
        }

        // Are HTTP and HTTPS reachable?
        boolean http = isReachable(domain, 80);
        boolean https = isReachable(domain, 443); //fixed


        // Lets have a local variable to hold the data before writing to the buffer
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%20s", domain));
        stringBuilder.append(String.format("%18s", ip));
        stringBuilder.append("\t\tHTTP: " + (http ? "UP" : "DOWN!"));
        stringBuilder.append(" | HTTPS: " + (https ? "UP\n" : "DOWN!\n"));


        // Log result to console
        log.append(stringBuilder);
    }

}