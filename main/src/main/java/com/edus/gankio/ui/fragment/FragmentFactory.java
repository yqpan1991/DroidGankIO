package com.edus.gankio.ui.fragment;

import android.support.v4.app.Fragment;

import com.edus.gankio.ui.SampleFragment;
import com.edus.gankio.ui.adapter.HomeFragmentType;

/**
 * Created by panda on 2018/10/21.
 */

public class FragmentFactory {
    public static Fragment getHomeFragment(HomeFragmentType homeFragmentType){
        /**
         *  HOME,
         ANDROID,
         IOS,
         RECOMMEND,
         APP,
         VIDEO
         * */
        if(homeFragmentType == HomeFragmentType.HOME){
            return HomePageHomeFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.ANDROID){
            return HomePageMobileAndroidFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.IOS){
            return HomePageMobileIOSFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.FRONT){
            return HomePageMobileFrontFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.RECOMMEND){
            return HomePageRecommendFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.APP){
            return HomePageMobileAppFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.BONUS){
            return HomePageBonusFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.VIDEO){
            return HomePageMobileVideoFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.ERROR){
            return SampleFragment.getInstance(null);
        }else{
            return SampleFragment.getInstance(null);
        }
    }
}
