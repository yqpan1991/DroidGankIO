package apollo.edus.com.share.utils;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

import apollo.edus.com.share.ShareContext;

/**
 * Description.
 *
 * @author panda
 */
public class PackageUtils {
    public static Drawable getIcon(Resources res, int resId, ResolveInfo ri) {
        Drawable result;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                result = res.getDrawableForDensity(resId, 0);
            } else {
                result = ri.loadIcon(ShareContext.getInstance().getContext().getPackageManager());
            }
        } catch (Resources.NotFoundException e) {
            result = null;
        }

        return result;
    }

    public static Drawable loadIconForResolveInfo(ResolveInfo ri) {
        PackageManager pm = ShareContext.getInstance().getContext().getPackageManager();

        Drawable dr;
        try {
            if (ri.resolvePackageName != null && ri.icon != 0) {
                dr = getIcon(pm.getResourcesForApplication(ri.resolvePackageName), ri.icon, ri);
                if (dr != null) {
                    return dr;
                }
            }
            final int iconRes = ri.getIconResource();
            if (iconRes != 0) {
                dr = getIcon(pm.getResourcesForApplication(ri.activityInfo.packageName), iconRes, ri);
                if (dr != null) {
                    return dr;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return ri.loadIcon(pm);
    }
}
