package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DailyAdviceServer {

    String[] adviceList = {"Take smaller bites", "Go for the tight jeans",
            "Yes, they do make you look fat", "One word: inappropriate",
            "Just for today, be honest and tell your boss what you actually feel about his manners.",
            "You might really want to rethink that haircut."};

    public void go() throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(6500);

            while (true) {
                Socket sock = serverSocket.accept();

                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                System.out.println("Something");
                writer.close();
                System.out.println(advice);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String getAdvice () {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }

    public static void main (String[] args) throws IOException {

    }

}
