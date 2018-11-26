package apollo.edus.com.share.plugin;

import android.app.Activity;

import apollo.edus.com.share.IShareCallback;
import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.message.ImageMessage;
import apollo.edus.com.share.message.LinkMessage;
import apollo.edus.com.share.message.TextMessage;

/**
 * 分享的插件
 *
 * @author panda
 */
public abstract class BaseSharePlugin implements ISharePlugin {

    @Override
    public String getPluginKey() {
        return getClass().getCanonicalName();
    }

    @Override
    public final boolean isSupported(Activity activity,BaseShareMessage shareMessage) {
        if(shareMessage == null){
            return false;
        }
        if(shareMessage instanceof TextMessage){
            return isSupportText(activity, (TextMessage) shareMessage);
        }else if(shareMessage instanceof ImageMessage){
            return isSupportImage(activity, (ImageMessage) shareMessage);
        }else if(shareMessage instanceof LinkMessage){
            return isSupportLink(activity, (LinkMessage) shareMessage);
        }
        return false;
    }

    @Override
    public void share(Activity activity, BaseShareMessage shareMessage, IShareCallback callback) {
        if(shareMessage == null){
            if(callback != null){
                //todo: 标记错误码
                callback.onshareFailed(this, shareMessage, null, null);
            }
            return;
        }
        if(shareMessage instanceof TextMessage){
            shareText(activity, (TextMessage) shareMessage, callback);
        }else if(shareMessage instanceof ImageMessage){
            shareImage(activity, (ImageMessage) shareMessage, callback);
        }else if(shareMessage instanceof LinkMessage){
            shareLink(activity, (LinkMessage) shareMessage, callback);
        }else{
            //todo: 标记错误码
            callback.onshareFailed(this, shareMessage, null ,null);
        }
    }

    protected abstract boolean isSupportText(Activity activity, TextMessage message);
    protected abstract boolean isSupportImage(Activity activity, ImageMessage message);
    protected abstract boolean isSupportLink(Activity activity, LinkMessage message);

    protected abstract void shareText(Activity activity, TextMessage shareMessage, IShareCallback shareCallback);

    protected abstract void shareImage(Activity activity, ImageMessage shareMessage, IShareCallback shareCallback);

    protected abstract void shareLink(Activity activity, LinkMessage shareMessage, IShareCallback shareCallback);
}
