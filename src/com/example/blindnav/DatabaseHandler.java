package com.example.blindnav;
//import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
//import android.os.Build;
//import android.support.v4.app.NavUtils;
//simport android.view.MenuItem;

public class DatabaseHandler extends Activity
{	
	Locations rooms[]=new Locations[16];
	private boolean mNaviFirstHit = true;
	Spinner spinner;
	public final static String keyMessage="";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_handler);
		
		
		// create Locations objects
	    rooms[0]=new Locations(0,"ROOM 101",103,106);
	    rooms[1]=new Locations(1,"ROOM 102",80,106);
	    rooms[2]=new Locations(2,"ROOM 103",71,57);
	    rooms[3]=new Locations(3,"ROOM 104",112,57);
	    rooms[4]=new Locations(4,"ROOM 105",192,57);
	    rooms[5]=new Locations(5,"ROOM 106",347,229);
	    rooms[6]=new Locations(6,"STAFFROOM",347,139);
	    rooms[7]=new Locations(7,"HOD CABIN",347,170);
	    rooms[8]=new Locations(8,"EXAM CELL",249,182);
	    rooms[9]=new Locations(9,"LAB 1",348,702);
	    rooms[10]=new Locations(10,"LAB 2",346,436);
	    rooms[11]=new Locations(11,"IT LAB",250,396);
	    rooms[12]=new Locations(12,"LIBRARY",249,588);
	    rooms[13]=new Locations(13,"MENS WASHROOM",336,61);
	    rooms[14]=new Locations(14,"LADIES WASHROOM 1",348,513);
	    rooms[15]=new Locations(15,"LADIES WASHROOM 2",345,631);
		
		spinner = (Spinner) findViewById(R.id.spinner1);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.room_names, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);				
		spinner.setAdapter(adapter);		
		
	spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
	{
		@SuppressWarnings("unused")
		public void onClick(View v) 
		{
            // TODO Auto-generated method stub
        }
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
		{
			if (mNaviFirstHit) {
		        mNaviFirstHit = false;		        
		    }
			else
			{
			String item=parent.getItemAtPosition(position).toString();
		    
		    
		    Intent i = new Intent(DatabaseHandler.this, Path.class);
		    
		    for(int j=0;j<16;j++)
		    {
		    	if(rooms[j].getName().equals(item))
		    		
		    	{
		    		Bundle b = new Bundle();
		    		b.putInt("keyMessage", (int)rooms[j].getId());                 
		    		i.putExtras(b);    
		    		Toast.makeText(parent.getContext(), "selected : "+(int)rooms[j].getId(),Toast.LENGTH_SHORT).show(); 
		    		//i.putExtra("keyMessage", (int)rooms[j].getId());
					startActivity(i);  
		    	}	    		
		    }	 
			//String selected_destination = item;
			
			     
				// TODO Auto-generated method stub
		}}
		
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub				
		}
	});
	//setupActionBar();
    }	 
		// Show the Up button in the action bar.
		
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.database_handler, menu);
		return true;
	}
	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		case android.R.id.home:			
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
	
	/*@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() 
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	*/
	}
