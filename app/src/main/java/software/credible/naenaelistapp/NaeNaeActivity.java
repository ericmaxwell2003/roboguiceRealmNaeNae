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
        List<Lyric> values = datasource.getAllLyrics();

        ArrayAdapter<Lyric> adapter = new ArrayAdapter<Lyric>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return delete(position);
            }
        });
    }

    public void add(View view) {
        String lyricText = ((Button) view).getText().toString();
        if (!TextUtils.isEmpty(lyricText)) {
            ArrayAdapter<Lyric> adapter = getListAdapter();
            Lyric lyric = null;
            lyric = datasource.createLyric(lyricText);
            adapter.add(lyric);
            adapter.notifyDataSetChanged();
        }
    }

    public boolean delete (int position) {
        boolean deleted = false;
        if (getListAdapter().getCount() > 0) {
            ArrayAdapter<Lyric> adapter = getListAdapter();
            Lyric lyric = adapter.getItem(position);
            datasource.deleteLyric(lyric);
            adapter.remove(lyric);
            deleted = true;
        }
        return deleted;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayAdapter<Lyric> getListAdapter() {
        return (ArrayAdapter<Lyric>) super.getListAdapter();
    }
}
