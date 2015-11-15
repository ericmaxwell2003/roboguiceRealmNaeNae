package software.credible.naenaelistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LyricDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_LYRIC};

	public LyricDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Lyric createLyric(String lyric) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_LYRIC, lyric);
		long insertId = database.insert(MySQLiteHelper.TABLE_LYRICS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_LYRICS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Lyric newLyric = cursorToLyric(cursor);
		cursor.close();
		return newLyric;
	}

	public void deleteLyric(Lyric lyric) {
		long id = lyric.getId();
		System.out.println("Lyric deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_LYRICS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
	}

    public void deleteAllLyrics() {
       database.delete(MySQLiteHelper.TABLE_LYRICS, null, null);
    }

	public Lyric getLyricByValue(String lyricText) {
		Lyric lyric = null;
		Cursor cursor = database.query(MySQLiteHelper.TABLE_LYRICS,
				allColumns, MySQLiteHelper.COLUMN_LYRIC + " = '" + lyricText + "'" , null, null, null, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			lyric = cursorToLyric(cursor);
		}
		// Make sure to close the cursor
		cursor.close();
		return lyric;
	}

	public List<Lyric> getAllLyrics() {
		List<Lyric> lyrics = new ArrayList<Lyric>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_LYRICS,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Lyric lyric = cursorToLyric(cursor);
			lyrics.add(lyric);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return lyrics;
	}

	private Lyric cursorToLyric(Cursor cursor) {
		Lyric lyric = new Lyric();
		lyric.setId(cursor.getLong(0));
		lyric.setLyricText(cursor.getString(1));
		return lyric;
	}
}
