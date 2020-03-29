package com.example.blindnav;

import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
//import android.media.MediaPlayer;

public class MainActivity extends Activity implements OnInitListener,Runnable
{
	
	
	static final int DIALOG_SET_SCAN_RATE = 0;
	WifiManager wifi;
	//Obtain access to and turn on WiFi
	boolean wifiWasEnabled;
	

	
	//TTS Stuff
	TextToSpeech tts;
	private int MY_DATA_CHECK_CODE = 0;
	
	Timer timer;

	Integer scanRate = 500;

	boolean accessibilityOn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		Toast.makeText(getApplicationContext(), "FIRST FLOOR",Toast.LENGTH_SHORT).show();
	//	MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.tone); mp.start();
		
		//Obtain access to and turn on WiFi
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiWasEnabled = wifi.isWifiEnabled();
			
		
		
		if(wifiWasEnabled)
		{
			
		}
		else
		{
			wifi.setWifiEnabled(true);
    			
		}
		
		
		//Test for TTS
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);	
	}
	
	
	
	public void showImage(View view)
	{
		ImageView map = (ImageView) findViewById(R.id.map);
		Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
		bMap.isMutable();
		// Bitmap marker=BitmapFactory.decodeResource(getResources(),R.drawable.marker);			
	    Bitmap mutable_map=bMap.copy(Bitmap.Config.ARGB_8888,true);
		
		map.setImageBitmap(mutable_map);
		
	}
		/*Canvas cview=new Canvas(mutable_map);
		
		//BitmapDrawable draw=new BitmapDrawable(bMap);
		Paint paint=new Paint();
		paint.setColor(Color.RED);
		cview.drawLine(300, 450, 300, 700, paint);
		map.setImageBitmap(mutable_map);
		Integer i=cview.getWidth();
		Log.i("Width=",Integer.toString(i));
	//	Graphics graphics=Graphics.FromImage(bMap);
		
	   cview.drawBitmap(marker,300,700,paint);
	}*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Intent intent = new Intent(MainActivity.this, DatabaseHandler.class);
	             startActivity(intent);	                         
	            return true;
	       
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		
	}	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
