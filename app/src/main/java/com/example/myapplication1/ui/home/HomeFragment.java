package com.example.myapplication1.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication1.MyApplication;
import com.example.myapplication1.R;
import com.example.myapplication1.network.Value;
import com.example.myapplication1.network.ValueHelper;
import com.example.myapplication1.network.ValueService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    ValueHelper valueHelper;
    private HomeViewModel homeViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //初始化个图标的图片
        final ImageView imageView1 = root.findViewById(R.id.imageView1);
        final ImageView imageView2 = root.findViewById(R.id.imageView2);

        imageView1.setImageResource(R.drawable.tem);
        imageView2.setImageResource(R.drawable.smoke);


        TextView textView = root.findViewById(R.id.textView);
        //UpdateUi();
        valueHelper = new ValueHelper();
        valueHelper.setValueActivity(getActivity());
        valueHelper.UpdateUi();

        //下拉刷新功能，更新ui数据的实现
        swipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.black);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ValueHelper valueHelper0 = new ValueHelper();
                valueHelper0.setValueActivity(getActivity());
                valueHelper0.UpdateUi();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MyApplication.getContext(),"数据刷新成功",Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }


}