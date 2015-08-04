package electricity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

public class Elec_Client extends Thread{
	private DataOutputStream dos;
	private DataInputStream dis;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	//private boolean isReceived;
	private Packet send_packet;
	private Packet[] recv_packet;
	private Packet recv_num;
	private Custom_Handler handler;
	
	//private Message msg;
	private int num_of_msg;
	
	
	public Elec_Client(Custom_Handler handler, Packet packet) {
		this.send_packet = packet;
		this.handler = handler;		
		num_of_msg = 0;
	}

	public void run() {
		try {
			
			Log.d("Test", "before Socket Created");
			//Socket sock = new Socket("192.168.0.15", 10040);
			Socket sock = new Socket("202.30.10.6", 10040);
			Log.d("Test", "Socket Created");
			
			dos = new DataOutputStream(sock.getOutputStream());
			dis = new DataInputStream(sock.getInputStream());
			oos = new ObjectOutputStream(dos);
			ois = new ObjectInputStream(dis);
			Log.d("Test", "Stream Setting Completed");
			
			oos.writeObject(send_packet);
			Log.d("Test", "Send Completed");
			recv_num = (Packet)ois.readObject();
			//Log.d("test","receive data : "+ recv_num.getData(0));
			num_of_msg = Integer.parseInt(recv_num.getData(0));
            Log.d("Elec_Client", "이거냐"+num_of_msg);
			recv_packet = new Packet[num_of_msg];
			
			handler.setNum_of_msg(num_of_msg);
			
			for(int i = 0 ; i < num_of_msg ; i++) {
	            
	            recv_packet[i] = (Packet) ois.readObject();
	           // Log.d("test","receive data : "+ recv_packet[i].getData(1));
	            //msg = handler.obtainMessage();
	            //msg.obj = recv_packet[i];
	            //Boolean a = handler.sendMessage(msg);
	            handler.handleMessage(recv_packet[i]);

	            Log.d("Elec_Client", "Receive Completed");
	            
	         }
			//msg = handler.obtainMessage();
			//msg = handler.obtainMessage();
			//msg.obj = recv_packet;			
			//handler.sendMessage(msg);
//			SystemClock.sleep(100);
			Log.d("Test", "Handler Execute Completed");
			//핸들러의 메시지에 패킷(객체)을 실어서 보낸다.
			
			dos.close();
			dis.close();
			sock.close();
			Log.d("Test", "Close Complete");

		} catch (IOException e) {
			Log.d("Test", "qqqqqqqIOException");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			Log.d("Test", "ClassNotFoundException");
			
		}
	}
}
