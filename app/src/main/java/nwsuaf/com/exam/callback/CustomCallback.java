package nwsuaf.com.exam.callback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import nwsuaf.com.exam.entity.netmodel.CustomResponse;
import nwsuaf.com.exam.entity.netmodel.NetObject_ClassList;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dengzhaoxuan on 2017/4/15.
 */

public class CustomCallback extends Callback<CustomResponse> {
    @Override
    public CustomResponse parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        CustomResponse res = new Gson().fromJson(string, CustomResponse.class);
        return res;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(CustomResponse response, int id) {

    }
}
