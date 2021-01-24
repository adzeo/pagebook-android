package com.example.pagebook.ui.fragments.homefeed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.fragments.homefeed.adapter.HomeFeedRecyclerViewAdapter;
import com.example.pagebook.responsemodel.GenericResponse;
import com.example.pagebook.generalmodels.PostDTO;
import com.example.pagebook.ui.fragments.homefeed.network.IPostsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class HomeFeedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initApi();
    }

    private void initApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance();
        IPostsApi iPostsApi = retrofit.create(IPostsApi.class);
        Call<GenericResponse> responses = iPostsApi.getFeedPosts();
        responses.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse (Call<GenericResponse> call, retrofit2.Response<GenericResponse> postResponseData) {

                if(postResponseData.body().isStatus()) {

                    List<PostDTO> postsList = (List<PostDTO>) postResponseData.body().getBody();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = getView().findViewById(R.id.home_feed_recycle_view);
                    HomeFeedRecyclerViewAdapter homeFeedRecyclerViewAdapter = new HomeFeedRecyclerViewAdapter(postsList, HomeFeedFragment.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(homeFeedRecyclerViewAdapter);
                }
                else {
                    Toast.makeText(getContext(), postResponseData.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<GenericResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}