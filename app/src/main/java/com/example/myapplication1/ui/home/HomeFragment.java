package com.example.myapplication1.ui.home;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication1.R;

import static android.content.Context.BIND_AUTO_CREATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private DataService.DataBinder dataBinder;//这里帮助实现后台操作
    private ValueService.ValueBinder valueBinder;

    //这里是儿童检测部分
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            dataBinder = (DataService.DataBinder) service;
            dataBinder.getFragment(getActivity());
        }


        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    //这里是传感器部分
    private ServiceConnection connection1 = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            valueBinder = (ValueService.ValueBinder)service;
            valueBinder.getFragment1(getActivity());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

      /*  final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        //开启后台数据监控服务
        Intent startIntent = new Intent(getActivity(),DataService.class);
        getActivity().bindService(startIntent,connection,BIND_AUTO_CREATE);
        getActivity().startService(startIntent);

       /* Intent startIntent1 = new Intent(getActivity(),ValueService.class);
        getActivity().bindService(startIntent1,connection1,BIND_AUTO_CREATE);
        getActivity().startService(startIntent1);*/

        //初始化个图标的图片
        final ImageView imageView1 = root.findViewById(R.id.imageView1);
        final ImageView imageView2 = root.findViewById(R.id.imageView2);

        imageView1.setImageResource(R.drawable.tem);
        imageView2.setImageResource(R.drawable.smoke);


        TextView textView = root.findViewById(R.id.textView);


        return root;
    }



}