package com.example.muzammilpeer.testingdragableviews;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggableView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
/**
 * Created by muzammilpeer on 10/26/15.
 */
public class VideoSampleActivity extends AppCompatActivity {

    private static final String APPLICATION_RAW_PATH =
            "android.resource://com.example.muzammilpeer.testingdragableviews/";
    private static final String VIDEO_POSTER =
            "http://wac.450f.edgecastcdn.net/80450F/screencrush.com/files/2013/11/the-amazing-spider-"
                    + "man-2-poster-rhino.jpg";
    private static final String VIDEO_THUMBNAIL =
            "http://wac.450f.edgecastcdn.net/80450F/screencrush.com/files/2013/11/the-amazing-spider-"
                    + "man-2-poster-green-goblin.jpg";
    private static final String VIDEO_TITLE = "The Amazing Spider-Man 2: Rise of Electro";

    @InjectView(R.id.draggable_view)
    DraggableView draggableView;
    @InjectView(R.id.video_view)
    VideoView videoView;
    @InjectView(R.id.iv_thumbnail)
    ImageView thumbnailImageView;
    @InjectView(R.id.iv_poster)
    ImageView posterImageView;

    /**
     * Initialize the Activity with some injected data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_sample);
        ButterKnife.inject(this);
        initializeVideoView();
        initializePoster();
        hookDraggableViewListener();


        draggableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draggableView.maximize();
                Log.e("draggableView click", "yes");

            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("videoView click","yes");
            }
        });

        thumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("thumbnailImageViewclick","yes");
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("videoView click", "setOnTouchListener yes");
//                if (draggableView.isMinimized()) {
//                    draggableView.maximize();
//                }
                return false;
            }
        });
    }

    /**
     * Method triggered when the iv_thumbnail widget is clicked. This method shows a toast with the
     * video title.
     */
    @OnClick(R.id.iv_thumbnail)
    void onThubmnailClicked() {
        Toast.makeText(this, VIDEO_TITLE, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method triggered when the iv_poster widget is clicked. This method maximized the draggableView
     * widget.
     */
    @OnClick(R.id.iv_poster)
    void onPosterClicked() {
//        draggableView.maximize();
    }

    /**
     * Hook DraggableListener to draggableView to pause or resume VideoView.
     */
    private void hookDraggableViewListener() {
        draggableView.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                startVideo();
            }

            //Empty
            @Override
            public void onMinimized() {
                //Empty
                Log.e("onMinimized","pressed");
                if (draggableView.isMinimized()) {
                    draggableView.maximize();
                }

            }

            @Override
            public void onClosedToLeft() {
                stopVideo();
            }

            @Override
            public void onClosedToRight() {
                stopVideo();
            }
        });
    }

    /**
     * Pause the VideoView content.
     */
    private void pauseVideo() {
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    /**
     * Resume the VideoView content.
     */
    private void startVideo() {
        if (!videoView.isPlaying()) {
            videoView.start();
        }
    }

   private void stopVideo()
   {
       if (!videoView.isPlaying()) {
           videoView.stopPlayback();
       }
   }
    /**
     * Initialize ViedeoView with a video by default.
     */
    private void initializeVideoView() {
        Uri path = Uri.parse(APPLICATION_RAW_PATH + R.raw.video);
        videoView.setVideoURI(path);
        videoView.start();
    }

    /**
     * Initialize some ImageViews with a video poster and a video thumbnail.
     */
    private void initializePoster() {
        Picasso.with(this)
                .load(VIDEO_POSTER)
                .placeholder(R.drawable.spiderman_placeholder)
                .into(posterImageView);
        Picasso.with(this)
                .load(VIDEO_THUMBNAIL)
                .placeholder(R.drawable.spiderman_placeholder)
                .into(thumbnailImageView);
    }
}
