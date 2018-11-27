package apollo.edus.com.share.message;

import android.graphics.Bitmap;

/**
 * Description.
 *
 * @author panda
 */
public class ImageMessage extends BaseShareMessage {

    private static final long serialVersionUID = -997236281089920362L;

    private String localPath;
    private String resId;
    private String url;
    private Bitmap bitmap;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
