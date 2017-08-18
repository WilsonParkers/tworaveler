package com.developer.hare.tworaveler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.hare.tworaveler.Data.SessionManager;
import com.developer.hare.tworaveler.Listener.OnPhotoBindListener;
import com.developer.hare.tworaveler.Model.AlertSelectionItemModel;
import com.developer.hare.tworaveler.Model.Response.UserResModel;
import com.developer.hare.tworaveler.Model.UserModel;
import com.developer.hare.tworaveler.Net.Net;
import com.developer.hare.tworaveler.R;
import com.developer.hare.tworaveler.UI.AlertManager;
import com.developer.hare.tworaveler.UI.Layout.MenuTopTitle;
import com.developer.hare.tworaveler.UI.PhotoManager;
import com.developer.hare.tworaveler.UI.UIFactory;
import com.developer.hare.tworaveler.Util.FontManager;
import com.developer.hare.tworaveler.Util.Image.ImageManager;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSet extends AppCompatActivity {

    private EditText ET_nickname, ET_message;
    private CircleImageView circleImageView;
    private MenuTopTitle menuTopTitle;
    private UIFactory uiFactory;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);
        init();
    }

    private void init() {
        uiFactory = UIFactory.getInstance(this);

        ET_nickname = uiFactory.createView(R.id.profile_set$ET_nickname);
        ET_message = uiFactory.createView(R.id.profile_set$ET_message);
        menuTopTitle = uiFactory.createView(R.id.profile_set$topbar);
        circleImageView = uiFactory.createView(R.id.profile_setIV_profile);

        menuTopTitle.getIB_left().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        menuTopTitle.getIB_right().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyData();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<AlertSelectionItemModel> AlertSelectionItemModels = new ArrayList<>();
                AlertSelectionItemModels.add(new AlertSelectionItemModel("사진 촬영", R.drawable.ic_camera_alt_black_24dp, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotoManager.getInstance().onCameraSelect(ProfileSet.this, new OnPhotoBindListener() {
                            @Override
                            public void bindData(FileData fileData) {
                                ImageManager.getInstance().loadImage(ProfileSet.this, fileData.getFile(), circleImageView);
                                AlertManager.getInstance().dismissAlertSelectionMode();
                            }
                        });
                    }
                }));
                AlertSelectionItemModels.add(new AlertSelectionItemModel("갤러리", R.drawable.ic_filter_black_24dp, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotoManager.getInstance().onGallerySingleSelect(ProfileSet.this, new OnPhotoBindListener() {
                            @Override
                            public void bindData(FileData fileData) {
                                ImageManager.getInstance().loadImage(ProfileSet.this, fileData.getFile(), circleImageView);
                                AlertManager.getInstance().dismissAlertSelectionMode();
                            }
                        });
                    }

                }));
                AlertManager.getInstance().showAlertSelectionMode(ProfileSet.this, "등록 방법 선택", 2, AlertSelectionItemModels).show();

            }
        });
        ET_nickname = uiFactory.createView(R.id.profile_set$ET_nickname);
        ET_message = uiFactory.createView(R.id.profile_set$ET_message);
        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add(ET_nickname);
        textViews.add(ET_message);
        FontManager.getInstance().setFont(textViews, "NotoSansCJKkr-Medium.otf");
    }

    public void onLogout(View view) {
        AlertManager.getInstance().showPopup(ProfileSet.this,
                "알림",
                "로그아웃 하시겠습니까?",
                "취소",
                new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                },
                "확인",
                new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Net.getInstance().getFactoryIm().userLogout();

                        startActivity(new Intent(ProfileSet.this, SignIn.class));
                        finish();
                    }
                }
        );
    }

    public void onWithdrawal(View view) {
        AlertManager.getInstance().showPopup(ProfileSet.this,
                "알림",
                "정말이야? 다시 한번 생각해봐 ㅠㅠ",
                "취소",
                new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                },
                "확인",
                new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Net.getInstance().getFactoryIm().userSignOut(new UserResModel(userModel.getEmail(), userModel.getPw()));

                        startActivity(new Intent(ProfileSet.this, SignIn.class));
                        finish();
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        userModel = SessionManager.getInstance().getUserModel();
        ET_nickname.setText(userModel.getNickname());
        ET_message.setText(userModel.getStatus_message());
        ImageManager imageManager = ImageManager.getInstance();
        imageManager.loadImage(imageManager.createRequestCreator(this, userModel.getProfile_pic_url_thumbnail()).placeholder(R.drawable.image_profile), circleImageView);
    }

    private void modifyData() {
        Net.getInstance().getFactoryIm().userLogout();
    }
}
