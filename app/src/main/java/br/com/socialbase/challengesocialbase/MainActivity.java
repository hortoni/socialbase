package br.com.socialbase.challengesocialbase;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.socialbase.challengesocialbase.component.DatabaseHelper;
import br.com.socialbase.challengesocialbase.component.PostListAdapter;
import br.com.socialbase.challengesocialbase.model.Post;
import br.com.socialbase.challengesocialbase.util.ConnectionUtil;
import br.com.socialbase.challengesocialbase.util.Constants;
import br.com.socialbase.challengesocialbase.webservice.AsyncReceiveRequest;
import br.com.socialbase.challengesocialbase.webservice.WsResult;

public class MainActivity extends BaseActivity {

    private ListView listview;
    private PostListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private AsyncReceiveRequest async = null;
    private DatabaseHelper databaseHelper;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_menu_item).getActionView();
        searchView .setQueryHint(getString(R.string.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query.toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText.toString());
                }
                return true;
            }
        });


        return true;
    }

}
