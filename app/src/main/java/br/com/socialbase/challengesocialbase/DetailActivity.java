package br.com.socialbase.challengesocialbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import socialbase.com.br.challengesocialbase.R;
import br.com.socialbase.challengesocialbase.model.Post;
import br.com.socialbase.challengesocialbase.util.Constants;

public class DetailActivity extends BaseActivity implements Constants{

    private Post post;
    private ImageView img;
    private TextView txtUsername, txtTitle, txtSubject, txtType, txtCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        post = (Post) getIntent().getSerializableExtra(KEY_DETAIL);
        setActionBar(post.getTitle());
        setHomeButtonEnable(true);

        img = (ImageView) findViewById(R.id.img);
        if (post.getImage() != null && post.getImage().getMedium() != null) {
            Picasso.with(this)
                    .load(post.getImage().getMedium())
                    .placeholder(R.drawable.placeholder)
                    .into(img);
        } else {
            img.setVisibility(View.GONE);
        }

        txtUsername = (TextView) findViewById(R.id.txt_username);
        txtUsername.setText(getString(R.string.username) + ": " + post.getUsername());

        txtTitle= (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getString(R.string.title) + ": " + post.getTitle());

        txtSubject= (TextView) findViewById(R.id.txt_subject);
        txtSubject.setText(getString(R.string.subject) + ": " + post.getSubject());

        txtType = (TextView) findViewById(R.id.txt_type);
        txtType.setText(getString(R.string.type) + ": " + post.getType());

        txtCategory= (TextView) findViewById(R.id.txt_category);
        txtCategory.setText(getString(R.string.category) + ": " + post.getCategory());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, WebActivity.class);
                intent.putExtra(KEY_TITLE, post.getTitle());
                intent.putExtra(KEY_URL, post.getUrl());
                startActivity(intent);
            }
        });
    }
}
