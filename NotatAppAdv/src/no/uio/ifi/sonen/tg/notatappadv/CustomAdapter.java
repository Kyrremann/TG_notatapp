package no.uio.ifi.sonen.tg.notatappadv;

import java.util.ArrayList;

import no.uio.ifi.sonen.tg.notatapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Note> {

	ArrayList<Note> notes;
	int textViewResourceId;
	Context context;

	public CustomAdapter(Context context, int textViewResourceId,
			ArrayList<Note> notes) {
		super(context, textViewResourceId, notes);
		this.notes = notes;
		this.textViewResourceId = textViewResourceId;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(textViewResourceId, parent, false);
			
			holder = new Holder();
			holder.title = (TextView) convertView.findViewById(R.id.note_title);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.title.setText(position + ": " + notes.get(position).title); 

		return convertView;
	}

	private class Holder {
		TextView title;
	}

}
