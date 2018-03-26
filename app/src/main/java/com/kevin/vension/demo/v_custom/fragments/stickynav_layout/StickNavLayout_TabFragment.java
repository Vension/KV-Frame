package com.kevin.vension.demo.v_custom.fragments.stickynav_layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevin.vension.demo.R;
import com.kevin.vension.demo.models.TestEntity;
import com.kevin.vension.demo.v_custom.adapters.RecyStickyLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

public class StickNavLayout_TabFragment extends Fragment
{
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private RecyclerView mRecyclerView;
    // private TextView mTextView;
    private List<TestEntity> mDatas = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_test_sticky_nav_tab, container, false);
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.id_stickynavlayout_innerscrollview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // mTextView = (TextView) view.findViewById(R.id.id_info);
        // mTextView.setText(mTitle);
        for (int i = 0; i < 50; i++) {
            TestEntity _Entity = new TestEntity(i+1,mTitle + " -> " + i);
            mDatas.add(_Entity);
        }

        mRecyclerView.setAdapter(new RecyStickyLayoutAdapter(mDatas));
        return view;

    }

    public static StickNavLayout_TabFragment newInstance(String title)
    {
        StickNavLayout_TabFragment tabFragment = new StickNavLayout_TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

}
