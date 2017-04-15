package nwsuaf.com.exam.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dengzhaoxuan on 2017/4/14.
 */

public abstract class GsonCallback<T> extends Callback<T> {
    private Gson mGson;
    private Class<T> mClass;
    public GsonCallback(Class<T> clazz){
        mGson = new Gson();
        mClass = clazz;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        return mGson.fromJson(response.body().toString(), mClass);
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(T response, int id) {

    }
}
