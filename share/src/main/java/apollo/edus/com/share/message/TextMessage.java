package apollo.edus.com.share.message;

/**
 * Description.
 *
 * @author panda
 */
public class TextMessage extends BaseShareMessage {

    private static final long serialVersionUID = 3719759459629458607L;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
