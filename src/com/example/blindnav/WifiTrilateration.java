package com.example.blindnav;

import java.util.*;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

@SuppressLint("NewApi")
@SuppressWarnings("unused")

public class WifiTrilateration extends Activity implements Runnable,OnInitListener
{
	
	TextToSpeech tts;
	private int MY_DATA_CHECK_CODE = 0;

   WifiManager mainWifiObj;
   WifiScanReceiver wifiReciever;
   String wifis[];
   float quality[];
   String quality_string[];
   int i=0;
   float strength_in_int;
   float distance;
   Float strength[] = new Float[3];
   float x,y;
   float k1,k2;
   String ans[]= new String[1];
   
   
   //mesages
   String start="You are at Lab 1. Move forward";
   String destination="You have reached your destination";
   String library="Library is towards left.Move forward to reach your destination";
   String lab2="lab 2 is towards right";
   String itlab=" IT Lab is towards left";
   String examcell="Exam cell is towards left";
   String hod="HOD Cabin is towards right";
   String room106="Room 106 is towards right";
   String staffroom="Staff room is towards right";
   String balcony="You are at the balcony.Please move straight to move towards foyer";
   String crossway="Walk straight for washroom.Stairs are towards the left.Move straight and take left for classrooms 101 to 105";
   String lab1="You are at lab1.Cheers";
   
	// x direction 414px=36.5 meters
	// 1 meter= (378/20) px = 18.9 px
	
	// y direction 724px=74 meters
	// 1 meter= (127/19) px = 6.684210 px
	
	Locations rooms[]=new Locations[16];
	Intent intent;
	ImageView map;
	Bitmap bMap;
	Bitmap mutable_map;
	int message;
	Canvas cview;
	Bitmap marker;
	Paint paint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_wifi_trilateration);
	      final Button button = (Button) findViewById(R.id.button1);
	      mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	      wifiReciever = new WifiScanReceiver();
	      //int count=1;
	      
	      
	      
	      map = (ImageView) findViewById(R.id.map);
			bMap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
			bMap.isMutable();
			// Bitmap marker=BitmapFactory.decodeResource(getResources(),R.drawable.marker);			
		    mutable_map=bMap.copy(Bitmap.Config.ARGB_8888,true);		
			map.setImageBitmap(mutable_map);
			Bitmap user=BitmapFactory.decodeResource(getResources(),R.drawable.user);			
			   
			
			myScan((View)map);
		      
		      Intent checkIntent = new Intent();
				checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
				startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
		      
			
			// create Locations objects
		    rooms[0]=new Locations(0,"ROOM 101",103,106);
		    rooms[1]=new Locations(1,"ROOM 102",80,106);
		    rooms[2]=new Locations(2,"ROOM 103",71,57);
		    rooms[3]=new Locations(3,"ROOM 104",112,57);
		    rooms[4]=new Locations(4,"ROOM 105",192,57);
		    rooms[5]=new Locations(5,"ROOM 106",347,229);
		    rooms[6]=new Locations(6,"STAFF ROOM",347,139);
		    rooms[7]=new Locations(7,"HOD",347,170);
		    rooms[8]=new Locations(8,"EXAM CELL",249,182);
		    rooms[9]=new Locations(9,"LAB 1",348,702);
		    rooms[10]=new Locations(10,"LAB 2",346,436);
		    rooms[11]=new Locations(11,"IT LAB",250,396);
		    rooms[12]=new Locations(12,"LIBRARY",249,588);
		    rooms[13]=new Locations(13,"MENS WASHROOM",336,61);
		    rooms[14]=new Locations(14,"LADIES WASHROOM 1",348,513);
		    rooms[15]=new Locations(15,"LADIES WASHROOM 2",345,631);
			
			Bundle b = this.getIntent().getExtras(); 
		    message=b.getInt("keyMessage");
		    switch(message)
		    {
		    case 0:drawPath(rooms[0]);break;
		    case 1:drawPath(rooms[1]);break;
		    case 2:drawPath(rooms[2]);break;
		    case 3:drawPath(rooms[3]);break;
		    case 4:drawPath(rooms[4]);break;
		    case 5:drawPath(rooms[5]);break;
		    case 6:drawPath(rooms[6]);break;
		    case 7:drawPath(rooms[7]);break;
		    case 8:drawPath(rooms[8]);break;
		    case 9:drawPath(rooms[9]);break;
		    case 10:drawPath(rooms[10]);break;
		    case 11:drawPath(rooms[11]);break;
		    case 12:drawPath(rooms[12]);break;
		    case 13:drawPath(rooms[13]);break;
		    case 14:drawPath(rooms[14]);break;
		    case 15:drawPath(rooms[15]);break;
		    }
		    
		   // showMarker1(message);
		  
	   }
	
	public void myScan(View view){
		   mainWifiObj.startScan();
		   
	   }
	
	//TTTS
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    if (requestCode == MY_DATA_CHECK_CODE) {
		        if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {      
		            tts = new TextToSpeech(this, this);
		        }
		        else {
		            Intent installTTSIntent = new Intent();
		            installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
		            startActivity(installTTSIntent);
		        }
		        }
		}
	   protected void onPause() {
		      unregisterReceiver(wifiReciever);
		      super.onPause();
		   }

		   protected void onResume() {
		      registerReceiver(wifiReciever, new IntentFilter(
		      WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		      super.onResume();
		   }

		   class WifiScanReceiver extends BroadcastReceiver 
		   {
		      @SuppressLint("UseValueOf")
		      
		      public void onReceive(Context c, Intent intent) {
		         List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
		         
		         List<ScanResult> known_list=new ArrayList<ScanResult>();
		         for(int i=0;i<wifiScanList.size();i++)
		         {
		        	 if(wifiScanList.get(i).SSID.equalsIgnoreCase("library") || wifiScanList.get(i).SSID.equalsIgnoreCase("s_123") || 
		        			 wifiScanList.get(i).SSID.equalsIgnoreCase("openwrt") || wifiScanList.get(i).SSID.equalsIgnoreCase("lab2")
		        			 || wifiScanList.get(i).SSID.equalsIgnoreCase("D-Link") 
		        			 || wifiScanList.get(i).SSID.equalsIgnoreCase("ITLab")
		        			 || wifiScanList.get(i).SSID.equalsIgnoreCase("106")
		        			 || wifiScanList.get(i).SSID.equalsIgnoreCase("exam cell")
		        			 || wifiScanList.get(i).SSID.equalsIgnoreCase("hod")
		        			 || wifiScanList.get(i).SSID.equalsIgnoreCase("staffroom"))
		        	 known_list.add(wifiScanList.get(i));
		         }
		         
		         wifis = new String[known_list.size()];
		         
		         Collections.sort(known_list, new Comparator<ScanResult>() {
		             @Override
		             public int compare(ScanResult lhs, ScanResult rhs) {
		                 return (lhs.level >rhs.level ? -1 : (lhs.level==rhs.level ? 0 : 1));
		             }
		         });
		         
		         
		            /* signal level to percentage*/
		         for(i=0;i<known_list.size();i++)
		         {
		        	 wifis[i] = ((known_list.get(i).SSID).toString());
		            if(known_list.get(i).level <= -100)
		                strength_in_int = new Integer(0);
		            else if(known_list.get(i).level >= -50)
		            	strength_in_int = new Integer(100);
		            else
		            	strength_in_int = new Integer(2 * (known_list.get(i).level + 100));
		            
		           strength[i]=strength_in_int;
		            
		            wifis[i]=wifis[i]+"\nStrength in %: "+strength_in_int + "\n********\n";
		            
		            ans[0]=wifis[0];
		            
		            if(ans[0].contains("D-Link"))// arunima
		            {
		            	tts.speak(start, TextToSpeech.QUEUE_FLUSH, null);
		            	showMarker(300,702,message);
		            	
		            }
		            if(ans[0].contains("library")) // library
		            {
		            	tts.speak(library, TextToSpeech.QUEUE_FLUSH, null);
		            	showMarker(300,588,message);
		            }
		            else if(ans[0].contains("S_123")) // snehita lab2
		            {
		            	tts.speak(lab2, TextToSpeech.QUEUE_FLUSH, null);
		            	showMarker(300,436,message);
		            }
		            else if(ans[0].contains("ITLab")) // itlab
		            {
		            	tts.speak(itlab, TextToSpeech.QUEUE_FLUSH, null);
		            	showMarker(300,396,message);
		            }
		            else if(ans[0].contains("110")) // 106
		            {
		            	tts.speak(room106, TextToSpeech.QUEUE_FLUSH, null);
		            	showMarker(300,229,message);
		            }
		            else if(ans[0].contains("exam cell")) // examcell
		            {
		            	tts.speak(examcell, TextToSpeech.QUEUE_FLUSH, null);
		            	showMarker(300,182,message);
		            }
		            else if(ans[0].contains("hod")) // hod
		            {
		            	tts.speak(hod, TextToSpeech.QUEUE_FLUSH, null);
		            	showMarker(300,170,message);
		            }
		            else if(ans[0].contains("staffroom")) // staffroom
		            {
		            	tts.speak(staffroom, TextToSpeech.QUEUE_FLUSH, null);
		            	showMarker(300,139,message);
		            }else
		            if(ans[0].contains("crossway"))// shrikant
		            {
		            	 	tts.speak(crossway, TextToSpeech.QUEUE_FLUSH, null);
		            	 	showMarker(300,85,message);
		            }		            	
		            }
		            
		            new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		         
		        }
		   }

		@Override
		public void onInit(int initStatus) {
		    if (initStatus == TextToSpeech.SUCCESS) {
		        tts.setLanguage(Locale.UK);
		    }
		}
		   
		
	public void showImage(View view)
	{
		
		
	}
	
	public void drawPath(Locations destination)
	{
		ImageView map = (ImageView) findViewById(R.id.map);
		Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
		bMap.isMutable();
		marker=BitmapFactory.decodeResource(getResources(),R.drawable.marker);			
		Bitmap user=BitmapFactory.decodeResource(getResources(),R.drawable.user);			
		   
		Bitmap mutable_map=bMap.copy(Bitmap.Config.ARGB_8888,true);		
		map.setImageBitmap(mutable_map);		
		cview=new Canvas(mutable_map);		
		
		paint=new Paint();
		paint.setStrokeWidth(8);
		paint.setColor(Color.YELLOW);
		
		switch(destination.getId())
		{
		// room 101
		case 0:
			cview.drawLine(300, 700, 300, 85, paint);
			cview.drawLine(300, 85, rooms[0].getxLoc(), 85, paint);
			cview.drawLine(rooms[0].getxLoc(), 85, rooms[0].getxLoc(),rooms[0].getyLoc() , paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[0].getxLoc()-8, rooms[0].getyLoc()-8,paint);
			break;
			
		//room 102
		case 1:
			cview.drawLine(300, 700, 300, 85, paint);
			cview.drawLine(300, 85, rooms[1].getxLoc(), 85, paint);
			cview.drawLine(rooms[1].getxLoc(), 85, rooms[1].getxLoc(),rooms[1].getyLoc() , paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[1].getxLoc()-8, rooms[1].getyLoc()-8,paint);
			break;
			
		// room 103
		case 2:
			cview.drawLine(300, 700, 300, 85, paint);
			cview.drawLine(300, 85, rooms[2].getxLoc(), 85, paint);
			cview.drawLine(rooms[2].getxLoc(), 85, rooms[2].getxLoc(),rooms[2].getyLoc() , paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[2].getxLoc()-8, rooms[2].getyLoc()-8,paint);
			break;
			
		//room 104
		case 3:
			cview.drawLine(300, 700, 300, 85, paint);
			cview.drawLine(300, 85, rooms[3].getxLoc(), 85, paint);
			cview.drawLine(rooms[3].getxLoc(), 85, rooms[3].getxLoc(),rooms[3].getyLoc() , paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[3].getxLoc()-8, rooms[3].getyLoc()-8,paint);
			break;
			
		// room 105
		case 4:
			cview.drawLine(300, 700, 300, 85, paint);
			cview.drawLine(300, 85, rooms[4].getxLoc(), 85, paint);
			cview.drawLine(rooms[4].getxLoc(), 85, rooms[4].getxLoc(),rooms[4].getyLoc() , paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[4].getxLoc()-8, rooms[4].getyLoc()-8,paint);
			break;
			
		// room 106
		case 5:
			cview.drawLine(300, 700, 300, rooms[5].getyLoc(), paint);
			cview.drawLine(300, rooms[5].getyLoc(), rooms[5].getxLoc(), rooms[5].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[5].getxLoc()-8, rooms[5].getyLoc()-8,paint);	
			break;
		
		
		// staff room
		case 6:
			cview.drawLine(300, 700, 300, rooms[6].getyLoc(), paint);
			cview.drawLine(300, rooms[6].getyLoc(), rooms[6].getxLoc(), rooms[6].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[6].getxLoc()-8, rooms[6].getyLoc()-8,paint);	
			break;			
		
		// hod
		case 7:
			cview.drawLine(300, 700, 300, rooms[7].getyLoc(), paint);
			cview.drawLine(300, rooms[7].getyLoc(), rooms[7].getxLoc(), rooms[7].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[7].getxLoc()-8, rooms[7].getyLoc()-8,paint);	
			break;	
		
		//exam cell
		case 8:
			cview.drawLine(300, 700, 300, rooms[8].getyLoc(), paint);
			cview.drawLine(300, rooms[8].getyLoc(), rooms[8].getxLoc(), rooms[8].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[8].getxLoc()-8, rooms[8].getyLoc()-8,paint);	
			break;	
		
		// lab 1
		case 9:
			cview.drawLine(300, 700, 300, rooms[9].getyLoc(), paint);
			cview.drawLine(300, rooms[9].getyLoc(), rooms[9].getxLoc(), rooms[9].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[9].getxLoc()-8, rooms[9].getyLoc()-8,paint);	
			break;	
		
		// lab 2
		case 10:
			cview.drawLine(300, 700, 300, rooms[10].getyLoc(), paint);
			cview.drawLine(300, rooms[10].getyLoc(), rooms[10].getxLoc(), rooms[10].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[10].getxLoc()-8, rooms[10].getyLoc()-8,paint);	
			break;	
		
		// it lab
		case 11:
			cview.drawLine(300, 700, 300, rooms[11].getyLoc(), paint);
			cview.drawLine(300, rooms[11].getyLoc(), rooms[11].getxLoc(), rooms[11].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[11].getxLoc()-8, rooms[11].getyLoc()-8,paint);	
			break;				
		
		// library
		case 12:
			cview.drawLine(300, 700, 300, rooms[12].getyLoc(), paint);
			cview.drawLine(300, rooms[12].getyLoc(), rooms[12].getxLoc(), rooms[12].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[12].getxLoc()-8, rooms[12].getyLoc()-8,paint);	
			break;	
		
		// mens washroom
		case 13:
			cview.drawLine(300, 700, 300, 85, paint);
			cview.drawLine(300, 85, rooms[13].getxLoc(), rooms[13].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[13].getxLoc()-8, rooms[13].getyLoc()-8,paint);	
			break;
		
		//ladies washroom 1
		case 14:
			cview.drawLine(300, 700, 300, rooms[14].getyLoc(), paint);
			cview.drawLine(300, rooms[14].getyLoc(), rooms[14].getxLoc(), rooms[14].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[14].getxLoc()-8, rooms[14].getyLoc()-8,paint);	
			break;	
		
		//ladies washroom 2
		case 15:
			cview.drawLine(300, 700, 300, rooms[15].getyLoc(), paint);
			cview.drawLine(300, rooms[15].getyLoc(), rooms[15].getxLoc(), rooms[15].getyLoc(), paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[15].getxLoc()-8, rooms[15].getyLoc()-8,paint);	
			break;	
		}
		
	    cview.drawBitmap(user,292,700,paint);
	    
	}
	
	public void showMarker(float x,float y,int message)
	{
		cview.drawBitmap(marker,x,y,paint);
		if(x==300 && y==rooms[message].getyLoc())
		{
			tts.speak(destination, TextToSpeech.QUEUE_FLUSH, null);
		}	
	}
	/*public void showMarker1(int message)
	{		
		Toast.makeText(getApplicationContext(), "Marker 1 class",Toast.LENGTH_SHORT).show();
		int b=700;		
		while(b>rooms[message].getyLoc())
		{
		cview.drawBitmap(marker,300,b,paint);
		b=b-40;
		// play audio file of message
		}
	}*/
	
	        
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wifi_trilateration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
