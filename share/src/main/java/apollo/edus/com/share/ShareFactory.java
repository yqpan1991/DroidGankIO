package apollo.edus.com.share;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description.
 *
 * @author panda
 */
public class ShareFactory {
    private static ShareFactory sInstance = new ShareFactory();
    private ConcurrentHashMap<Class, Object> mClzMap;

    private ShareFactory(){
        mClzMap = new ConcurrentHashMap<>();
    }

    public static ShareFactory getInstance(){
        return sInstance;
    }

    public <T> T getService(Class<T> clz){
        //todo: 先简单实现
        if(mClzMap.contains(clz)){
            return (T) mClzMap.get(clz);
        }
        T object = null;
        if(clz == ShareInterface.class){
            object = (T) new ShareInterfaceImpl();
            mClzMap.put(clz, object);
        }
        return object;
    }
}
