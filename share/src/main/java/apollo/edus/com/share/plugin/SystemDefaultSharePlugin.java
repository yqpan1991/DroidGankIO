package apollo.edus.com.share.plugin;

import android.app.Activity;
import android.text.TextUtils;

import apollo.edus.com.share.IShareCallback;
import apollo.edus.com.share.message.ImageMessage;
import apollo.edus.com.share.message.LinkMessage;
import apollo.edus.com.share.message.TextMessage;

/**
 * Description.
 *
 * @author panda
 */
public class SystemDefaultSharePlugin extends BaseSharePlugin {
    @Override
    protected boolean isSupportText(Activity activity, TextMessage message) {
        if(message == null || TextUtils.isEmpty(message.getText())){
            return false;
        }
        return true;
    }

    @Override
    protected boolean isSupportImage(Activity activity, ImageMessage message) {
        //todo:
        return false;
    }

    @Override
    protected boolean isSupportLink(Activity activity, LinkMessage message) {
        //todo:
        return false;
    }

    @Override
    protected void shareText(Activity activity, TextMessage shareMessage, IShareCallback shareCallback) {
        //todo:
    }

    @Override
    protected void shareImage(Activity activity, ImageMessage shareMessage, IShareCallback shareCallback) {
        //todo:
    }

    @Override
    protected void shareLink(Activity activity, LinkMessage shareMessage, IShareCallback shareCallback) {
        //todo:
    }
}
