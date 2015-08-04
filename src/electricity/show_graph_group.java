package electricity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class show_graph_group extends ActivityGroup {

	public static show_graph_group show_graph_group;
	private ArrayList<View> history;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		history = new ArrayList<View>();
		show_graph_group = this;
		
		Log.d("why", "show_graph_group111");
		
		Intent intent = new Intent(show_graph_group.this, show_graph.class);
		
		Log.d("why", "show_graph_group2222");
		View view = getLocalActivityManager().startActivity("show_graph", intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		
		Log.d("why", "show_graph_group3333");
		replaceView(view);
		
	}
	
	// 새로운 Level의 Activity를 추가하는 경우
	public void replaceView(View view) {
		history.add(view);
		setContentView(view);
		
		Log.d("why", "replaceView");
	}
	
	// Back Key가 눌러졌을 경우에 대한 처리
	public void back() {
		if(history.size() > 0) {
			history.remove(history.size()-1);
			if(history.size() ==  0)
				finish();
			else 
				setContentView(history.get(history.size()-1));
		} 
		else
		{
			finish();
		}
	}
	// Back Key에 대한 Event Handler
		@Override
		public void onBackPressed() {
			show_graph_group.back();
			return ;
		}

}
