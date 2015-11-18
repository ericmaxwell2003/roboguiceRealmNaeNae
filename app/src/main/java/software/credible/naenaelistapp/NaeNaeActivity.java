package software.credible.naenaelistapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import roboguice.activity.RoboListActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_naenae)
public class NaeNaeActivity extends RoboListActivity {

    @Inject private LyricDataSource datasource;
    @Inject private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Lyric> values = new ArrayList<>(datasource.getAllLyrics());

        ArrayAdapter<Lyric> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return delete(position);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void add(View view) {
        String lyricText = ((Button) view).getText().toString();
        if (!TextUtils.isEmpty(lyricText)) {
            ArrayAdapter<Lyric> adapter = getListAdapter();
            Lyric lyric = datasource.createLyric(lyricText);
            lyric = addToStringMethod(lyric);
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

    /**
     * Temporary add to lyric to add a toString to a detatched version to insert into the array
     * adapter, so that we can continue to not write a custom view/adapter for the ListView.
     * In a real world app, we would probably use our own custom item view and adapter.
     * @param lyric Lyric object
     * @return lyric a subclass of lyric that has a toString() method.
     */
    private Lyric addToStringMethod(Lyric lyric) {
        Lyric augmentedLyric = new Lyric(){
            @Override
            public String toString() {
                return getLyricText();
            }
        };
        augmentedLyric.setId(lyric.getId());
        augmentedLyric.setLyricText(lyric.getLyricText());
        return augmentedLyric;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayAdapter<Lyric> getListAdapter() {
        return (ArrayAdapter<Lyric>) super.getListAdapter();
    }
}
