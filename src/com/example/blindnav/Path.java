package com.example.blindnav;

//import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
//import android.os.Build;
//import android.support.v4.app.NavUtils;
//import android.view.MenuItem;
//import android.view.View;

public class Path extends Activity
{
	Locations rooms[]=new Locations[16];
	Intent intent;
	ImageView map;
	Bitmap bMap;
	Bitmap mutable_map;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path);
		
		map = (ImageView) findViewById(R.id.map);
		bMap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
		bMap.isMutable();
		// Bitmap marker=BitmapFactory.decodeResource(getResources(),R.drawable.marker);			
	    mutable_map=bMap.copy(Bitmap.Config.ARGB_8888,true);		
		map.setImageBitmap(mutable_map);
		
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
		
		// Show the Up button in the action bar.
		//setupActionBar();
		
		//intent = getIntent();
	  //  String message = (String)(intent.getStringExtra(DatabaseHandler.keyMessage));
	    Bundle b = this.getIntent().getExtras(); 
	    int message=b.getInt("keyMessage");
	    
	   // int message=getIntent().getExtras().getInt(DatabaseHandler.keyMessage,0);
	    Toast.makeText(getApplicationContext(), "Path class "+message,Toast.LENGTH_SHORT).show();
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
	}
	public void showImage(View view)
	{
		
		
	}
	
	public void drawPath(Locations destination)
	{
		ImageView map = (ImageView) findViewById(R.id.map);
		Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
		bMap.isMutable();
		Bitmap marker=BitmapFactory.decodeResource(getResources(),R.drawable.marker);			
		Bitmap user=BitmapFactory.decodeResource(getResources(),R.drawable.user);			
	    
		Bitmap mutable_map=bMap.copy(Bitmap.Config.ARGB_8888,true);		
		map.setImageBitmap(mutable_map);		
		Canvas cview=new Canvas(mutable_map);		
		
		Paint paint=new Paint();
		paint.setColor(Color.YELLOW);
		paint.setStrokeWidth(8);
		
		switch(destination.getId())
		{
		// room 101
		case 0:			
			
			cview.drawLine(300, 700, 300, 85, paint);
			cview.drawLine(300, 85, rooms[0].getxLoc(), 85, paint);
			cview.drawLine(rooms[0].getxLoc(), 85, rooms[0].getxLoc(),rooms[0].getyLoc() , paint);
			map.setImageBitmap(mutable_map);
			cview.drawBitmap(marker,rooms[0].getxLoc(), rooms[0].getyLoc(),paint);
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
			cview.drawBitmap(marker,rooms[14].getxLoc()-8, rooms[14].getyLoc(),paint);	
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
	    
	    /*try 
		{
		    Thread.sleep(5000);
		} 
		catch (InterruptedException e)
		{
		  
		}
	    */
	    Intent i = new Intent(Path.this, WifiTrilateration.class);
	    Bundle b = new Bundle();
		b.putInt("keyMessage", (int)destination.getId());                 
		i.putExtras(b);
		startActivity(i); 
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.path, menu);
		return true;
	}

	/*@Override
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
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}*/

}