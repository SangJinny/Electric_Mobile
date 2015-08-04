package electricity;

import com.example.ui.R;

import android.R.id;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.ViewFlipper;
import android.view.MotionEvent;

public class UI_menu extends TabActivity {

	private ViewFlipper m_viewFlipper;
	private int m_nPreTouchPosX = 0;
	LinearLayout sub1, sub2;
	public TabHost tabHost = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		Log.d("why", "Open_Main_Activity");

		Resources res = getResources(); // ���ҽ� ��ü ����
		tabHost = (TabHost) findViewById(id.tabhost); // TabHost ��ü ����
		TabHost.TabSpec spec; // TabHost.TabSpec ����
		Intent intent; // Intent ����

		Log.d("why", "Resource");

		intent = new Intent(this, chk_onoff_main.class);
		spec = tabHost.newTabSpec("tab1")
				.setIndicator("On/Off ��Ȳ", res.getDrawable(R.drawable.buzzer))
				.setContent(intent);
		tabHost.addTab(spec);

		Log.d("why", "1");

		intent = new Intent(this, show_graph_group.class);
		spec = tabHost.newTabSpec("tab2")
				.setIndicator("��뷮", res.getDrawable(R.drawable.chart_icon))
				.setContent(intent);
		tabHost.addTab(spec);

		Log.d("why", "2");

		intent = new Intent(this, Cal_money.class);
		spec = tabHost.newTabSpec("tab3")
				.setIndicator("���", res.getDrawable(R.drawable.money_icon))
				.setContent(intent);
		tabHost.addTab(spec);

		Log.d("why", "3");

		tabHost.setCurrentTab(0); // �ʱ� ���� �� ����
	}

}
