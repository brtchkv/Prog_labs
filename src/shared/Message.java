package shared;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;


public class Message {
    //Статусы:
    //false - запрос коллекции
    //true - приложена коллекция
    private final boolean STATUS;
    private final Vector<Human> list;
    public static final int PACKET_SIZE = 65535;

    public Message(@SuppressWarnings("SameParameterValue") boolean STATUS, Vector<Human> list) {
        this.STATUS = STATUS;
        this.list = list;
    }
    public boolean getSTATUS() {
        return STATUS;
    }

    public Vector<Human> getList() {
        return list;
    }

    public byte[] toBytes() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(PACKET_SIZE);
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        return baos.toByteArray();
    }
}
