package apollo.edus.com.share.message;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * ShareMessage的封装
 *
 * @author panda
 */
public abstract class BaseShareMessage implements Serializable {

    private HashMap<String, BaseShareMessage> mExtraMessageMap;

    public BaseShareMessage(){
        mExtraMessageMap = new HashMap<>();
    }

    public void addExtraShareMessage(String key, BaseShareMessage shareMessage){
        if(TextUtils.isEmpty(key)){
            return;
        }
        mExtraMessageMap.remove(key);
        if(shareMessage != null){
            mExtraMessageMap.put(key, shareMessage);
        }
    }

    public BaseShareMessage getExtraShareMessage(String key){
        if(TextUtils.isEmpty(key)){
            return null;
        }
        return mExtraMessageMap.get(key);
    }


}
