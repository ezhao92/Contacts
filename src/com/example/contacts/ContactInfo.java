package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ContactInfo extends Activity {

	private int id;
	private contactsDB cDB;
	private SQLiteDatabase db;
	private String name;
	private String no;
	private String email;
	private TextView cName;
	private TextView cNo;
	private TextView cEm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);
		// Show the Up button in the action bar.
		setupActionBar();
		cDB = new contactsDB(this);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			id = extras.getInt("com.example.contacts.ID");
		}
		// Query info and bind to TextView
		db = cDB.getReadableDatabase();
		String selection = contactsDB._ID + "=?";
		String[] selectionArgs = { "" + id };
		String[] projection = { contactsDB.COL_NAME, contactsDB.COL_PH,
				contactsDB.COL_EM };
		Cursor c = db.query(contactsDB.TABLE_NAME, projection, selection,
				selectionArgs, null, null, null, null);
		c.moveToFirst();
		cName = (TextView) findViewById(R.id.infoName);
		cNo = (TextView) findViewById(R.id.infoNumber);
		cEm = (TextView) findViewById(R.id.infoEmail);
		name = c.getString(c.getColumnIndex(contactsDB.COL_NAME));
		no = c.getString(c.getColumnIndex(contactsDB.COL_PH));
		email = c.getString(c.getColumnIndex(contactsDB.COL_EM));
		cName.setText(name);
		cNo.setText(no);
		cEm.setText(email);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_info_action, menu);
		return super.onCreateOptionsMenu(menu);
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
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
			return true;
		case R.id.action_delete:
			deleteContact();
			return true;
		case R.id.action_update:
			updateContact();
			return true;
		case R.id.action_call:
			callContact();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void deleteContact() {
		db = cDB.getWritableDatabase();
		String selection = contactsDB._ID + "=?";
		String[] selectionArgs = { "" + id };
		db.delete(contactsDB.TABLE_NAME, selection, selectionArgs);
		CharSequence text = "Contact Deleted";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(this, text, duration);
		toast.show();
		finish();
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	protected void updateContact() {
		Intent i = new Intent(this, UpdateContact.class);
		String[] info = { name, no, email, "" + id };
		i.putExtra("com.example.contacts.info", info);
		startActivity(i);
		finish();
	}

	protected void callContact() {
		Intent i = new Intent(Intent.ACTION_CALL);
		i.setData(Uri.parse("tel:" + no));
		startActivity(i);
	}

}
