package apollo.edus.com.share.plugin;

import android.app.Activity;
import android.content.Context;

import apollo.edus.com.share.IShareCallback;
import apollo.edus.com.share.message.BaseShareMessage;

/**
 * Description.
 *
 * @author panda
 */
public interface ISharePlugin {
    boolean isSupported(Activity activity, BaseShareMessage shareMessage);
    String getPluginKey();
    void share(Activity activity, BaseShareMessage shareMessage, IShareCallback shareCallback);
}
