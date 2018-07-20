package com.kimonik.mypublicmodule.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import com.kimonik.mypublicmodule.HomeActivity;
import com.kimonik.mypublicmodule.R;
import com.kimonik.mypublicmodule.app.MApp;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.ui.MTopBarView;
import com.kimonik.utilsmodule.utils.LUtils;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ===============================================================
 * name:             VideoViewTestActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/20
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class VideoViewTestActivity extends BaseActivity {
    @BindView(R.id.videoview_act_home)
    VideoView videoviewActHome;
    @BindView(R.id.mtb_act_videoview)
    MTopBarView mtbActVideoview;
    private int count = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.act_videoview;
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {
//        setTopMargin(mtbActVideoview, MApp.STATUS_BAE_HEIGHT);
        setCloseLisenter(mtbActVideoview);

        LUtils.e(HomeActivity.class, "logflag-????--");
        //https://www.tudoujf.com/assets/trust/style/css/20170525/MP4/movie.mp4
        videoviewActHome.setVideoURI(Uri.parse("https://www.tudoujf.com/assets/trust/style/css/20170525/MP4/movie.mp4"));
//        videoviewActHome.setVideoURI(Uri.parse(url));
        videoviewActHome.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoviewActHome.start();
                videoviewActHome.seekTo(2000);
                videoviewActHome.pause();
            }
        });
//        videoviewActHome.setMediaController(new MediaController(this));
        videoviewActHome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        count++;
                        if (count % 2 == 1) {
                            videoviewActHome.start();
                        } else {
                            videoviewActHome.pause();
                        }

                        break;
                }
                return true;
            }

        });

        /**
         *  String path = Environment.getExternalStorageDirectory().getPath()+"/"+et.getText().toString();//获取视频路径
         Uri uri = Uri.parse(path);//将路径转换成uri
         video.setVideoURI(uri);//为视频播放器设置视频路径
         video.setMediaController(new MediaController(Main2Activity.this));//显示控制栏
         video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override public void onPrepared(MediaPlayer mp) {
        video.start();//开始播放视频
        }
        });
         */
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi(Response<String> response) {

    }

    @Override
    public void loadInternetDataToUi() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
