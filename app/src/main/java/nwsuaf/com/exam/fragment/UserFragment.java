package nwsuaf.com.exam.fragment;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.customview.SubmitButton;
import nwsuaf.com.exam.customview.WheelIndicatorItem;
import nwsuaf.com.exam.customview.WheelIndicatorView;
import nwsuaf.com.exam.service.ExamDateService;


public class UserFragment extends Fragment implements View.OnClickListener {
    private WheelIndicatorView wheelIndicatorView;
    private TextView tv_id_passrate;
    private TextView tv_id_pass,tv_id_total,tv_id_unpass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.peo_layout, container, false);
        initViews(view);
        initViewSource();
        return view;
    }

    private void initViewSource() {
        //获取做题记录
        ExamDateService service = new ExamDateService(getContext());
        float passnum = service.getPassData().size();
        float totalnum = service.getTotalData().size();
        int passrate = (int) (passnum/totalnum * 100.0f);
        tv_id_passrate.setText(passrate+"%");
        tv_id_pass.setText(String.valueOf((int)passnum));
        tv_id_total.setText(String.valueOf((int) totalnum));
        tv_id_unpass.setText(String.valueOf((int) (totalnum - passnum)));

        // dummy data
        float dailyKmsTarget = 100.0f; // 4.0Km is the user target, for example
        float totalKmsDone = passrate; // User has done 3 Km
        int percentageOfExerciseDone = (int) (totalKmsDone/dailyKmsTarget * 100); //

        wheelIndicatorView.setFilledPercent(percentageOfExerciseDone);

        WheelIndicatorItem bikeActivityIndicatorItem = new WheelIndicatorItem(1.8f, Color.parseColor("#ff9000"));
        WheelIndicatorItem walkingActivityIndicatorItem = new WheelIndicatorItem(0.9f, Color.argb(255, 194, 30, 92));
        WheelIndicatorItem runningActivityIndicatorItem = new WheelIndicatorItem(0.3f, getResources().getColor(R.color.my_wonderful_blue_color));

        wheelIndicatorView.addWheelIndicatorItem(bikeActivityIndicatorItem);
        wheelIndicatorView.addWheelIndicatorItem(walkingActivityIndicatorItem);
        wheelIndicatorView.addWheelIndicatorItem(runningActivityIndicatorItem);

        // Or you can add it as
        //wheelIndicatorView.setWheelIndicatorItems(Arrays.asList(runningActivityIndicatorItem,walkingActivityIndicatorItem,bikeActivityIndicatorItem));

        wheelIndicatorView.startItemsAnimation(); // Animate!
    }
    private void initViews(View view) {
        wheelIndicatorView = (WheelIndicatorView) view.findViewById(R.id.wheel_indicator_view);

        tv_id_passrate = (TextView) view.findViewById(R.id.tv_id_passrate);
        tv_id_pass = (TextView) view.findViewById(R.id.tv_id_pass);
        tv_id_total = (TextView) view.findViewById(R.id.tv_id_total);
        tv_id_unpass = (TextView) view.findViewById(R.id.tv_id_unpass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_id_exam:
                break;
        }
    }
}
