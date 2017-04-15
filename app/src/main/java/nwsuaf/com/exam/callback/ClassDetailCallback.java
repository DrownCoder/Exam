package nwsuaf.com.exam.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import nwsuaf.com.exam.entity.netmodel.NetObject_ClassDetail;
import nwsuaf.com.exam.entity.netmodel.NetObject_ClassList;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dengzhaoxuan on 2017/4/15.
 */

public class ClassDetailCallback extends Callback<NetObject_ClassDetail> {
    @Override
    public NetObject_ClassDetail parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        NetObject_ClassDetail user = new Gson().fromJson(string, NetObject_ClassDetail.class);
        return user;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(NetObject_ClassDetail response, int id) {

    }
}
