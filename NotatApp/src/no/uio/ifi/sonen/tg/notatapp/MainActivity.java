package no.uio.ifi.sonen.tg.notatapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	ArrayList<Note> notes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		notes = new ArrayList<Note>();
		notes.add(new Note("Android 101", "Teach people how to make an app..."));
		notes.add(new Note("Android 202", "What to teach..."));
		notes.add(new Note("Games to play", "OpenTTD and Quakeworld"));
		notes.add(new Note("Games to code", "Awesome games needs to be made"));
		notes.add(new Note("Groceries", "Milk, egg, bread, butter, jam, cheese"));
		setListAdapter(new CustomAdapter(this, R.layout.row_note, notes));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, NoteActivity.class);
		intent.putExtra(Note.TITLE, notes.get(position).title);
		intent.putExtra(Note.NOTE, notes.get(position).note);
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
			builder.setPositiveButton(R.string.action_save_node,
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							String title = ((EditText) layout.findViewById(R.id.dialog_title)).getText()
									.toString();
							String note = ((EditText) layout.findViewById(R.id.dialog_note)).getText()
									.toString();
							notes.add(new Note(title, note));
							((CustomAdapter) getListAdapter()).notifyDataSetChanged();
						}
					});
			builder.setNegativeButton(R.string.action_cancel, new OnClickListener() {
				
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
}
