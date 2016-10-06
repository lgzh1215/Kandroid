package moe.lpj.kandroid.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import moe.lpj.kandroid.R;
import moe.lpj.kandroid.utils.UtilsA;

public class HomeFragment extends Fragment {

    private MainActivity mActivity;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must be MainActivity");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = UtilsA.findViewById(view, R.id.viewpager);
        mTabLayout = mActivity.getTabLayout();
        setupViewPager();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setupViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("Page One");
        titles.add("Page Two");
        titles.add("Page Three");
        titles.add("Page Three");
        titles.add("Page Three");
        titles.add("Page Three");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(4)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(5)));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeOverviewFragment());
        fragments.add(new HomeOverviewFragment());
        fragments.add(new HomeOverviewFragment());
        fragments.add(new HomeOverviewFragment());
        fragments.add(new HomeOverviewFragment());
        fragments.add(new HomeOverviewFragment());
        MyAdepter adapter =
                new MyAdepter(mActivity.getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class MyAdepter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public MyAdepter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}