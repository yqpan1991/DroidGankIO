package com.edus.gankio;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edus.gankio.ui.fragment.FragmentFactory;
import com.edus.gankio.ui.adapter.HomeFragmentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panda on 2018/10/17.
 */

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void setupViewPager(ViewPager viewPager) {
        List<PageInfo> pageInfoList = new ArrayList<>();
        pageInfoList.add(buildPageInfo(R.string.dg_title_tab_home, HomeFragmentType.HOME));
        pageInfoList.add(buildPageInfo(R.string.dg_title_tab_android, HomeFragmentType.ANDROID));
        pageInfoList.add(buildPageInfo(R.string.dg_title_tab_ios, HomeFragmentType.IOS));
        pageInfoList.add(buildPageInfo(R.string.dg_title_tab_front, HomeFragmentType.FRONT));
        pageInfoList.add(buildPageInfo(R.string.dg_title_tab_recommend, HomeFragmentType.RECOMMEND));
        pageInfoList.add(buildPageInfo(R.string.dg_title_tab_bonus, HomeFragmentType.BONUS));
        pageInfoList.add(buildPageInfo(R.string.dg_title_tab_app, HomeFragmentType.APP));
        pageInfoList.add(buildPageInfo(R.string.dg_title_tab_videos, HomeFragmentType.VIDEO));
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(this, getSupportFragmentManager(), pageInfoList);
        viewPager.setOffscreenPageLimit(pageInfoList.size());
        viewPager.setAdapter(homePagerAdapter);
    }

    private PageInfo buildPageInfo(int titleRes, HomeFragmentType homeFragmentType){
        return new PageInfo(titleRes, homeFragmentType);
    }

    private static class PageInfo{
        private int titleRes;
        private HomeFragmentType fragmentType;

        public PageInfo(int titleRes, HomeFragmentType fragmentType){
            this.titleRes = titleRes;
            this.fragmentType = fragmentType;
        }

        public int getTitleRes() {
            return titleRes;
        }

        public HomeFragmentType getFragmentType() {
            return fragmentType;
        }
    }

    private static class HomePagerAdapter extends FragmentPagerAdapter{
        private List<PageInfo> mPageInfoList = new ArrayList<>();
        private Context mContext;

        public HomePagerAdapter(Context context, FragmentManager fm, List<PageInfo> pageInfoList) {
            super(fm);
            if(context == null){
                throw new RuntimeException("Context cannot be null");
            }
            mContext = context;
            if(pageInfoList != null && !pageInfoList.isEmpty()){
                mPageInfoList.addAll(pageInfoList);
            }
        }

        @Override
        public Fragment getItem(int position) {
            PageInfo pageInfo = null;
            if(position >= 0 && position < mPageInfoList.size()){
                pageInfo = mPageInfoList.get(position);
            }
            if(pageInfo == null){
                pageInfo = new PageInfo(R.string.dg_title_tab_error, HomeFragmentType.ERROR);
            }
            return FragmentFactory.getHomeFragment(pageInfo.getFragmentType());
        }

        @Override
        public int getCount() {
            return mPageInfoList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            PageInfo pageInfo = null;
            if(position >= 0 && position < mPageInfoList.size()){
                pageInfo = mPageInfoList.get(position);
            }
            if(pageInfo == null){
                pageInfo = new PageInfo(R.string.dg_title_tab_error, HomeFragmentType.ERROR);
            }
            return mContext.getResources().getString(pageInfo.getTitleRes());
        }
    }



    private static class MyPagerAdapter extends PagerAdapter{
        private Context mContext;

        public MyPagerAdapter(Context context){
            mContext = context;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(mContext);
            textView.setText(String.valueOf(position));
            container.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return textView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(object instanceof View){
                container.removeView((View) object);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }
    }

}
