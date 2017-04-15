package nwsuaf.com.exam.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import nwsuaf.com.exam.entity.netmodel.NetObject_Peo;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dengzhaoxuan on 2017/4/15.
 */

public class LoginCallback extends Callback<NetObject_Peo> {
    @Override
    public NetObject_Peo parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        NetObject_Peo user = new Gson().fromJson(string, NetObject_Peo.class);
        return user;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(NetObject_Peo response, int id) {

    }
}
