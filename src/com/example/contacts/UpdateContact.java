package com.example.contacts;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContact extends Activity {
	
	private contactsDB cDB = new contactsDB(this);
	private EditText name;
	private EditText no;
	private EditText email;
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_contact);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		name = (EditText) findViewById(R.id.editName);
		no = (EditText) findViewById(R.id.editPhone);
		email = (EditText) findViewById(R.id.editEmail);
		String[] temp;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			temp = extras.getStringArray("com.example.contacts.info");
			name.setText(temp[0]);
			no.setText(temp[1]);
			email.setText(temp[2]);
			id = Integer.parseInt(temp[3]);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_contact, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        Intent i = new Intent(this, ContactInfo.class);
	        i.putExtra("com.example.contacts.ID", id);
	        finish();
	        startActivity(i);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	public void updateContact(View view){
		SQLiteDatabase db = cDB.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(contactsDB.COL_NAME, name.getText().toString());
		values.put(contactsDB.COL_PH, no.getText().toString());
		values.put(contactsDB.COL_EM, email.getText().toString());
		String selection = contactsDB._ID + " =?";
		String[] selectionArgs = {""+ id};
		int count = db.update(contactsDB.TABLE_NAME, values, selection, selectionArgs);
		
		CharSequence text = "Contact Updated Successfully";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(this, text, duration);
		toast.show();
		
		Intent i = new Intent(this, ContactInfo.class);
		i.putExtra("com.example.contacts.ID", id);
		finish();
		startActivity(i);
	}

}
