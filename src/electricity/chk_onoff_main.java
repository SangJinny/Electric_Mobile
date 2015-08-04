package electricity;

import java.util.ArrayList;

import com.example.ui.R;
import com.example.ui.R.id;
import com.example.ui.R.layout;

import electricity.onoff_adapter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class chk_onoff_main extends Activity {
	
	ArrayList<chk_onoff_list> list;
	public static onoff_adapter adapter;
	public static ListView listview;
	Custom_Handler CH;
	Packet send_packet;
	Elec_Client elec_thread;
	Packet[] recv_packet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onoff_main);
		
		String[] msg = { "0", "", "", "", "", "", "", "" };
		CH = new Custom_Handler(2);
		send_packet = new Packet();
		send_packet.setType(1);
		send_packet.setData(msg);
		Log.d("Test", "write on send_packet");
		

		elec_thread = new Elec_Client(CH, send_packet);

		try {
			elec_thread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		
		
		
	}
	
	public void onStart() {
		super.onStart();
		
		try {
			elec_thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int k = CH.getNum_of_msg();// 보여줄 데이터 개수
		Log.d("aaaaa", "k값은 " + k);
		// 패킷을 우선 수신하여서 recv_packet배열에 저장
		recv_packet = new Packet[k];
		
		for (int i = 0; i < k; i++) {
			recv_packet[i] = CH.get_Packet(i);
		}
		Log.d("on_off", "recv_packet배열 저장");
		
		list = new ArrayList<chk_onoff_list>();
		Log.d("on_off", "before for");
		for(int i = 0 ; i < k ; i++) {
			String temp = recv_packet[i].getData(2);
			Log.d("on_off", "inside for " + temp);
			list.add(new chk_onoff_list(recv_packet[i].getData(2), recv_packet[i].get_bool()));
		}
		listview = (ListView)findViewById(R.id.listView1);
		Log.d("on_off", "under listview");
		adapter = new onoff_adapter(chk_onoff_main.this, R.layout.chk_onoff, list);
		Log.d("on_off", "under adapter");
		listview.setAdapter(adapter);
		Log.d("on_off", "under set adapter");
	}
}
