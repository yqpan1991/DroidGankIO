package apollo.edus.com.share.message;

/**
 * 链接消息
 *
 * @author panda
 */
public class LinkMessage extends BaseShareMessage {

    private static final long serialVersionUID = 3747712838755503912L;

    private String title;
    private String content;
    private String url;
    private String thumbUrl;
    private int thumbResId;
    private String thumbLocalPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getThumbResId() {
        return thumbResId;
    }

    public void setThumbResId(int thumbResId) {
        this.thumbResId = thumbResId;
    }

    public String getThumbLocalPath() {
        return thumbLocalPath;
    }

    public void setThumbLocalPath(String thumbLocalPath) {
        this.thumbLocalPath = thumbLocalPath;
    }

}
