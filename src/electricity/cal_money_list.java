package electricity;

import android.media.Image;
import android.widget.ImageView;

public class cal_money_list {
	
	String money_name;
	int money_cost;
	int money_emergency;
	
	public cal_money_list(String money_name, int money_cost,
			int money_emergency) {
		super();
		this.money_name = money_name;
		this.money_cost = money_cost;
		this.money_emergency = money_emergency;
	}
	public String getMoney_name() {
		return money_name;
	}
	public void setMoney_name(String money_name) {
		this.money_name = money_name;
	}
	public int getMoney_cost() {
		return money_cost;
	}
	public void setMoney_cost(int money_cost) {
		this.money_cost = money_cost;
	}
	public int getMoney_emergency() {
		return money_emergency;
	}
	public void setMoney_emergency(int money_emergency) {
		this.money_emergency = money_emergency;
	}
	
	

}
