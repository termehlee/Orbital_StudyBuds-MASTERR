package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.core.users.getall.GetUsersContract;
import com.example.myapplication.core.users.getall.GetUsersPresenter;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.activities.ChatActivity;
import com.example.myapplication.ui.adapters.UserListingRecyclerAdapter;
import com.example.myapplication.utils.ItemClickSupport;

import java.util.List;



public class UsersFragment extends Fragment implements GetUsersContract.View, ItemClickSupport.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String ARG_TYPE = "type";
    public static final String TYPE_CHATS = "type_chats";
    public static final String TYPE_ALL = "type_all";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewAllUserListing;
    private LinearLayoutManager layoutManager;

    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;

    private GetUsersPresenter mGetUsersPresenter;

    public static Fragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_users, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewAllUserListing = view.findViewById(R.id.recycler_view_all_user_listing);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mGetUsersPresenter = new GetUsersPresenter(this);
        getUsers();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        ItemClickSupport.addTo(mRecyclerViewAllUserListing)
                .setOnItemClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getUsers();
    }

    private void getUsers() {
        if (TextUtils.equals(getArguments().getString(ARG_TYPE), TYPE_CHATS)) {

        } else if (TextUtils.equals(getArguments().getString(ARG_TYPE), TYPE_ALL)) {
            mGetUsersPresenter.getAllUsers();
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        ChatActivity.startActivity(getActivity(),
                mUserListingRecyclerAdapter.getUser(position).email,
                mUserListingRecyclerAdapter.getUser(position).uid,
                mUserListingRecyclerAdapter.getUser(position).firebaseToken);
    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mRecyclerViewAllUserListing.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewAllUserListing.setHasFixedSize(true);
        mUserListingRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetChatUsersSuccess(List<User> users) {

    }

    @Override
    public void onGetChatUsersFailure(String message) {

    }

}

