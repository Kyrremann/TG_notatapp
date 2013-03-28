package no.uio.ifi.sonen.tg.notatappadv;

import java.util.ArrayList;

import no.uio.ifi.sonen.tg.notatapp.R;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private ArrayList<Note> notes;
	private Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		database = new Database(this);
		database.open();
		setListAdapter(new SimpleCursorAdapter(this, R.layout.row_note,
				database.getNotes(), new String[] { Database.NOTE_TITLE },
				new int[] { R.id.note_title }, 0));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, NoteActivity.class);
		String title = ((TextView) v).getText().toString();
		intent.putExtra(Note.TITLE, title);
		if (!database.isOpen()) {
			database.open();
		}
		Cursor cursor = database.getNote(title);
		cursor.moveToFirst();
		intent.putExtra(Note.NOTE, cursor.getString(0));
		cursor.close();
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		if (item.getItemId() == R.id.action_add_note) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.action_add_note);
			LayoutInflater inflater = getLayoutInflater();
			final LinearLayout layout = (LinearLayout) inflater.inflate(
					R.layout.dialog_add_note, null, false);
			builder.setView(layout);
			builder.setPositiveButton(R.string.action_save_note,
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							String title = ((EditText) layout
									.findViewById(R.id.dialog_title)).getText()
									.toString();
							String note = ((EditText) layout
									.findViewById(R.id.dialog_note)).getText()
									.toString();
							if (!database.isOpen()) {
								database.open();
							}
							database.putNote(title, note);
							((SimpleCursorAdapter) getListAdapter())
									.changeCursor(database.getNotes());
						}
					});
			builder.setNegativeButton(R.string.action_cancel,
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							dialog.cancel();
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (database == null) {
			database = new Database(this);
		}

		database.open();
	}

	@Override
	protected void onStop() {
		super.onStop();
		database.close();
	}
}
