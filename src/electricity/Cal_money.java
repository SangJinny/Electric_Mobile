package electricity;

import java.util.ArrayList;

import com.example.ui.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class Cal_money extends Activity {

	Custom_Handler CH;
	Packet send_packet;
	Elec_Client elec_thread;
	Packet[] recv_packet;
	ArrayList<cal_money_list> list;
	public static ListView listview;
//	public static money_adapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[] msg = { "0", "", "", "", "", "", "", "" };
		CH = new Custom_Handler(1);
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

		// setContentView(R.layout.cal_money);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setContentView(R.layout.money_main);

		Log.d("bbbbb", "onStart시작");

		try {
			elec_thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int k = CH.getNum_of_msg();// 보여줄 데이터 개수
		Log.d("bbbbb", "k : " + k);
		// 패킷을 우선 수신하여서 recv_packet배열에 저장

		recv_packet = new Packet[k];

		for (int i = 0; i < k; i++) {
			recv_packet[i] = CH.get_Packet(i);
		}
		Log.d("bbbbb", "recv_packet배열 저장");

		int[] base_price = new int[5];
		double[] add_price = new double[5];
		int[] last_price = new int[5];
		double[] power_d = new double[5];
		String[] money_name = {"3개월 전 요금", "2개월 전 요금", "1개월 전 요금", "현재 사용 요금", "이번 달 예상 요금"};
		
		list = new ArrayList<cal_money_list>();
		
		for (int i = 0; i < k; i++) {
			power_d[0] += Double.parseDouble(recv_packet[i].getData(3));
			power_d[1] += Double.parseDouble(recv_packet[i].getData(4));
			power_d[2] += Double.parseDouble(recv_packet[i].getData(5));
			power_d[3] += Double.parseDouble(recv_packet[i].getData(7));
			
		}
		for (int i = 0; i < 4; i++) {
			power_d[i] /= 60; 
			
		}
		
		Log.d("bbbbb", "power_d[0] : " + power_d[0]);
		power_d[4] = (int)(power_d[3]/Double.parseDouble(recv_packet[0].getData(6))*30);

		for (int j = 0; j < 5; j++) {
			if (power_d[j] < 100) {
				base_price[j] = 410;
				add_price[j] = power_d[j] * 60.7;
			} else if (power_d[j] < 200) {
				base_price[j] = 910;
				add_price[j] = 6070 + (power_d[j] - 10) * 125.9;
			} else if (power_d[j] < 300) {
				base_price[j] = 1600;
				add_price[j] = 6070 + 12590 + (power_d[j] - 20) * 187.9;
			} else if (power_d[j] < 400) {
				base_price[j] = 3850;
				add_price[j] = 6070 + 12590 + 18790
						+ (power_d[j] - 30) * 280.6;
			} else if (power_d[j] < 500) {
				base_price[j] = 7300;
				add_price[j] = 6070 + 12590 + 18790 + 28060
						+ (power_d[j] - 40) * 417.7;
			} else {
				base_price[j] = 12940;
				add_price[j] = 6070 + 12590 + 18790 + 28060 + 41770
						+ (power_d[j] - 50) * 709.5;
			}
		}

		Log.d("bbbbb", "add_price[0] : " + add_price[0]);
		for (int j = 0; j < 5; j++) {
			last_price[j] = (int) (base_price[j] + add_price[j]);
		}
		
		
		Log.d("money", "aaaaaaaa");
		listview = (ListView)findViewById(R.id.moneylistView1);
		
		for(int i=0;i<5;i++){
			list.add(new cal_money_list(money_name[i], last_price[i], R.drawable.money_good));
		}
		
		
		Log.d("money", "asdfg");
		money_adapter adapter = new money_adapter(Cal_money.this, R.layout.money_list, list);
		Log.d("money", "1111qqqq");
		listview.setAdapter(adapter);
	}

}
