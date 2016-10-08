package software.credible.naenaelistapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

public class NaeNaeActivity extends ListActivity {

    private LyricDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naenae);

        datasource = new LyricDataSource(this);
        datasource.open();

        List<Lyric> values = datasource.getAllLyrics();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Lyric> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                delete(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    public void add(View view) {
        String lyricText = ((Button) view).getText().toString();
        if (!TextUtils.isEmpty(lyricText)) {
            ArrayAdapter<Lyric> adapter = getListAdapter();
            Lyric lyric = null;
            lyric = datasource.createLyric(lyricText);
            adapter.add(lyric);
        }
    }

    public void delete (int position) {
        if (getListAdapter().getCount() > 0) {
            ArrayAdapter<Lyric> adapter = getListAdapter();
            Lyric lyric = adapter.getItem(position);
            datasource.deleteLyric(lyric);
            adapter.remove(lyric);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayAdapter<Lyric> getListAdapter() {
        return (ArrayAdapter<Lyric>) super.getListAdapter();
    }
}
