package apollo.edus.com.share.unit;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import apollo.edus.com.share.IShareCallback;
import apollo.edus.com.share.ShareConstants;
import apollo.edus.com.share.message.ImageMessage;
import apollo.edus.com.share.message.LinkMessage;
import apollo.edus.com.share.message.TextMessage;

/**
 * Description.
 *
 * @author panda
 */
public class SystemDefaultShareUnit extends BaseShareUnit {

    public SystemDefaultShareUnit(UnitInfo unitInfo){
        super(unitInfo);
    }

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
        notifyStart(shareCallback, shareMessage);
        if(!isSupported(activity, shareMessage)){
            notifyFailed(shareCallback, shareMessage, ShareConstants.NOT_SUPPORTED,"");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送到属性
        intent.setType("text/plain"); // 分享发送到数据类型
        if(TextUtils.isEmpty(shareMessage.getTitle())){
            intent.putExtra(Intent.EXTRA_SUBJECT, shareMessage.getText()); // 分享的主题
        }else{
            intent.putExtra(Intent.EXTRA_SUBJECT, shareMessage.getTitle()); // 分享的主题
        }
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage.getText());
        intent.setPackage(getUnitInfo().getPkgId());
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        boolean failed = false;
        try{
            activity.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
            failed = true;
        }
        if(failed){
            notifyFailed(shareCallback, shareMessage, ShareConstants.START_EXCEPTION, null);
        }else{
            notifySucceed(shareCallback, shareMessage);
        }

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
