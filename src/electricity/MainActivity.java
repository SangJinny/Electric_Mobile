package electricity;

import com.example.ui.R;

import android.R.id;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Log.d("why", "main_onCreate");
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run() {
            	Log.d("why", "run()");
                Intent intent = new Intent(MainActivity.this, UI_menu.class);
                Log.d("why", "make_Intent");
                startActivity(intent);
                Log.d("why", "finish()");
                finish();
            }
        }, 2000);
    }
}

class Custom_Handler{
	
	public void handleMessage(Packet msg) {
		// TODO Auto-generated method stub
		 recv_packet[index] = msg;
	      Log.d("Test","Packet copy complete : "+ recv_packet[index].getData(1));
	      index++;
	      //text1.setText(recv_packet.getData(0)+" "+recv_packet.getType());
	      Log.d("Test","Change Complete");
	}


	private Packet[] recv_packet;
	private int graph_type; // bar graph, 2, 1, now, per month
	private int index;
	private int num_of_msg;
	Custom_Handler(int graph_type){
		recv_packet = new Packet[10];
		this.graph_type = graph_type;
		index = 0;
		num_of_msg = 0;
	} // 변경시킬 View를 파라미터로 마음껏 추가하면 된다.
	
	
	public Packet get_Packet(int index) {
		return recv_packet[index];
	}

	public int getNum_of_msg() {
		return num_of_msg;
		 
	}

	public void setNum_of_msg(int num_of_msg) {
		this.num_of_msg = num_of_msg;
	}
	
}
