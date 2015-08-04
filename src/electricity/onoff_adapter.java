package electricity;

import java.util.ArrayList;

import com.example.ui.R;
import com.example.ui.R.id;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class onoff_adapter extends BaseAdapter {

	Context ctx;
	int layout;
	ArrayList<chk_onoff_list> list;
	LayoutInflater inf;
	Custom_Handler CH;
	Packet send_packet;
	Elec_Client elec_thread;
	Packet[] recv_packet;

	public onoff_adapter(Context ctx, int layout, ArrayList<chk_onoff_list> list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = inf.inflate(layout, null);
		}

		final ToggleButton t = (ToggleButton) convertView
				.findViewById(R.id.toggleButton1);
		final TextView txt = (TextView) convertView
				.findViewById(R.id.textview1);
		final chk_onoff_list item = list.get(position);

		if (item.getOnoff_state() == true) {
			t.setChecked(true);
		}

		txt.setText(item.getItem_name());

		t.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (t.isChecked()) {
					String ID = String.valueOf(position + 1);
					String[] msg = { "0", ID, item.getItem_name(), "", "", "",
							"", "" };
					CH = new Custom_Handler(2);
					send_packet = new Packet();
					send_packet.setType(2);
					send_packet.setData(msg);
					send_packet.set_bool(true);
					Log.d("Test", "write on send_packet");
					recv_packet = new Packet[10];

					elec_thread = new Elec_Client(CH, send_packet);

					try {
						elec_thread.start();

					} catch (Exception e) {
						e.printStackTrace();
					}
					// txt.setText("checked");
					try {
		                  t.setEnabled(false);
		                  Thread.sleep(1500);
		                  t.setEnabled(true);
		               } catch (InterruptedException e) {
		                  // TODO Auto-generated catch block
		               }
					item.setOnoff_state(true);
				} else {
					String ID = String.valueOf(position + 1);
					String[] msg = { "0", ID, item.getItem_name(), "", "", "",
							"", "" };
					CH = new Custom_Handler(2);
					send_packet = new Packet();
					send_packet.setType(2);
					send_packet.setData(msg);
					send_packet.set_bool(false);
					Log.d("Test", "write on send_packet");
					recv_packet = new Packet[10];

					elec_thread = new Elec_Client(CH, send_packet);

					try {
						elec_thread.start();

					} catch (Exception e) {
						e.printStackTrace();
					}
					// txt.setText("Nooooooo checked");
					try {
		                  t.setEnabled(false);
		                  Thread.sleep(1500);
		                  t.setEnabled(true);
		               } catch (InterruptedException e) {
		                  // TODO Auto-generated catch block
		               }
					item.setOnoff_state(false);
				}
			}
		});
		txt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Log.d("dialog", "dialog open");
				final Dialog dialog = new Dialog(ctx);
				dialog.setTitle("이름 변경");
				View view = inf.inflate(R.layout.name_change, null);

				final EditText ed1 = (EditText) view
						.findViewById(R.id.editText1);
				Button b_ok = (Button) view.findViewById(R.id.change_ok);
				Button b_cancel = (Button) view
						.findViewById(R.id.change_cancel);

				ed1.setText(item.getItem_name());

				dialog.setContentView(view);
				dialog.show();

				b_ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String changed_name = ed1.getText().toString();
						String ID = String.valueOf(position + 1);
						String[] msg = { "0", ID, item.getItem_name(),
								changed_name, "", "", "", "" };
						CH = new Custom_Handler(2);
						send_packet = new Packet();
						send_packet.setType(3);
						send_packet.setData(msg);
						send_packet.set_bool(false);
						Log.d("Test", "write on send_packet");
						recv_packet = new Packet[10];

						elec_thread = new Elec_Client(CH, send_packet);

						try {
							elec_thread.start();

						} catch (Exception e) {
							e.printStackTrace();
						}

						chk_onoff_list newItem = new chk_onoff_list(
								changed_name, item.getOnoff_state()); // true라
																		// 가정
						list.set(position, newItem);
						notifyDataSetChanged();

						dialog.dismiss();
					}
				});
				
				b_cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
			}
		});

		return convertView;
	}
}
