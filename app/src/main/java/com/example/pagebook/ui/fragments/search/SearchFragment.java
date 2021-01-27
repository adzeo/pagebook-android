package com.example.pagebook.ui.fragments.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.pagebook.R;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //search bar functionality
        SearchView searchView = getActivity().findViewById(R.id.searchView_friends);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("myTag", "Calling Friend Search for query = " + query);
                Bundle bundle = new Bundle();
                bundle.putString("searchFriendQuery", query);
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                searchResultFragment.setArguments(bundle);
                loadFragment(searchResultFragment);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        SearchView searchView = getView().findViewById(R.id.searchView_friends);
        searchView.clearFocus();

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.friends_search_fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}