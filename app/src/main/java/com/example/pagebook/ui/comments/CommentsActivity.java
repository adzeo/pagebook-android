package com.example.pagebook.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.Comment;
import com.example.pagebook.models.CommentDTO;
import com.example.pagebook.models.PushNotificationRequest;
import com.example.pagebook.models.PushNotificationResponse;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.network.IPushNotificationApi;
import com.example.pagebook.networkmanager.NotificationRetrofitBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.comments.adapter.CommentsRecyclerViewAdapter;
import com.example.pagebook.ui.comments.network.ICommentsApi;
import com.example.pagebook.ui.friendsprofile.FriendsProfileActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentsActivity extends AppCompatActivity {

    User myUser = UserBuilder.getInstance();

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
            comment.setApproved(false);
            comment.setParentCommentId(getIntent().getStringExtra("parentCommentId"));
            comment.setPostId(getIntent().getStringExtra("postId"));
            comment.setText(etComment.getText().toString());
            comment.setUserId(myUser.getId());

            Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
            ICommentsApi iCommentsApi = retrofit.create(ICommentsApi.class);
            Call<Comment> responses = iCommentsApi.addComment(comment, getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
            responses.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse (Call<Comment> call, retrofit2.Response<Comment> responseData) {
                    if(responseData.body() != null) {
                        Toast.makeText(CommentsActivity.this, "Comment Saved", Toast.LENGTH_SHORT).show();
                        etComment.setText("");
                        initApi();
                        initNotifApi();
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

    private void initNotifApi() {
        PushNotificationRequest notificationRequest=new PushNotificationRequest();
        notificationRequest.setTitle("PageBook");
        notificationRequest.setMessage(myUser.getFirstName()+" commented on your post.");
//        notificationRequest.setTopic("batman");
        notificationRequest.setTopic(getIntent().getStringExtra("postUserId"));
        notificationRequest.setToken(getSharedPreferences("com.example.pagebook",Context.MODE_PRIVATE).getString("FCMToken",""));
        Retrofit retrofit = NotificationRetrofitBuilder.getInstance();
        IPushNotificationApi iPushNotificationApi = retrofit.create(IPushNotificationApi.class);
        Map<String,String> data=new HashMap<>();
        notificationRequest.setData(data);
        Call<PushNotificationResponse> responseCall=iPushNotificationApi.sendNotification(notificationRequest,getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responseCall.enqueue(new Callback<PushNotificationResponse>() {
            @Override
            public void onResponse(Call<PushNotificationResponse> call, Response<PushNotificationResponse> response) {
                if(response.body()!=null)
                {
                    Log.d("Notification Response",response.body().getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<PushNotificationResponse> call, Throwable t) {

                Log.d("Notification Response", t.getMessage());
            }
        });
    }

    private void initApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        ICommentsApi iCommentsApi = retrofit.create(ICommentsApi.class);
        Call<List<CommentDTO>> responses = iCommentsApi.getPostComments(getIntent().getStringExtra("parentCommentId"), getIntent().getStringExtra("postId"), getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
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
                    commentsRecyclerViewAdapter.notifyDataSetChanged();
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