package electricity;

import java.text.DecimalFormat;
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

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Get_Statistics extends Activity {

   Custom_Handler CH;
   Packet send_packet;
   Elec_Client elec_thread;
   Packet[] recv_packet;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      String[] msg = { "0", "", "", "", "", "", "", "" };
      CH = new Custom_Handler(1);
      send_packet = new Packet();
      send_packet.setType(1);
      send_packet.setData(msg);
      Log.d("Get_Statistics", "write on send_packet");

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

      try {
         elec_thread.join();
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      int k = CH.getNum_of_msg();// 보여줄 데이터 개수
      
      Log.d("get", "onStart시작");
      
      int seriesLength = 0;
      double max = 0;

      recv_packet = new Packet[k];

      for (int i = 0; i < k; i++) {
         recv_packet[i] = CH.get_Packet(i);
      }
      Log.d("get", "recv_packet배열 저장");

      List<Float[]> values = new ArrayList<Float[]>();
      Float[][] a = new Float[4][k];
      int [][] c = new int[4][k];
      
     
      for (int j = 0; j < k; j++) {
         a[0][j] = Float.parseFloat(recv_packet[j].getData(3));
         a[1][j] = Float.parseFloat(recv_packet[j].getData(4));
         a[2][j] = Float.parseFloat(recv_packet[j].getData(5));
         a[3][j] = Float.parseFloat(recv_packet[j].getData(7));
         c[0][j]= (int) (a[0][j] * 10);
         c[1][j]= (int) (a[1][j] * 10);
         c[2][j]= (int) (a[2][j] * 10);
         c[3][j]= (int) (a[3][j] * 10);
         a[0][j] = ((float) c[0][j]/(float) 10);
         a[1][j] =  ((float) c[1][j]/(float) 10);
         a[2][j] =  ((float) c[2][j]/(float) 10);
         a[3][j] =  ((float) c[3][j]/(float) 10);

         //a[3][j] = (float) Math.round(Float.parseFloat(recv_packet[j].getData(7))*10000)/10000;
         // values.add(a);// 이번달
      }
      for(int w=0; w<4;w++)
      {
//        a[w] = Math.round(a[w]*10) / 10.0;
    	  values.add(a[w]);
      }
      
      Log.d("get", "values배열 저장");
      XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

      renderer.setChartTitle("과거 사용량과의 비교");
      renderer.setChartTitleTextSize(50);

      String[] titles = new String[] { "3달 전", "2달 전", "1달 전", "이번 달" };

      int[] colors = new int[] { Color.rgb(255, 90, 217), Color.rgb(54, 255, 255), Color.rgb(255, 255, 72),
               Color.rgb(83, 255, 76) };
      // //////////////////////////////////////////////////////
   //   renderer.setLegendTextSize(35);

      int length = values.size(); // 항목갯수

      Log.d("get", "legnth : " + length);
      for (int i = 0; i < length; i++) {
         SimpleSeriesRenderer r = new SimpleSeriesRenderer();
         r.setColor(colors[i]);
         renderer.addSeriesRenderer(r);
      }

      renderer.setXTitle("제품별");
      renderer.setYTitle("사용량");
      renderer.setAxisTitleTextSize(28);
      renderer.setMarginsColor(Color.WHITE);

      renderer.setLabelsTextSize(20);
      renderer.setLegendHeight(200);
      renderer.setXAxisMin(0.5);
      // renderer.setXAxisMax(length + 0.5); // 장비 갯수 +0.5
      renderer.setYAxisMin(0);

      renderer.setLabelsColor(Color.BLACK);
      renderer.setBackgroundColor(Color.WHITE);

      // 그래프 위에 값 표시하기
      for (int i = 0; i < length; i++) {
         renderer.getSeriesRendererAt(i).setDisplayChartValues(true);
         renderer.getSeriesRendererAt(i)
               .setChartValuesTextAlign(Align.RIGHT);
         renderer.getSeriesRendererAt(i).setChartValuesTextSize(20);
      }
      Log.d("get", "그래프위값 표시하기");

      renderer.setYLabels(5);

      // x,y축 정렬 방향
      renderer.setXLabelsAlign(Align.CENTER);
      renderer.setYLabelsAlign(Align.CENTER);
      renderer.setPanEnabled(true, true); // x,y축 스크롤 여부 on/off
      renderer.setZoomEnabled(true); // zoom기능 on/off
      renderer.setZoomRate(1.0f);
      renderer.setBarSpacing(0.5f);

      Log.d("get", "xy축 정렬 방향");

      XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
      for (int i = 0; i < titles.length; i++) {
         CategorySeries series = new CategorySeries(titles[i]);
         Float[] v = values.get(i);
         
         Log.d("get","int i = 0; i < titles.length; i++");

         seriesLength = v.length;
         renderer.setXAxisMax(seriesLength + 0.5);

         for (int q = 0; q < seriesLength; q++) {
            series.add(v[q]);
            if (max < v[q]) {
               max = v[q];
            }
            //Log.d("Test", "series.add : " + v[q]);
            renderer.setXLabels(seriesLength + 1);
         }
         dataset.addSeries(series.toXYSeries());
      }
      Log.d("get", "111111111");
      String[] x_name = new String[seriesLength];
      for (int b= 0; b < k; b++) 
      {
         x_name[b] = recv_packet[b].getData(2);
         
      }
      Log.d("get", "222222222");
      renderer.setXLabels(0);
      for (int i = 0; i < seriesLength; i++) {
         renderer.addXTextLabel(i + 1, x_name[i]);
      }
      renderer.setYAxisMax(max + (max * 0.1)); // max 찾아서 넣기
      GraphicalView gv = ChartFactory.getBarChartView(this, dataset,
            renderer, Type.DEFAULT);

      setContentView(gv);

   }

}