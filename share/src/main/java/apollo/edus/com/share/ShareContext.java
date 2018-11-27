package apollo.edus.com.share;

import android.content.Context;

import apollo.edus.com.share.utils.Singleton;

/**
 * Description.
 *
 * @author panda
 */
public class ShareContext {

    private static Singleton<ShareContext> sSingleton = new Singleton<ShareContext>() {
        @Override
        protected ShareContext create() {
            return new ShareContext();
        }
    };

    private Context mContext;
    private ShareContext(){
    }

    /**
     * should init on ApplicationCreate
     * @param context
     */
    public void init(Context context){
        if(context == null){
            throw new RuntimeException("context cannot be null");
        }
        mContext = context.getApplicationContext();
    }

    public static ShareContext getInstance(){
        return sSingleton.get();
    }

    public Context getContext(){
        return mContext;
    }

}
