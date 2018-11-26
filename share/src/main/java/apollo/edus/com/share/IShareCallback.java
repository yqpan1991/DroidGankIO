package apollo.edus.com.share;

import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.plugin.ISharePlugin;

/**
 * Description.
 *
 * @author panda
 */
public interface IShareCallback {
    void onStartShare(ISharePlugin sharePlugin, BaseShareMessage shareMessage);
    void onShareSucceed(ISharePlugin sharePlugin, BaseShareMessage shareMessage);
    void onshareFailed(ISharePlugin sharePlugin, BaseShareMessage shareMessage, String code, String reason);
}
