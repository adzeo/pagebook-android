package com.example.pagebook.ui.fragments.notification;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.Notification;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.fragments.notification.adapter.NotificationsRecyclerViewAdapter;
import com.example.pagebook.ui.fragments.notification.network.INotificationApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class NotificationFragment extends Fragment {

    User myUser = UserBuilder.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefreshNotificationFrag);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initApi();
                pullToRefresh.setRefreshing(false);
            }
        });

        initApi();
    }

    private void initApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        INotificationApi iNotificationApi = retrofit.create(INotificationApi.class);
        Call<List<Notification>> responses = iNotificationApi.getNotifications(myUser.getId(), getActivity().getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse (Call<List<Notification>> call, retrofit2.Response<List<Notification>> responseData) {

                if(responseData.body() != null) {

                    List<Notification> notificationList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = getView().findViewById(R.id.notification_recycle_view);
                    NotificationsRecyclerViewAdapter notificationsRecyclerViewAdapter = new NotificationsRecyclerViewAdapter(notificationList);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(notificationsRecyclerViewAdapter);
                }
                else {
                    Toast.makeText(getContext(), "Unable To Retrieve Notifications", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<List<Notification>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}