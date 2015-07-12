package com.ivangusef.android.playgl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ivangusef.android.playgl.cube.CubeActivity;
import com.ivangusef.android.playgl.triangle.TriangleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Иван on 05.07.2015.
 */
public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_triangle)
    void onTriangleButtonClick() {
        startActivity(TriangleActivity.makeLaunchIntent(this));
    }

    @OnClick(R.id.btn_cube)
    void onCubeButtonClick() {
        startActivity(CubeActivity.makeLaunchIntent(this));
    }
}
