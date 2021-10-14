package com.example.practiceproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.practiceproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher; // Intent형 activityResultLauncher 객체 생성
    ActivityMainBinding binding; // 메인 액티비티 바인딩
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main); // 뷰 바인딩, 메모리에 레이아웃 파일 인플레이션

        init();
    }

    private void init() {
        //activityResultLauncher 초기화
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == 9001) { // resultCode가 9001로 넘어왔다면
                Intent intent = result.getData(); // ActivityResult 객체 result로 intent를 받아온다.
                String name = intent.getStringExtra(getString(R.string.name_key)); // 설정한 key 값으로 StringExtra를 받아온다.
                Toast.makeText(getApplicationContext(), getString(R.string.toast_result_ok) + name, Toast.LENGTH_SHORT).show();
                // 토스트 메시지로 띄운다.
            } else if(result.getResultCode() == 9002) { // resultCode가 9002로 넘어왔다면
                Toast.makeText(getApplicationContext(), getString(R.string.result_failed), Toast.LENGTH_SHORT).show();
                // 토스트 메시지로 띄운다.
            }
        });

        binding.mainBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class); // 메뉴 액티비티를 실행할 intent
            intent.putExtra(getString(R.string.first_key), getString(R.string.first_value)); // StringExtra를 넘겨준다.
            activityResultLauncher.launch(intent);// startActivityForResult가 아닌, ActivityResultLauncher의 launch 메서드로 intent를 실행한다.
        });
    }
}