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
            return HomePageAndroidFragment.getInstance(null);
//            return SampleFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.IOS){
            return SampleFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.RECOMMEND){
            return SampleFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.APP){
            return SampleFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.VIDEO){
            return SampleFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.ERROR){
            return SampleFragment.getInstance(null);
        }else{
            return SampleFragment.getInstance(null);
        }
    }
}
