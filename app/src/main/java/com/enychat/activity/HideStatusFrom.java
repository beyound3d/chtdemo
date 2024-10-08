package com.enychat.activity;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HideStatusFrom extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back) ImageView ivBtnBack;
    @BindView(R.id.iv_status_hide) ImageView ivStatusHide;
    @BindView(R.id.toolbar_new_broadcast) Toolbar toolbar;
    @BindView(R.id.tv_title) TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_status_from);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        tvTitle.setText(R.string.hide_status_from);

        ivBtnBack.setOnClickListener(this);
        ivStatusHide.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_new_group, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    @Override
    public void onClick(View view) {
        if (view == ivBtnBack) {
            finish();
        }else if (view == ivStatusHide) {
                Toast.makeText(getApplicationContext(), "Status Hide....", Toast.LENGTH_LONG).show();
        }

    }
}
