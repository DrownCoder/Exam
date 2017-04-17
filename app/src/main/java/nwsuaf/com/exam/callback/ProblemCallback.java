package nwsuaf.com.exam.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import nwsuaf.com.exam.entity.netmodel.NetObject_ProblemData;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dengzhaoxuan on 2017/4/17.
 */

public class ProblemCallback extends Callback<NetObject_ProblemData> {
    @Override
    public NetObject_ProblemData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        NetObject_ProblemData user = new Gson().fromJson(string, NetObject_ProblemData.class);
        return user;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(NetObject_ProblemData response, int id) {

    }
}
