package kg.kloop.flaschentaschentest1;

import android.graphics.Color;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by User on 22.04.2016.
 */
public class PicSender extends Object {

    public final static String VALUES_PER_COLOR = "255";
    public final static String MAGIC_NUMBER = "P6";

    private String serverAddress = "";
    private int serverPort = 0;


    public PicSender(String serverAddress, int serverPort){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void sendPix (int red, int green, int blue, int offsetX, int offsetY, int offsetZ){
        superFill(red, green, blue, 1, 1, offsetX, offsetY, offsetZ);
    }

    public void superFill(int red, int green, int blue, int width, int height, int offsetX, int offsetY, int offsetZ){
        byte[] result = new byte[width * height * 3];
        for(int i = 0; i < result.length; i++){
            if ( (i % 3) == 0) result[i] = intToByte(red)[3];
            else if ( (i % 3) == 1) result[i] = intToByte(green)[3];
            else result[i] = intToByte(blue)[3];
        }
        result = addByteArrays(prepareHeader(width, height), result);
        result = addByteArrays(result, prepareFooter(offsetX, offsetY, offsetZ));

        final byte[] result1 = result;

        new Thread(new Runnable() {
            public void run() {
                sendData(result1);
            }
        }).start();

    }

    private byte[] prepareHeader(int width, int height) {
        byte[] result = null;
        String header = MAGIC_NUMBER + "\n" + Integer.toString(width) + " " + Integer.toString(height) + "\n" + VALUES_PER_COLOR + "\n";
        result = header.getBytes();
        return result;
    }

    private byte[] prepareFooter(int offsetX, int offsetY, int offsetZ) {
        byte[] result = null;
        String footer = "\n" + Integer.toString(offsetX) +
                        "\n" + Integer.toString(offsetY) +
                        "\n" + Integer.toString(offsetZ);
        result = footer.getBytes();
        return result;
    }

    private void sendData (byte[] data) {

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        InetAddress local = null;
        try {
            local = InetAddress.getByName(serverAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int msgLength = data.length;

        DatagramPacket packet = new DatagramPacket(data, msgLength, local, serverPort);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] addByte (byte[] a, byte b){
        byte [] c= new byte[a.length+1];
        for(int i=0;i<a.length;i++) c[i]=a[i];
        c[a.length]=b;
        return c;
    }

    private byte[] addByteArrays (byte[] a, byte[] b){
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    private byte[] intToByte(int a){
        byte[] bytes;
        return bytes = ByteBuffer.allocate(4).putInt(a).array();
    }

}
