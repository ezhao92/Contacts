package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView mContactList;
	private contactsDB cDB;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Pull data from database into ListView
		mContactList = (ListView) findViewById(R.id.contactList);
		cDB = new contactsDB(this);
		SQLiteDatabase db = cDB.getReadableDatabase();
		String[] projection = { contactsDB._ID, contactsDB.COL_NAME };
		String sortOrder = contactsDB.COL_NAME + " ASC";
		Cursor c = db.query(contactsDB.TABLE_NAME, projection, null, null,
				null, null, sortOrder);
		String[] from = { contactsDB.COL_NAME };
		int[] to = { R.id.contactName };
		adapter = new SimpleCursorAdapter(this, R.layout.item_layout, c, from,
				to, 0);
		mContactList.setAdapter(adapter);
		// Enable selection of items in ListView
		mContactList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				Cursor mycursor = (Cursor)listView.getItemAtPosition(position);
				int ID = Integer.parseInt(mycursor.getString(0));
				Intent i = new Intent(MainActivity.this, ContactInfo.class);
				i.putExtra("com.example.contacts.ID", ID);
				finish();
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_action, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			openSearch();
			return true;
		case R.id.add_contact:
			newContact();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void newContact() {
		Intent i = new Intent(this, AddContact.class);
		startActivity(i);
		finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		adapter.notifyDataSetChanged();
		mContactList.setAdapter(adapter);
	}

	protected void openSearch() {

	}

}
