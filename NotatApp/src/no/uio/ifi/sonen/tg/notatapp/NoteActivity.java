package no.uio.ifi.sonen.tg.notatapp;

import android.os.Bundle;
import android.app.Activity;
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
}
