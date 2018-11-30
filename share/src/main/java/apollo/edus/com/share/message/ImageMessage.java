package apollo.edus.com.share.message;


import java.util.List;

/**
 * Description.
 *
 * @author panda
 */
public class ImageMessage extends BaseShareMessage {

    private static final long serialVersionUID = -997236281089920362L;

    private List<String> mLocalPathList;
    private List<Integer> mResIdList;
    private List<String> mUrlList;

    public List<String> getLocalPathList() {
        return mLocalPathList;
    }

    public void setLocalPathList(List<String> localPathList) {
        mLocalPathList = localPathList;
    }

    public List<Integer> getResIdList() {
        return mResIdList;
    }

    public void setResIdList(List<Integer> resIdList) {
        mResIdList = resIdList;
    }

    public List<String> getUrlList() {
        return mUrlList;
    }

    public void setUrlList(List<String> urlList) {
        mUrlList = urlList;
    }
}
