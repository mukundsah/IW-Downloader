package com.app.instancedownload.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.app.instancedownload.R;
import com.app.instancedownload.util.Method;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Setting extends AppCompatActivity {

    private Method method;
    private Animation myAnim;
    private String themMode;
    private MaterialTextView textViewCash;

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        method = new Method(Setting.this);

        myAnim = AnimationUtils.loadAnimation(Setting.this, R.anim.bounce);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_setting);
        toolbar.setTitle(getResources().getString(R.string.setting));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ConstraintLayout conThem = findViewById(R.id.con_them_setting);
        ConstraintLayout conAbout = findViewById(R.id.con_aboutUs_setting);
        ConstraintLayout conGuidance = findViewById(R.id.con_guidance_setting);
        ConstraintLayout conPrivacy = findViewById(R.id.con_privacy_policy_setting);
        ConstraintLayout conShareApp = findViewById(R.id.con_shareApp_setting);
        ConstraintLayout conRateApp = findViewById(R.id.con_rateApp_setting);
        ConstraintLayout conMoreApp = findViewById(R.id.con_moreApp_setting);
        ImageView imageViewClear = findViewById(R.id.imageView_clear_setting);
        SwitchMaterial switchMaterial = findViewById(R.id.switch_delete_setting);
        MaterialTextView textViewThemName = findViewById(R.id.textView_themName_setting);
        textViewCash = findViewById(R.id.textView_cash_setting);

        switchMaterial.setChecked(method.pref.getBoolean(method.isDelete, true));

        switch (method.getThem()) {
            case "system":
                textViewThemName.setText(getResources().getString(R.string.system_default));
                break;
            case "light":
                textViewThemName.setText(getResources().getString(R.string.light));
                break;
            case "dark":
                textViewThemName.setText(getResources().getString(R.string.dark));
                break;
            default:
                break;
        }

        textViewCash.setText(getResources().getString(R.string.cash_file)
                + " " + new DecimalFormat("##.##")
                .format((FileUtils.sizeOfDirectory(getCacheDir()) + FileUtils.sizeOfDirectory(new File(getExternalCacheDir().getAbsolutePath()))) / (1024 * 1024))
                + " " + getResources().getString(R.string.mb));

        imageViewClear.setOnClickListener(v -> {
            imageViewClear.startAnimation(myAnim);
            FileUtils.deleteQuietly(getCacheDir());
            try {
                FileUtils.deleteDirectory(new File(getExternalCacheDir().getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            textViewCash.setText(getResources().getString(R.string.cash_file) + " " + FileUtils.sizeOfDirectory(getCacheDir()) + " " + getResources().getString(R.string.mb));
        });

        conThem.setOnClickListener(v -> {
            Dialog dialog = new Dialog(Setting.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_them);
            dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup_them);
            MaterialTextView textViewOk = dialog.findViewById(R.id.textView_ok_them);
            MaterialTextView textViewCancel = dialog.findViewById(R.id.textView_cancel_them);

            switch (method.getThem()) {
                case "system":
                    radioGroup.check(radioGroup.getChildAt(0).getId());
                    break;
                case "light":
                    radioGroup.check(radioGroup.getChildAt(1).getId());
                    break;
                case "dark":
                    radioGroup.check(radioGroup.getChildAt(2).getId());
                    break;
                default:
                    break;
            }

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                MaterialRadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    switch (checkedId) {
                        case R.id.radioButton_system_them:
                            themMode = "system";
                            break;
                        case R.id.radioButton_light_them:
                            themMode = "light";
                            break;
                        case R.id.radioButton_dark_them:
                            themMode = "dark";
                            break;
                        default:
                            break;
                    }
                }
            });

            textViewOk.setOnClickListener(vOk -> {
                method.editor.putString(method.themSetting, themMode);
                method.editor.commit();
                dialog.dismiss();
                startActivity(new Intent(Setting.this, SplashScreen.class));
                finishAffinity();
            });

            textViewCancel.setOnClickListener(vCancel -> dialog.dismiss());

            dialog.show();
        });

        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            method.editor.putBoolean(method.isDelete, isChecked);
            method.editor.commit();
        });

        conAbout.setOnClickListener(v -> {
            startActivity(new Intent(Setting.this, AboutUs.class));
        });

        conPrivacy.setOnClickListener(v -> {
            startActivity(new Intent(Setting.this, PrivacyPolicy.class));
        });

        conGuidance.setOnClickListener(v -> {
            startActivity(new Intent(Setting.this, Information.class));
        });

        conShareApp.setOnClickListener(v -> {
            try {

                String string = "\n" + getResources().getString(R.string.Let_me_recommend_you_this_application) + "\n\n"
                        + "https://play.google.com/store/apps/details?id=" + getApplication().getPackageName();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, string);
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.choose_one)));

            } catch (Exception e) {
                //e.toString();
            }
        });

        conRateApp.setOnClickListener(v -> {
            Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
            }
        });

        conMoreApp.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_more_app)))));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
