package software.credible.commentslistapp;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

public class CommentsActivity extends ListActivity {

    private CommentsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        datasource = new CommentsDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return delete(parent, view, position, id);
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
        String commentText = ((Button) view).getText().toString();
        if (!TextUtils.isEmpty(commentText)) {
            ArrayAdapter<Comment> adapter = getListAdapter();
            Comment comment = null;
            comment = datasource.createComment(commentText);
            adapter.add(comment);
            adapter.notifyDataSetChanged();
        }
    }

    public boolean delete (AdapterView<?> parent, View view, int position, long id) {
        boolean deleted = false;
        if (getListAdapter().getCount() > 0) {
            ArrayAdapter<Comment> adapter = getListAdapter();
            Comment comment = adapter.getItem(position);
            datasource.deleteComment(comment);
            adapter.remove(comment);
            deleted = true;
        }
        return deleted;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayAdapter<Comment> getListAdapter() {
        return (ArrayAdapter<Comment>) super.getListAdapter();
    }
}
