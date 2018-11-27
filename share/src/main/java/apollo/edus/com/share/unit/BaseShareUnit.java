package apollo.edus.com.share.unit;

import android.app.Activity;
import android.text.TextUtils;

import apollo.edus.com.share.IShareCallback;
import apollo.edus.com.share.ShareConstants;
import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.message.ImageMessage;
import apollo.edus.com.share.message.LinkMessage;
import apollo.edus.com.share.message.TextMessage;

/**
 * 分享的插件
 *
 * @author panda
 */
public abstract class BaseShareUnit implements IShareUnit {
    private UnitInfo mUnitInfo;

    public BaseShareUnit(UnitInfo unitInfo){
        mUnitInfo = unitInfo;
    }

    @Override
    public String getPluginKey() {
        return getClass().getCanonicalName();
    }

    @Override
    public final boolean isSupported(Activity activity,BaseShareMessage shareMessage) {
        if(shareMessage == null || mUnitInfo == null || TextUtils.isEmpty(mUnitInfo.getPkgId())){
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
        if(!isSupported(activity, shareMessage)){
            notifyFailed(callback, shareMessage, ShareConstants.PARAMS_INVALID, null);
            return;
        }
        if(shareMessage == null){
            notifyFailed(callback, shareMessage, ShareConstants.PARAMS_INVALID, null);
            return;
        }
        if(shareMessage instanceof TextMessage){
            shareText(activity, (TextMessage) shareMessage, callback);
        }else if(shareMessage instanceof ImageMessage){
            shareImage(activity, (ImageMessage) shareMessage, callback);
        }else if(shareMessage instanceof LinkMessage){
            shareLink(activity, (LinkMessage) shareMessage, callback);
        }else{
            callback.onShareFailed(this, shareMessage, ShareConstants.NOT_SUPPORTED ,null);
        }
    }

    protected abstract boolean isSupportText(Activity activity, TextMessage message);
    protected abstract boolean isSupportImage(Activity activity, ImageMessage message);
    protected abstract boolean isSupportLink(Activity activity, LinkMessage message);

    protected abstract void shareText(Activity activity, TextMessage shareMessage, IShareCallback shareCallback);

    protected abstract void shareImage(Activity activity, ImageMessage shareMessage, IShareCallback shareCallback);

    protected abstract void shareLink(Activity activity, LinkMessage shareMessage, IShareCallback shareCallback);

    protected void notifyStart(IShareCallback callback, BaseShareMessage shareMessage){
        if(callback != null){
            callback.onStartShare(this, shareMessage);
        }
    }

    protected void notifySucceed(IShareCallback callback, BaseShareMessage shareMessage){
        if(callback != null){
            callback.onShareSucceed(this, shareMessage);
        }
    }

    protected void notifyFailed(IShareCallback callback, BaseShareMessage shareMessage, String code, String reason){
        if(callback != null){
            callback.onShareFailed(this, shareMessage, code, reason);
        }
    }

    public UnitInfo getUnitInfo(){
        return mUnitInfo;
    }

}
