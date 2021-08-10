package com.example.myapplication1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication1.MyApplication;
import com.example.myapplication1.R;
import com.example.myapplication1.network.home.ValueHelper;
import com.example.myapplication1.network.home.ValueResponse;

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
        valueHelper = new ValueHelper();
        valueHelper.setValueActivity(getActivity());

        //UpdateUi();
        homeViewModel.getValue().observe(getViewLifecycleOwner(), new Observer<ValueResponse>() {
            @Override
            public void onChanged(ValueResponse valueResponse) {
                //更新UI
                valueHelper.UiChange(valueResponse);

            }
        });


        //下拉刷新功能，更新ui数据的实现
        swipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.black);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


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