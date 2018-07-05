package com.kimonik.mypublicmodule;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.test.suitebuilder.annotation.Suppress;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.kimonik.utilsmodule.adapter.listview.CommonAdapter;
import com.kimonik.utilsmodule.adapter.listview.ViewHolder;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.utils.LUtils;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

//    @BindView(R.id.listView_act_home)
//    ListView listViewActHome;
    @BindView(R.id.videoview_act_home)
    VideoView videoviewActHome;
    private List<String> list;
    private int count = 0;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    @Override
    public int getLayoutResId() {
        return R.layout.act_home;
    }

    @Override
    public void initDataFromIntent() {
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("这是第" + i + "个!");
        }
        LUtils.e(HomeActivity.class,"logflag-我的c++--"+stringFromJNI());


    }

    private CommonAdapter<String> getAdapter() {
        return new CommonAdapter<String>(this, R.layout.lv_item_act_home, list) {

            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_lv_item_act_home, item);
            }
        };
    }

    @Override
    public void onClick(View v) {
    }



    @Override
    public void initView() {
        LUtils.e(HomeActivity.class, "logflag-????--");
        //https://www.tudoujf.com/assets/trust/style/css/20170525/MP4/movie.mp4
        String url="http://119.188.11.21/om.tc.qq.com/AoLLlZHwXrH3rTg-TN7wr8X-vtzLhc9FkeewcRVOS1L0/m0705in7bap.mp4?sdtfrom=v1104&amp;guid=a1d381e1e489b91ba078c7433256dce7&amp;vkey=00B435641602EF9C69F17AEBC55072A55F5B5A5F38B1E2CB12A3C9B21F205C9FE56FB292B1F03BDB6724F78292868D7827DC16BFF22A13FB7E17DC4C15CC099475E3E9296AE0B0844F1838D35325C49BFF848B88E57D240A2779C5BA0B04E1FE8B341B1EF2FBC2473B2A69D03AB4DF19C911F77227E2D27D";
//        listViewActHome.setAdapter(getAdapter());
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
                        if (count%2==1){
                            videoviewActHome.start();
                        }else {
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
    protected int setStatusBarColor() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
