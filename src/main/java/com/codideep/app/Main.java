/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codideep.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author KAAF0
 */
public class Main {

    public static void main(String args[]) {
        int port = 7777;
        List<String> listMessage = new ArrayList<>();
        String message;
        String messageToSend;
        StringBuilder joinMessage;
        Socket clientSocket;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Socket server iniciado en el puerto " + port);

            while (true) {
                clientSocket = serverSocket.accept();

                message = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();

                if (message.substring(0, 15).equals("sendMessageXyz_")) {
                    listMessage.add(UUID.randomUUID().toString() + "__SEPID__" + message.split("sendMessageXyz_")[1]);
                } else {
                    joinMessage = new StringBuilder();

                    for (String element : listMessage) {
                        joinMessage.append(element).append("_MessageXYZMessage_");
                    }

                    messageToSend = joinMessage.toString();

                    if (!listMessage.isEmpty()) {
                        messageToSend = messageToSend.substring(0, messageToSend.length() - 19);
                    }

                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                    writer.println(messageToSend);
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
