package com.edus.gankio.ui.adapter;

import com.edus.gankio.ui.SampleFragment;

/**
 * Created by panda on 2018/10/21.
 */

public class FragmentFactory {
    public static SampleFragment getHomeFragment(HomeFragmentType homeFragmentType){
        /**
         *  HOME,
         ANDROID,
         IOS,
         RECOMMEND,
         APP,
         VIDEO
         * */
        if(homeFragmentType == HomeFragmentType.HOME){
            return SampleFragment.getInstance(null);
        }else if(homeFragmentType == HomeFragmentType.ANDROID){
            return SampleFragment.getInstance(null);
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
