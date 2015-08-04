package electricity;

import java.net.Socket;

import com.example.ui.R;

import electricity.Custom_Handler;
import electricity.Elec_Client;
import electricity.Packet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class show_graph extends Activity {
	
	Custom_Handler CH;
	Socket sock;
	TextView t;
	Packet send_packet;
	Packet recv_packet;
	Elec_Client elec_thread;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       				
		Log.d("why", "show_graph");
		setContentView(R.layout.chk_graph);
		
		Button b1 = (Button) findViewById(R.id.pi_graph);
		Button b2 = (Button) findViewById(R.id.bar_graph);
		

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				Log.d("why", "button1");
				Intent intent = new Intent(show_graph.this, pi_graph.class);
				//startActivity(intent);
				view = show_graph_group.show_graph_group.getLocalActivityManager().startActivity("pi_graph", intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
				show_graph_group.show_graph_group.replaceView(view);
			}
		});

		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.d("why", "button2");
				Intent intent = new Intent(show_graph.this, Get_Statistics.class);
				//startActivity(intent);
				view = show_graph_group.show_graph_group.getLocalActivityManager().startActivity("Get_Statistics", intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
				show_graph_group.show_graph_group.replaceView(view);
			}
		});
	}
}
