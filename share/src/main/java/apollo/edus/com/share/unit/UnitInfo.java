package apollo.edus.com.share.unit;

import android.graphics.Bitmap;

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
    private Bitmap iconBitmap;
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

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    public boolean isCheckExist() {
        return checkExist;
    }

    public void setCheckExist(boolean checkExist) {
        this.checkExist = checkExist;
    }
}
