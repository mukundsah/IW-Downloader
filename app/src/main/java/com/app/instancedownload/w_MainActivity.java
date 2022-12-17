package com.app.instancedownload;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;

public class w_MainActivity extends AppCompatActivity {
    int requestCode=1;

    w_Adapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    File[] files;

    ArrayList<w_ModelClass> fileslist=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        refreshLayout=findViewById(R.id.swipe);

        checkPermission();

        //setuplayout();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                setuplayout();
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    },1000);
                }
            }
        });

    }


    private void setuplayout() {

        fileslist.clear();
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter=new w_Adapter(w_MainActivity.this,getData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<w_ModelClass> getData() {
        w_ModelClass f;
        String targetpath= Environment.getExternalStorageDirectory().getAbsolutePath()+ w_Constant.FOLDER_NAME+"Media/.Statuses";
        File targerdir=new File(targetpath);
        files=targerdir.listFiles();
        for (int i=0;i<files.length;i++)
        {
            File file=files[i];
            f=new w_ModelClass();
            f.setUri(Uri.fromFile(file));
            f.setPath(files[i].getAbsolutePath());
            f.setFilename(file.getName());
            if(!f.getUri().toString().endsWith(".nomedia"))
            {
                fileslist.add(f);
            }
        }
        return fileslist;


    }

    private void checkPermission() {

        if(SDK_INT>23)
        {

            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                //main code

                setuplayout();
            }
            else
            {
                ActivityCompat.requestPermissions(w_MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode);
            }
            
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Already",Toast.LENGTH_SHORT).show();
            setuplayout();
        }


    }
}