package com.example.myapplication1.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication1.MyApplication;
import com.example.myapplication1.R;

public class NotificationsFragment extends Fragment {


    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.textView1);
        /*notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        ImageView imageView = (ImageView) root.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.load);
        ImageView imageView1 = (ImageView) root.findViewById(R.id.imageView1);
        imageView1.setImageResource(R.drawable.history);
        ImageView imageView2 = (ImageView) root.findViewById(R.id.imageView2);
        imageView2.setImageResource(R.drawable.setting);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        return root;
    }
}