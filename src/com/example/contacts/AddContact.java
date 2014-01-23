package com.example.contacts;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends Activity {

	private EditText name;
	private EditText no;
	private EditText email;
	private contactsDB cDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		name = (EditText) findViewById(R.id.name);
		no = (EditText) findViewById(R.id.no);
		email = (EditText) findViewById(R.id.email);
		cDB = new contactsDB(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        Intent i = new Intent(this, MainActivity.class);
	        finish();
	        startActivity(i);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	public void saveInfo(View view){
		SQLiteDatabase db = cDB.getWritableDatabase();
		ContentValues values = new ContentValues();
		CharSequence text = "";
		if(name.getText().toString().equals("")){
			text = "Invalid Inputs";
		} else{
			values.put(contactsDB.COL_NAME, name.getText().toString());
			values.put(contactsDB.COL_PH, no.getText().toString());
			values.put(contactsDB.COL_EM, email.getText().toString());
			long rowID = db.insert(contactsDB.TABLE_NAME, null, values);
			text = "Contact Added";
			Intent i = new Intent(this, ContactInfo.class);
			int ID = (int) rowID;
			i.putExtra("com.example.contacts.ID", ID);
			finish();
			startActivity(i);

		}
		int duration = Toast.LENGTH_SHORT;	
		Toast toast = Toast.makeText(this, text, duration);
		toast.show();
	}

}
