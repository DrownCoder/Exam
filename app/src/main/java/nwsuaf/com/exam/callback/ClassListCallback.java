package nwsuaf.com.exam.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import nwsuaf.com.exam.entity.netmodel.ClassInfo;
import nwsuaf.com.exam.entity.netmodel.NetObject_ClassList;
import nwsuaf.com.exam.entity.netmodel.NetObject_Peo;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dengzhaoxuan on 2017/4/15.
 */

public class ClassListCallback extends Callback<NetObject_ClassList> {
    @Override
    public NetObject_ClassList parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        NetObject_ClassList user = new Gson().fromJson(string, NetObject_ClassList.class);
        return user;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(NetObject_ClassList response, int id) {

    }
}
