package com.developer.hare.tworaveler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.developer.hare.tworaveler.Adapter.CityListAdapter;
import com.developer.hare.tworaveler.Adapter.FeedCityListAdapter;
import com.developer.hare.tworaveler.Adapter.FeedNicknameListAdapter;
import com.developer.hare.tworaveler.Adapter.NicknameListAdapter;
import com.developer.hare.tworaveler.Data.DataDefinition;
import com.developer.hare.tworaveler.Data.SessionManager;
import com.developer.hare.tworaveler.Listener.OnSelectCityListener;
import com.developer.hare.tworaveler.Listener.OnSelectNicknameListener;
import com.developer.hare.tworaveler.Model.CityModel;
import com.developer.hare.tworaveler.Model.Response.ResponseArrayModel;
import com.developer.hare.tworaveler.Model.ScheduleModel;
import com.developer.hare.tworaveler.Net.Net;
import com.developer.hare.tworaveler.R;
import com.developer.hare.tworaveler.UI.FontManager;
import com.developer.hare.tworaveler.UI.UIFactory;
import com.developer.hare.tworaveler.Util.HandlerManager;
import com.developer.hare.tworaveler.Util.LogManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.color.black;
import static com.developer.hare.tworaveler.Data.DataDefinition.Intent.KEY_CITYMODEL;
import static com.developer.hare.tworaveler.Data.DataDefinition.Intent.KEY_SCHEDULE_MODEL;
import static com.developer.hare.tworaveler.Data.DataDefinition.Intent.RESULT_CODE_CITY_MODEL;
import static com.developer.hare.tworaveler.Data.DataDefinition.Intent.RESULT_CODE_FAIL;
import static com.developer.hare.tworaveler.Data.DataDefinition.Intent.RESULT_CODE_SCHEDULE_MODEL;

public class SearchFeed extends AppCompatActivity {

    private UIFactory uiFactory;
    private FeedCityListAdapter cityListAdapter;
    private FeedNicknameListAdapter nicknameListAdapter;
    private ArrayList<CityModel> cityItems = new ArrayList<>();
    private ArrayList<ScheduleModel> nicknameItems = new ArrayList<>();

    //    private MenuTopTitle menuTopTitle;
    private RecyclerView RV_citylist, RV_nicknamelist;
    private EditText ET_city;
    private TextView city, nickname;
    private ImageButton IB_search;
    private boolean isCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_feed);
        init();
    }

    private void init() {
        uiFactory = UIFactory.getInstance(this);
        city = uiFactory.createView(R.id.search_feed$city);
        nickname = uiFactory.createView(R.id.search_feed$nickname);
        RV_citylist = uiFactory.createView(R.id.search_feed$RV_citylist);
        RV_citylist.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));


        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCity = true;
                selectFeed(isCity);
            }
        });
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCity = false;
                selectFeed(isCity);
            }
        });

        ET_city = uiFactory.createView(R.id.search_feed$ET_city);
        FontManager.getInstance().setFont(ET_city, "NotoSansKR-Medium-Hestia.otf");
        ET_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(isCity) {
                    LogManager.log(LogManager.LOG_INFO, SearchFeed.class, "afterTextChanged(Editable)", "running city");
                    Net.getInstance().getFactoryIm().searchCity( ET_city.getText().toString()).enqueue(new Callback<ResponseArrayModel<CityModel>>() {
                        @Override
                        public void onResponse(Call<ResponseArrayModel<CityModel>> call, Response<ResponseArrayModel<CityModel>> response) {
                            if (response.isSuccessful()) {
                                ResponseArrayModel<CityModel> result = response.body();
                                cityItems = result.getResult();
                                HandlerManager.getInstance().getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
//                                    cityListAdapter.notifyDataSetChanged();
//                                        RV_citylist.setAdapter(new FeedCityListAdapter(items, getApplicationContext()));
                                        RV_citylist.setAdapter(new CityListAdapter(new OnSelectCityListener() {
                                            @Override
                                            public void onSelectCity(CityModel model) {
                                                Intent intent = new Intent();
                                                intent.putExtra(KEY_CITYMODEL, model);
                                                setResult(RESULT_CODE_CITY_MODEL, intent);
                                                finish();
                                            }
                                        }, cityItems, getApplicationContext() ));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseArrayModel<CityModel>> call, Throwable t) {
                            LogManager.log(LogManager.LOG_INFO, SearchFeed.class, "onResponse(Call<ResponseArrayModel<ScheduleDayModel>> call, Response<ResponseArrayModel<ScheduleDayModel>> response)", "onFail : " + t);
                        }
                    });
                }else{
                    Net.getInstance().getFactoryIm().searchNickname(SessionManager.getInstance().getUserModel().getUser_no(), ET_city.getText().toString()).enqueue(new Callback<ResponseArrayModel<ScheduleModel>>() {
                        @Override
                        public void onResponse(Call<ResponseArrayModel<ScheduleModel>> call, Response<ResponseArrayModel<ScheduleModel>> response) {
                           /* LogManager.log(LogManager.LOG_INFO, SearchFeed.class, "ScheduleModel", "" + nicknameItems);
                            LogManager.log(LogManager.LOG_INFO, SearchFeed.class, "afterTextChanged(Editable)",response.body().toString());
                            LogManager.log(LogManager.LOG_INFO, SearchFeed.class, "afterTextChanged(Editable)", "running nickname"+ response.isSuccessful());
                            LogManager.log(LogManager.LOG_INFO, SearchFeed.class, "afterTextChanged(Editable)", "running nickname"+ response.message());*/
                            if (response.isSuccessful()) {
                                ResponseArrayModel<ScheduleModel> result = response.body();
                                nicknameItems = result.getResult();
                                HandlerManager.getInstance().getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        RV_citylist.setAdapter(new NicknameListAdapter(new OnSelectNicknameListener() {
                                            @Override
                                            public void onSelectNickname(ScheduleModel model) {
                                                Intent intent = new Intent();
                                                intent.putExtra(KEY_SCHEDULE_MODEL, model);
                                                setResult(RESULT_CODE_SCHEDULE_MODEL, intent);
                                                finish();
                                            }
                                        }, nicknameItems, getApplicationContext()));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseArrayModel<ScheduleModel>> call, Throwable t) {
                            LogManager.log(LogManager.LOG_INFO, SearchFeed.class, "onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response)", "onFail : " + t);
                        }
                    });
                }
            }
        });
        IB_search = uiFactory.createView(R.id.search_feed$IB_cancel);
        IB_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_city.setText("");
//                onFinish(DataDefinition.Intent.RESULT_CODE_SUCCESS, null);
            }
        });
        city.callOnClick();
    }

    private void onFinish(int code, ScheduleModel model) {
        Intent intent = new Intent();
        if (code == DataDefinition.Intent.RESULT_CODE_SUCCESS) {
            intent.putExtra(KEY_CITYMODEL, model);
        }
        setResult(code, intent);
        finish();
    }

    private void selectFeed(boolean isCity){
        if(isCity){
            SessionManager.getInstance().actionAfterSessionCheck(SearchFeed.this, new SessionManager.OnActionAfterSessionCheckListener() {
                @Override
                public void action() {
                    city.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.menu_top_title_color));
                    nickname.setTextColor(ContextCompat.getColor(getBaseContext(), black));
                    RV_citylist.setAdapter(new CityListAdapter(null, cityItems, getApplicationContext() ));
                    ET_city.setHint(R.string.select_feed_city);
                }
            });
        }else {
            SessionManager.getInstance().actionAfterSessionCheck(SearchFeed.this, new SessionManager.OnActionAfterSessionCheckListener() {
                @Override
                public void action() {
                    nickname.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.menu_top_title_color));
                    city.setTextColor(ContextCompat.getColor(getBaseContext(), black));
                    RV_citylist.setAdapter(new NicknameListAdapter(null, nicknameItems, getApplicationContext()));
                    ET_city.setHint(R.string.select_feed_nickname);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CODE_FAIL, intent);
        finish();
        super.onBackPressed();
    }
}
