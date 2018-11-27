package apollo.edus.com.share.unit;

import android.app.Activity;

import apollo.edus.com.share.IShareCallback;
import apollo.edus.com.share.message.BaseShareMessage;

/**
 * Description.
 *
 * @author panda
 */
public interface IShareUnit {
    boolean isSupported(Activity activity, BaseShareMessage shareMessage);
    String getPluginKey();
    void share(Activity activity, BaseShareMessage shareMessage, IShareCallback shareCallback);
}
