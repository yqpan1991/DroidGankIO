package apollo.edus.com.share;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.message.LinkMessage;
import apollo.edus.com.share.message.TextMessage;
import apollo.edus.com.share.ui.fragment.ShareFragment;
import apollo.edus.com.share.unit.BaseShareUnit;
import apollo.edus.com.share.unit.SystemDefaultShareUnit;
import apollo.edus.com.share.unit.UnitInfo;
import apollo.edus.com.share.utils.PackageUtils;

/**
 * Description.
 *
 * @author panda
 */
public class ShareInterfaceImpl extends ShareInterface {
    //整体包装
    //1. 1个分享组件:1个shareMessage 直接分享(外部直接调用即可)
    //2. N个分享组件: 1个shareMessage 弹窗分享

    @Override
    public void shareText(Activity activity, TextMessage shareMessage, IShareCallback shareCallback) {
        if(activity == null || shareMessage == null){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送到属性
        intent.setType("text/plain"); // 分享发送到数据类型
        if(TextUtils.isEmpty(shareMessage.getTitle())){
            intent.putExtra(Intent.EXTRA_SUBJECT, shareMessage.getText()); // 分享的主题
        }else{
            intent.putExtra(Intent.EXTRA_SUBJECT, shareMessage.getTitle()); // 分享的主题
        }
        PackageManager pm = activity.getPackageManager();
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage.getText());
        List<ResolveInfo> supportedActivities = pm.queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(supportedActivities == null || supportedActivities.size() <= 0){
            return;
        }
        ArrayList<BaseShareUnit> baseShareUnits = new ArrayList<>();
        for(ResolveInfo info : supportedActivities){
            UnitInfo unitInfo = new UnitInfo();
            unitInfo.setPkgId(info.activityInfo.packageName);
            CharSequence riLabel = info.loadLabel(pm);
            if (riLabel == null) {
                riLabel = info.activityInfo.packageName;
            }
            unitInfo.setDisplayName(riLabel.toString());
            unitInfo.setIconDrawable(PackageUtils.loadIconForResolveInfo(info));
            SystemDefaultShareUnit shareUnit = new SystemDefaultShareUnit(unitInfo);
            baseShareUnits.add(shareUnit);
        }

        shareMessage(activity, baseShareUnits, shareMessage, shareCallback);
    }

    @Override
    public void shareLink(Activity activity, LinkMessage linkMessage, IShareCallback shareCallback) {
        if(activity == null || linkMessage == null || TextUtils.isEmpty(linkMessage.getUrl())){
            return;
        }
        //linkMessage,如果没有三方的分享组件,采用系统的分享,直接拼接链接即可
        //或者最后采用系统的分享组件即可
        String title = linkMessage.getTitle();
        String content = linkMessage.getContent();
        String linkUrl = linkMessage.getUrl();
        TextMessage textMessage = new TextMessage();
        textMessage.setTitle(title);
        StringBuilder stringBuilder = new StringBuilder();
        if(!TextUtils.isEmpty(title)){
            stringBuilder.append(title).append("\n");
        }
        if(!TextUtils.isEmpty(content)){
            stringBuilder.append(content).append("\n");
        }
        if(!TextUtils.isEmpty(linkUrl)){
            stringBuilder.append(linkUrl);
        }
        textMessage.setText(stringBuilder.toString());
        shareText(activity, textMessage, shareCallback);
    }

    @Override
    public void shareMessage(Activity activity, List<BaseShareUnit> shareUnitList, BaseShareMessage shareMessage, IShareCallback shareCallback) {
        if(activity == null || shareUnitList == null || shareUnitList.isEmpty() || shareMessage == null){
            return;
        }
        ShareFragment shareFragment = new ShareFragment();
        shareFragment.setShareMessage(shareMessage);
        shareFragment.setShareUnitList(shareUnitList);
        shareFragment.setShareCallback(shareCallback);
        shareFragment.show(activity.getFragmentManager(),  "share_fragment_for_text");

    }
}
