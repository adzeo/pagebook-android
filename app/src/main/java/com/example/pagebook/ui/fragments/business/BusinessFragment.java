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
import com.example.pagebook.models.BusinessDTO;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.fragments.business.adapter.BusinessesFollowingRecycleViewAdapter;
import com.example.pagebook.ui.fragments.business.network.IBusinessProfileApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class BusinessFragment extends Fragment implements BusinessesFollowingRecycleViewAdapter.UserDataInterface {

    User myUser = UserBuilder.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_add_business).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BusinessRegistrationActivity.class);
            startActivity(intent);
        });

        view.findViewById(R.id.btn_follow_business).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BusinessSearchActivity.class);
            startActivity(intent);
        });

        initApi();
    }

    @Override
    public void onUserClick(BusinessDTO business) {
        Intent intent = new Intent(getContext(), BusinessProfilePageActivity.class);
        intent.putExtra("businessProfileId", business.getId());
        startActivity(intent);
    }

    private void initApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);
        Call<List<BusinessDTO>> responses = iBusinessProfileApi.getBusinessPagesFollowingList(myUser.getId(), getActivity().getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<List<BusinessDTO>>() {
            @Override
            public void onResponse(Call<List<BusinessDTO>> call, retrofit2.Response<List<BusinessDTO>> responseData) {

                if (responseData.body() != null) {

                    List<BusinessDTO> friendsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = getView().findViewById(R.id.business_following_recycle_view);
                    BusinessesFollowingRecycleViewAdapter businessesFollowingRecycleViewAdapter = new BusinessesFollowingRecycleViewAdapter(friendsList, BusinessFragment.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(businessesFollowingRecycleViewAdapter);
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BusinessDTO>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}