package apollo.edus.com.share;

import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.unit.IShareUnit;

/**
 * Description.
 *
 * @author panda
 */
public interface IShareCallback {
    void onStartShare(IShareUnit sharePlugin, BaseShareMessage shareMessage);
    void onShareSucceed(IShareUnit sharePlugin, BaseShareMessage shareMessage);
    void onShareFailed(IShareUnit sharePlugin, BaseShareMessage shareMessage, String code, String reason);
}
