package socialbase.com.br.challengesocialbase;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import socialbase.com.br.challengesocialbase.component.DatabaseHelper;
import socialbase.com.br.challengesocialbase.component.PostListAdapter;
import socialbase.com.br.challengesocialbase.model.Post;
import socialbase.com.br.challengesocialbase.util.ConnectionUtil;
import socialbase.com.br.challengesocialbase.util.Constants;
import socialbase.com.br.challengesocialbase.webservice.AsyncReceiveRequest;
import socialbase.com.br.challengesocialbase.webservice.WsResult;

public class MainActivity extends BaseActivity {

    private ListView listview;
    private PostListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private AsyncReceiveRequest async = null;
    private DatabaseHelper databaseHelper;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();

        listview = (ListView) findViewById(R.id.listview);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPostsFromServer();
            }
        });

        edtSearch = (EditText) findViewById(R.id.edt_search);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        databaseHelper = DatabaseHelper.getInstance(this);
        getPostsFromServer();
    }


    private void getPostsFromServer() {
        if (ConnectionUtil.isConnected(this)) {
            swipeLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeLayout.setRefreshing(true);
                }
            });

            async = new AsyncReceiveRequest(this) {
                @Override
                public void onExecutionCompleted(WsResult result) {
                    if (swipeLayout != null && swipeLayout.isRefreshing()) {
                        swipeLayout.setRefreshing(false);
                    }

                    if (result.getResponse().isStatusGood()) {
                        ArrayList<Post> list = result.getPosts();
                        adapter = new PostListAdapter(MainActivity.this, R.layout.layout_post_listitem, list);
                        listview.setAdapter(adapter);
                        addPosts(result.getPosts());
                    }
                }
            };

            async.prepareToReceive(Constants.EXPECTED_RESULT_POSTS, null, false);
            async.execute();
        } else {
            List<Post> posts = databaseHelper.getAllPosts();
            adapter = new PostListAdapter(MainActivity.this, R.layout.layout_post_listitem, (ArrayList) posts);
            listview.setAdapter(adapter);

        }
    }

    private void addPosts(List<Post> posts) {
        databaseHelper.deleteAllPosts();
        for (Post post: posts) {
            databaseHelper.addPost(post);
        }
    }

}
