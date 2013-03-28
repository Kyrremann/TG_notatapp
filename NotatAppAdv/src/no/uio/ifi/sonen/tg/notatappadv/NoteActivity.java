package no.uio.ifi.sonen.tg.notatappadv;

import no.uio.ifi.sonen.tg.notatapp.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NoteActivity extends Activity {

	private String title, note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		Bundle bundle = getIntent().getExtras();
		title = bundle.getString(Note.TITLE);
		note = bundle.getString(Note.NOTE);

		TextView textView = (TextView) findViewById(R.id.note_title);
		textView.setText(title);

		textView = (TextView) findViewById(R.id.note_note);
		textView.setText(note);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.action_share_note) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_TEXT, title);
			intent.setType("text/plain");
			startActivity(intent);
		}

		return true;
	}
}
