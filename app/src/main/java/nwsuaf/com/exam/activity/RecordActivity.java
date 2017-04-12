package nwsuaf.com.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.RecordAdapter;
import nwsuaf.com.exam.entity.examdate;
import nwsuaf.com.exam.service.ExamDateService;


public class RecordActivity extends BaseActivity {
    private RecyclerView rcv_record;
    private ImageView iv_id_leftbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        TopView();

        iv_id_leftbtn = (ImageView) findViewById(R.id.iv_id_leftbtn);
        iv_id_leftbtn.setImageResource(R.drawable.left);
        iv_id_leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        rcv_record = (RecyclerView) findViewById(R.id.rcv_record);
        //获取做题记录
        ExamDateService service = new ExamDateService(this);
        List<examdate> datas = service.getTotalData();
        if(datas == null||datas.size()==0){
            Intent intent = new Intent(this,NullActivity.class);
            startActivity(intent);
            finish();
        }
        RecordAdapter adapter = new RecordAdapter(this,datas);
        rcv_record.setLayoutManager(new LinearLayoutManager(this));
        rcv_record.setAdapter(adapter);
    }

}
