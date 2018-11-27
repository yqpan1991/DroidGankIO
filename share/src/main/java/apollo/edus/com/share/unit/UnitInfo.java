package apollo.edus.com.share.unit;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Description.
 *
 * @author panda
 */
public class UnitInfo {
    //pkgId,displayName,icon,check exist
    private String pkgId;
    private String displayName;
    private int iconResId;
    private String iconThumbUrl;
    private String iconLocalPath;
    private Drawable iconDrawable;
    private boolean checkExist;

    public String getPkgId() {
        return pkgId;
    }

    public void setPkgId(String pkgId) {
        this.pkgId = pkgId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getIconThumbUrl() {
        return iconThumbUrl;
    }

    public void setIconThumbUrl(String iconThumbUrl) {
        this.iconThumbUrl = iconThumbUrl;
    }

    public String getIconLocalPath() {
        return iconLocalPath;
    }

    public void setIconLocalPath(String iconLocalPath) {
        this.iconLocalPath = iconLocalPath;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public boolean isCheckExist() {
        return checkExist;
    }

    public void setCheckExist(boolean checkExist) {
        this.checkExist = checkExist;
    }
}
