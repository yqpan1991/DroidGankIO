package apollo.edus.com.share.message;

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
    private boolean needScreenShot;

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

    public boolean isNeedScreenShot() {
        return needScreenShot;
    }

    public void setNeedScreenShot(boolean needScreenShot) {
        this.needScreenShot = needScreenShot;
    }
}
