package electricity;

import java.util.ArrayList;

import com.example.ui.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class money_adapter extends BaseAdapter {

	Context ctx;
	int layout;
	ArrayList<cal_money_list> list;
	LayoutInflater inf;
	Custom_Handler CH;
	Packet send_packet;
	Elec_Client elec_thread;
	Packet[] recv_packet;

	public money_adapter(Context ctx, int layout,
			ArrayList<cal_money_list> list) {
		super();
		this.ctx = ctx;
		this.layout = layout;
		this.list = list;
		inf = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = inf.inflate(layout, null);
		}
		Log.d("money", "inflate");
		TextView money_name = (TextView)convertView.findViewById(R.id.money_title);
		TextView money_cost = (TextView)convertView.findViewById(R.id.money_cost);
		ImageView money_emergency = (ImageView)convertView.findViewById(R.id.money_emergency);
		
		Log.d("money", "textview");
		cal_money_list dto = list.get(position);
		
		Log.d("money", "dto position : "+position);
		money_name.setText(dto.getMoney_name());
		Log.d("money", "money_name : "+dto.getMoney_name());
		money_cost.setText(String.valueOf(dto.getMoney_cost()));
		Log.d("money", "money_cost : "+dto.getMoney_cost());
		
		if(dto.getMoney_cost() > 50000){
			dto.setMoney_emergency(R.drawable.money_bad);
		}
		money_emergency.setImageResource(dto.getMoney_emergency());
		Log.d("money", "money_emergency : "+dto.getMoney_emergency());
		return convertView;
	}

}
