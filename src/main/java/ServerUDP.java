import collection.JsonCollectionParser;
import commands.CommandAbstract;
import commands.CommandParser;
import exception.CommandException;
import util.Manager;
import util.Reply;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Logger;

public class ServerUDP {
    // Серверный UDP-сокет запущен на этом порту
    public final static int SERVICE_PORT = 50002;
    private static final Manager manager = new Manager();
    private static final CommandParser parser = new CommandParser();
    private static final Scanner scanner = new Scanner(System.in);
    private static final ByteBuffer outputBuffer = ByteBuffer.allocate(16384);
    private static final ByteBuffer inputBuffer = ByteBuffer.allocate(16384);
    private static final Logger log = Logger.getLogger(ServerUDP.class.getName());
    private static boolean power = true;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        log.info("ServerUDP on");
        Manager manager = new Manager();
        JsonCollectionParser jsonCollectionParser = new JsonCollectionParser();
        jsonCollectionParser.parseJsonToSet(manager.getCollectionManager());
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVICE_PORT);
        datagramChannel.bind(inetSocketAddress);
        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ, SelectionKey.OP_WRITE);
        CommandAbstract command = null;
        SocketAddress address = null;
        while (power) {
            selector.select(500);
            try {
                String inputStr = checkConsoleInput();
                if (inputStr != null && inputStr.equals("save")) {
                    System.out.println(manager.getCommandManager().getCommandMap().get("save").execute(manager));

                }
            } catch (IOException | CommandException ex) {
                log.warning(ex.getMessage());
            }

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {

                SelectionKey sk = it.next();
                it.remove();
                if (sk.isReadable()) {
                    ((Buffer)inputBuffer).clear();
                    address = datagramChannel.receive(inputBuffer);
                    ((Buffer) inputBuffer).flip();
                    log.info("package hes been received");
                    command = read();
                    assert command != null;
                    sk.interestOps(SelectionKey.OP_WRITE);
                } else if (sk.isWritable()) {
                    Reply reply = parser.executeCommand(manager, command);
                    fillOutBuffer(reply);
                    datagramChannel.send(outputBuffer, address);
                    log.info("package has been sent");
                    sk.interestOps(SelectionKey.OP_READ);
                }
            }
        }

            Thread haltedHook = new Thread(() -> {
                try {
                    manager.getCommandManager().getCommandMap().get("save").execute(manager);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            });
            Runtime.getRuntime().addShutdownHook(haltedHook);

    }
    private static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
    private static CommandAbstract read() {
        try {

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inputBuffer.array()));
            CommandAbstract command = (CommandAbstract) objectInputStream.readObject();
            return command;

        } catch (IOException | ClassNotFoundException ex) {
            log.warning(ex.getMessage());
            return null;
        }
    }
    private static void fillOutBuffer(Reply reply) throws IOException {
        ((Buffer)outputBuffer).clear();
        outputBuffer.put(serialize(reply));
        ((Buffer)outputBuffer).flip();
    }
    private static String checkConsoleInput() throws  IOException{
        if (System.in.available() != 0) {
            return scanner.nextLine();
        }
        return null;
    }
}

