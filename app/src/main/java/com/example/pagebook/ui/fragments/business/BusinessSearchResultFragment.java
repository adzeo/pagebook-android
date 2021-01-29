package com.example.pagebook.ui.fragments.business;

import android.content.Context;
import android.content.Intent;
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
import com.example.pagebook.ui.fragments.business.adapter.SearchBusinessRecyclerViewAdapter;
import com.example.pagebook.models.SearchBusinesses;
import com.example.pagebook.ui.fragments.business.network.ISearchBusinessApi;
import com.example.pagebook.ui.friendsprofile.FriendsProfileActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class BusinessSearchResultFragment extends Fragment implements SearchBusinessRecyclerViewAdapter.UserDataInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        String searchBusinessQuery = "";
        if (bundle != null) {
            searchBusinessQuery = bundle.getString("searchBusinessQuery");
        }
        initApi(searchBusinessQuery);
    }

    @Override
    public void onUserClick(SearchBusinesses searchBusiness) {
        Intent intent = new Intent(getContext(), BusinessProfilePageActivity.class);
        intent.putExtra("businessProfileId", searchBusiness.getId());
        startActivity(intent);
    }

    private void initApi(String searchBusinessQuery) {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        ISearchBusinessApi iSearchBusinessApi = retrofit.create(ISearchBusinessApi.class);
        Call<List<SearchBusinesses>> responses = iSearchBusinessApi.getSearchBusiness(searchBusinessQuery, getActivity().getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<List<SearchBusinesses>>() {
            @Override
            public void onResponse(Call<List<SearchBusinesses>> call, retrofit2.Response<List<SearchBusinesses>> responseData) {

                if (responseData.body() != null) {

                    List<SearchBusinesses> searchBusinessList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = getView().findViewById(R.id.search_result_recycle_view);
                    SearchBusinessRecyclerViewAdapter searchBusinessRecyclerViewAdapter = new SearchBusinessRecyclerViewAdapter(searchBusinessList, BusinessSearchResultFragment.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(searchBusinessRecyclerViewAdapter);
                    searchBusinessRecyclerViewAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SearchBusinesses>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}