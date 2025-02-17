package com.developer.hare.tworaveler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.hare.tworaveler.Data.DataDefinition;
import com.developer.hare.tworaveler.Data.SessionManager;
import com.developer.hare.tworaveler.Model.ScheduleModel;
import com.developer.hare.tworaveler.R;
import com.developer.hare.tworaveler.UI.Layout.MenuTopTitle;
import com.developer.hare.tworaveler.UI.UIFactory;
import com.developer.hare.tworaveler.Util.Date.DateManager;
import com.developer.hare.tworaveler.Util.Image.ImageManager;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.developer.hare.tworaveler.Data.DataDefinition.Intent.KEY_SCHEDULE_MODEL;

public class RegistDetail extends AppCompatActivity {
    private UIFactory uiFactory;
    private ImageManager imageManager;
    private DateManager dateManager;
    private Intent intent;
    private ScheduleModel scheduleModel;

    private com.prolificinteractive.materialcalendarview.MaterialCalendarView materialCalendarView;
    private MenuTopTitle menuTopTitle;
    private TextView TV_title, TV_date;
    private ImageView IV_cover;
    private View scheduleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_detail);
        init();
    }

    protected void init() {
        intent = getIntent();
        scheduleModel = (ScheduleModel) intent.getExtras().getSerializable(KEY_SCHEDULE_MODEL);

        uiFactory = UIFactory.getInstance(this);
        dateManager = DateManager.getInstance();
        imageManager = ImageManager.getInstance();

        scheduleItem = uiFactory.createView(R.id.activity_regist_detail$IC_schedul_item);
        menuTopTitle = uiFactory.createView(R.id.activity_regist_detail$menuToptitle);
        menuTopTitle.setIBRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistDetail.this, MyScheduleModify.class);
                intent.putExtra(KEY_SCHEDULE_MODEL, scheduleModel);
                startActivity(intent);
            }
        });
        menuTopTitle.setIBLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uiFactory.setResource(scheduleItem);
        TV_title = uiFactory.createView(R.id.item_mypage$TV_title);
        TV_date = uiFactory.createView(R.id.item_mypage$TV_date);
        IV_cover = uiFactory.createView(R.id.item_mypage$IV_cover);
        uiFactory.createView(R.id.item_mypage$TV_like).setVisibility(View.GONE);
        uiFactory.createView(R.id.item_mypage$TV_comment).setVisibility(View.GONE);
        uiFactory.createView(R.id.item_mypage$IV_like).setVisibility(View.GONE);
        uiFactory.createView(R.id.item_mypage$IV_comment).setVisibility(View.GONE);
        uiFactory.createView(R.id.item_mypage$IV_more).setVisibility(View.GONE);

        initMaterialCalendarView();
    }

    private void initMaterialCalendarView() {
        materialCalendarView = uiFactory.createView(R.id.activity_regist_detail$calendar);
        Date startDate = DateManager.getInstance().parseDate(scheduleModel.getStart_date(), DataDefinition.RegularExpression.FORMAT_DATE);
        Date endDate = DateManager.getInstance().parseDate(scheduleModel.getEnd_date(), DataDefinition.RegularExpression.FORMAT_DATE);
        materialCalendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat(DataDefinition.RegularExpression.FORMAT_DATE)));
        int[] startArr = DateManager.getInstance().getTimeArr(startDate);
        int[] endArr = DateManager.getInstance().getTimeArr(endDate);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONTH)
                .setMinimumDate(CalendarDay.from(startArr[0], startArr[1] - 1, startArr[2]))
                .setMaximumDate(CalendarDay.from(endArr[0], endArr[1] - 1, endArr[2]))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        materialCalendarView.setCurrentDate(startDate);
        materialCalendarView.selectRange(CalendarDay.from(startDate), CalendarDay.from(endDate));
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Intent intent = new Intent(RegistDetail.this, RegistDayList.class);
                intent.putExtra(DataDefinition.Intent.KEY_DATE, DateManager.getInstance().formatDate(date.getDate().getTime(), DataDefinition.RegularExpression.FORMAT_DATE));
                intent.putExtra(KEY_SCHEDULE_MODEL, scheduleModel);
                startActivityForResult(intent, DataDefinition.Intent.REQUEST_CODE_REGIST_DAY_LIST);
            }
        });
        materialCalendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat(DataDefinition.RegularExpression.FORMAT_DATE_REGIST_DETAIL)));
        materialCalendarView.setTopbarVisible(true);
    }

    private void setData() {
        menuTopTitle.getTV_title().setText(SessionManager.getInstance().getUserModel().getNickname());
        TV_title.setText(scheduleModel.getTripName());
        TV_date.setText(scheduleModel.getStart_date() + " ~ " + scheduleModel.getEnd_date());
        imageManager.loadImage(imageManager.createRequestCreator(this, scheduleModel.getTrip_pic_url(), ImageManager.FIT_TYPE), IV_cover);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == DataDefinition.Intent.REQUEST_CODE_SEARCH_CITY) {
            // Make sure the request was successful
            if (resultCode == DataDefinition.Intent.RESULT_CODE_SUCCESS) {
//                CityModel model = (CityModel) data.getSerializableExtra(DataDefinition.Intent.KEY_CITYMODEL);
            }
        }
    }
}
