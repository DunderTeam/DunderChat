package model.networking.server;

// Move thread owner of SocketListener and MessageGetter here...
public class Receiver {
    private final SocketListener socketlistener;
    private final MessageGetter messagegetter;
    private Thread socketThread;
    private Thread messageThread;

    public Receiver() {
        this.socketlistener = new SocketListener(5555);
        this.messagegetter = new MessageGetter(1);

        this.socketThread = (new Thread(socketlistener));
        this.messageThread = (new Thread(messagegetter));
    }

    public void start() {
        messageThread.start();
        socketThread.start();
    }
}
