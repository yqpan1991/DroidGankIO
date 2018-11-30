package apollo.edus.com.share;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.message.TextMessage;
import apollo.edus.com.share.unit.BaseShareUnit;

/**
 * ShareInterface
 *
 * @author panda
 */
public abstract class ShareInterface {

    /**
     * 分享文本
     * @param activity
     * @param textMessage
     */
    public void shareText(AppCompatActivity activity, TextMessage textMessage){

    }

    /**
     * 分享消息
     * @param activity
     * @param shareUnitList
     * @param shareMessage
     */
    public void shareMessage(AppCompatActivity activity, List<BaseShareUnit> shareUnitList, BaseShareMessage shareMessage){

    }

    public static ShareInterface getImpl(){
        return ShareFactory.getInstance().getService(ShareInterface.class);
    }

}
