package electricity;

import java.util.ArrayList;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
//import android.R;
import android.R.integer;
import android.R.layout;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ui.R;

public class pi_graph extends Activity {

   Custom_Handler CH;
   Packet send_packet;
   Elec_Client elec_thread;
   Packet[] recv_packet;
   XYMultipleSeriesRenderer renderer;
   Float[] values;
   LinearLayout chart_layout;
   GraphicalView gv;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

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

   @Override
   protected void onStart() {
      // TODO Auto-generated method stub
      super.onStart();
      setContentView(R.layout.pi);

      Log.d("aaaaa", "onStart시작");

      try {
         elec_thread.join();
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      
      Button b_all = (Button) findViewById(R.id.all_pi);
      Button m3_ago = (Button) findViewById(R.id.m3_ago_pi);
      Button m2_ago = (Button) findViewById(R.id.m2_ago_pi);
      Button m1_ago = (Button) findViewById(R.id.m1_ago_pi);
      Button now_m = (Button) findViewById(R.id.now_pi);

      final int k = CH.getNum_of_msg();// 보여줄 데이터 개수
      Log.d("aaaaa", "k값은 " + k);
      // 패킷을 우선 수신하여서 recv_packet배열에 저장
      recv_packet = new Packet[k];
      values = new Float[k];

      for (int i = 0; i < k; i++) {
         recv_packet[i] = CH.get_Packet(i);
      }
      Log.d("aaaaa", "recv_packet배열 저장");

      
      for (int j = 0; j < k; j++) {
         values[j] = Float.parseFloat(recv_packet[j].getData(3))
               + Float.parseFloat(recv_packet[j].getData(4))
               + Float.parseFloat(recv_packet[j].getData(5))
               + Float.parseFloat(recv_packet[j].getData(7));
      }

      b_all.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            // TODO Auto-generated method stub
            for (int j = 0; j < k; j++) {
               values[j] = Float.parseFloat(recv_packet[j].getData(3))
                     + Float.parseFloat(recv_packet[j].getData(4))
                     + Float.parseFloat(recv_packet[j].getData(5))
                     + Float.parseFloat(recv_packet[j].getData(7));
            }
            int pi_total = 0;

            for (int i = 0; i < k; i++) {
               pi_total += values[i];

            }
            make_pi(pi_total, k);
         }
      });

      m3_ago.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            // TODO Auto-generated method stub
            for (int j = 0; j < k; j++) {
               values[j] = Float.parseFloat(recv_packet[j].getData(3));
            }
            int pi_total = 0;

            for (int i = 0; i < k; i++) {
               pi_total += values[i];

            }
            make_pi(pi_total, k);
         }
      });

      m2_ago.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            // TODO Auto-generated method stub
            for (int j = 0; j < k; j++) {
               values[j] = Float.parseFloat(recv_packet[j].getData(4));
            }
            int pi_total = 0;

            for (int i = 0; i < k; i++) {
               pi_total += values[i];

            }
            make_pi(pi_total, k);
         }
      });

      m1_ago.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            // TODO Auto-generated method stub
            for (int j = 0; j < k; j++) {
               values[j] = Float.parseFloat(recv_packet[j].getData(5));
            }
            int pi_total = 0;

            for (int i = 0; i < k; i++) {
               pi_total += values[i];

            }
            make_pi(pi_total, k);
         }
      });

      now_m.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            // TODO Auto-generated method stub
            for (int j = 0; j < k; j++) {
               values[j] = Float.parseFloat(recv_packet[j].getData(7));
            }
            int pi_total = 0;

            for (int i = 0; i < k; i++) {
               pi_total += values[i];

            }
            make_pi(pi_total, k);
         }
      });

      Log.d("aaaaa", "on start 종료");
   }

   public void make_pi(int pi_total, int k) {
      Log.d("aaaaa", "22222");
      // 항목별 문구 지정
      CategorySeries series = new CategorySeries("항목별");
      renderer = new XYMultipleSeriesRenderer();
      chart_layout = (LinearLayout) findViewById(R.id.linearLayout_chart);

      // 값 쏴주기
      for (int q = 0; q < k; q++) {
         series.add(
               recv_packet[q].getData(2)
                     + Math.round((values[q] / pi_total * 100) * 10)
                     / 10.0 + "%", values[q]);

      }

      Log.d("aaaaa", "33333");

      // 그래프와 함께 표시되는 항목별 표시 문구의 글자 크기
      renderer.setLabelsTextSize(30);
      renderer.setChartTitle("사용량");
      renderer.setChartTitleTextSize(80);
      Log.d("aaaaa", "11111");

      // 하단부에 표시되는 문구의 글자 크기
      renderer.setLegendTextSize(50);

      Log.d("aaaaa", "44444");

      // Zoom 버튼 표시 여부
      renderer.setZoomButtonsVisible(true);
      // Zoom 기능 활성화
      renderer.setZoomEnabled(true);
      renderer.setLabelsColor(Color.BLACK);

      int[] colors = new int[] { Color.rgb(255, 144, 144),
            Color.rgb(237, 175, 140), Color.rgb(126, 255, 255),
            Color.rgb(107, 102, 255), Color.rgb(255, 144, 255),
            Color.rgb(167, 72, 255), Color.rgb(126, 255, 255),
            Color.rgb(243, 255, 72) };
      Log.d("aaaaa", "55555");
      for (int color = 0; color < k; color++) {
         SimpleSeriesRenderer r = new SimpleSeriesRenderer();
         r.setColor(colors[color]);
         renderer.addSeriesRenderer(r);
      }

      Log.d("aaaaa", "66666");
      // 그래프를 view로 얻어온다
      chart_layout.removeView(gv);
      gv = ChartFactory.getPieChartView(pi_graph.this, series, renderer);
      Log.d("aaaaa", "7777");
      // setContentView(gv);
      chart_layout.addView(gv);
//      setContentView(gv);

      Log.d("aaaaa", "make_pi 종료");
   }

}