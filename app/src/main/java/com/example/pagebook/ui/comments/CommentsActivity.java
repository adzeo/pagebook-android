package com.example.pagebook.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.Comment;
import com.example.pagebook.models.CommentDTO;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.comments.adapter.CommentsRecyclerViewAdapter;
import com.example.pagebook.ui.comments.network.ICommentsApi;
import com.example.pagebook.ui.fragments.homefeed.HomeFeedFragment;
import com.example.pagebook.ui.fragments.homefeed.adapter.HomeFeedRecyclerViewAdapter;
import com.example.pagebook.ui.fragments.homefeed.network.IPostsApi;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class CommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_comments);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextInputLayout tvCommentLayout = findViewById(R.id.tv_comments_name);
        TextInputEditText etComment = findViewById(R.id.et_comment);

        tvCommentLayout.setEndIconOnClickListener(v -> {
            Comment comment = new Comment();
//            comment;

            Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
            ICommentsApi iCommentsApi = retrofit.create(ICommentsApi.class);
            Call<Comment> responses = iCommentsApi.addComment(comment);
            responses.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse (Call<Comment> call, retrofit2.Response<Comment> responseData) {

                    if(responseData.body() != null) {

                        Comment commentDTOList = responseData.body();
                    }
                    else {
                        finish();
                    }
                }

                @Override
                public void onFailure (Call<Comment> call, Throwable t) {
                    Toast.makeText(CommentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        });

        initApi();


    }

    private void initApi() {
        //api call for myFeedPosts
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        ICommentsApi iCommentsApi = retrofit.create(ICommentsApi.class);

        // TODO: Implement Pagination
        Call<List<CommentDTO>> responses = iCommentsApi.getPostComments(getIntent().getStringExtra("parentCommentId"), getIntent().getStringExtra("postId"));
        responses.enqueue(new Callback<List<CommentDTO>>() {
            @Override
            public void onResponse (Call<List<CommentDTO>> call, retrofit2.Response<List<CommentDTO>> responseData) {

                if(responseData.body() != null) {

                    List<CommentDTO> commentDTOList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = findViewById(R.id.comments_recycle_view);
                    CommentsRecyclerViewAdapter commentsRecyclerViewAdapter = new CommentsRecyclerViewAdapter(commentDTOList, CommentsActivity.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
                    recyclerView.setAdapter(commentsRecyclerViewAdapter);
                }
                else {
                    finish();
                }
            }

            @Override
            public void onFailure (Call<List<CommentDTO>> call, Throwable t) {
                Toast.makeText(CommentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}