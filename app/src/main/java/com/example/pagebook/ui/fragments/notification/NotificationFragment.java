package com.example.pagebook.ui.fragments.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pagebook.R;

public class NotificationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: api call for notifications
//        initApi();
//    }
//
//    private void initApi() {
//        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
//        INotificationApi iNotificationApi = retrofit.create(INotificationApi.class);
//        Call<GenericResponse> responses = iNotificationApi.getNotifications();
//        responses.enqueue(new Callback<GenericResponse>() {
//            @Override
//            public void onResponse (Call<GenericResponse> call, retrofit2.Response<GenericResponse> responseData) {
//
//                if(responseData.body().isStatus()) {
//
//                    List<Notification> notificationList = (List<Notification>) responseData.body().getBody();
//
//                    //fetching the id of recycler view
//                    RecyclerView recyclerView = getView().findViewById(R.id.notification_recycle_view);
//                    NotificationsRecyclerViewAdapter notificationsRecyclerViewAdapter = new NotificationsRecyclerViewAdapter(notificationList);
//
//                    //setting Linear Layout manager in the recycler view
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                    recyclerView.setAdapter(notificationsRecyclerViewAdapter);
//                }
//                else {
//                    Toast.makeText(getContext(), responseData.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure (Call<GenericResponse> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}