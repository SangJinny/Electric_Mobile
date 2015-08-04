package electricity;

import android.R.bool;

public class chk_onoff_list {

	String item_name;
	boolean onoff_state;
	
	public chk_onoff_list(String item_name, boolean b) {
		super();
		this.item_name = item_name;
		this.onoff_state = b;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public boolean getOnoff_state() {
		return onoff_state;
	}

	public void setOnoff_state(boolean onoff_state) {
		this.onoff_state = onoff_state;
	}
	
	
}
