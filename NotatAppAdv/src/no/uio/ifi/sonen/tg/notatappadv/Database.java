package no.uio.ifi.sonen.tg.notatappadv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	private Context context;
	private static final int DATABASE_VERSION = 1;
	private static final String DB_NAME = "Note";

	private static final String NOTE_TABLE_NAME = "NOTES";
	public static final String NOTE_TITLE = "_id";
	public static final String NOTE_NOTE = "NOTE";

	private static final String NOTE_CREATE = "CREATE TABLE " + NOTE_TABLE_NAME
			+ " (" + NOTE_TITLE + " TEXT, " + NOTE_NOTE + " TEXT);";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(NOTE_CREATE);
			populate(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("UPGRADE", "Upgrading database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");

			// Kills the table and existing data
			db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE_NAME);

			// Recreates the database with a new version
			onCreate(db);
		}

		public void populate(SQLiteDatabase db) {
			// NOTES
			db.execSQL("INSERT INTO "
					+ NOTE_TABLE_NAME
					+ " VALUES ('Android 101', 'Teach people how to make an app...')");
			db.execSQL("INSERT INTO " + NOTE_TABLE_NAME
					+ " VALUES ('Android 202', 'What to teach...')");
			db.execSQL("INSERT INTO " + NOTE_TABLE_NAME
					+ " VALUES ('Games to play', 'OpenTTD and Quakeworld')");
			db.execSQL("INSERT INTO "
					+ NOTE_TABLE_NAME
					+ " VALUES ('Games to code', 'Awesome games needs to be made')");
			db.execSQL("INSERT INTO "
					+ NOTE_TABLE_NAME
					+ " VALUES ('Groceries', 'Milk, egg, bread, butter, jam, cheese')");
		}
	}

	public Database(Context context) {
		this.context = context;
	}

	public Database open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		if (db.getVersion() != DATABASE_VERSION)
			dbHelper.onUpgrade(db, db.getVersion(), DATABASE_VERSION);
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public boolean isOpen() {
		return db.isOpen();
	}

	public Cursor getNote(String title) {
		return db.query(NOTE_TABLE_NAME, new String[] { NOTE_NOTE }, NOTE_TITLE
				+ "=?", new String[] { title }, null, null, null);
	}

	public Cursor getNotes() {
		return db.query(NOTE_TABLE_NAME,
				new String[] { NOTE_TITLE, NOTE_NOTE }, null, null, null, null,
				null);
	}

	public long putNote(String title, String note) {
		ContentValues values = new ContentValues(2);
		values.put(NOTE_TITLE, title);
		values.put(NOTE_NOTE, note);

		return db.insert(NOTE_TABLE_NAME, null, values);
	}

	public void removeNote() {
		// TODO: Remove note
	}
}
