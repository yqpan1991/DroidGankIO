package apollo.edus.com.share;

import android.support.v7.app.AppCompatActivity;

import apollo.edus.com.share.message.TextMessage;

/**
 * ShareInterface
 *
 * @author panda
 */
public abstract class ShareInterface {

    public void shareText(AppCompatActivity activity, TextMessage textMessage){

    }

    public static ShareInterface getImpl(){
        return ShareFactory.getInstance().getService(ShareInterface.class);
    }

}
