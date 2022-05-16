import commands.CommandAbstract;
import commands.CommandParser;
import util.CommandManager;
import util.Reply;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class ClientUDP {
    /* Порт сервера, к которому собирается
  подключиться клиентский сокет */
    public final static int SERVICE_PORT = 50002;
    private static final ByteBuffer outputBuffer = ByteBuffer.allocate(16384);
    private static final ByteBuffer inputBuffer = ByteBuffer.allocate(16384);

    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVICE_PORT);
            CommandParser commandParser = new CommandParser();
            CommandManager commandManager = new CommandManager();
            datagramSocket.setSoTimeout(1000);
            boolean power = true;
            while (power) {
                CommandAbstract command = (CommandAbstract) commandParser.parseCommand(commandManager, new Scanner(System.in));
                if (command == null) {
                    continue;
                }

                DatagramPacket outPacket = makeOutPacket(command, inetSocketAddress);
                datagramSocket.send(outPacket);
                DatagramPacket inputPacket = makeInputPacket(inetSocketAddress);
                try {
                    datagramSocket.receive(inputPacket);
                } catch (SocketTimeoutException ex) {
                    System.err.println("Пакет утеренн!\nПовторите ввод...");
                    continue;
                }
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                Reply reply = (Reply) objectInputStream.readObject();
                System.out.println(reply);

            }


        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }


    private static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
    private static DatagramPacket makeOutPacket(Object data, InetSocketAddress inetSocketAddress) throws IOException{
        ((Buffer) outputBuffer).clear();
        outputBuffer.put(serialize(data));
        ((Buffer)outputBuffer).flip();

        return new DatagramPacket(outputBuffer.array(), outputBuffer.limit(), inetSocketAddress);

    }
    private static DatagramPacket makeInputPacket(InetSocketAddress inetSocketAddress) {
        ((Buffer)inputBuffer).clear();
        return new DatagramPacket(inputBuffer.array(), inputBuffer.limit(), inetSocketAddress);
    }
}
