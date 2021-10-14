package com.example.practiceproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.practiceproject.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding; // 메뉴 액티비티 바인딩

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu); // 뷰 바인딩, 메모리에 레이아웃 파일 인플레이션

        Intent intent = getIntent(); // 메인 액티비티에서 넘어온 intent 받아옴.

        String data = intent.getStringExtra(getString(R.string.first_key)); // 메인 액티비티가 넘겨준 StringExtra값 받음.

        binding.menuGetTv.setText(data); // TextView 객체 텍스트값 설정

        binding.menuBackBtn.setOnClickListener(v -> {
            Intent finish_intent = new Intent(getApplicationContext(), MainActivity.class); // resultCode와 데이터 값 전달을 위한 intent 생성
            finish_intent.putExtra(getString(R.string.name_key), getString(R.string.name_value)); // 데이터 값 전달
            setResult(9001, finish_intent); // 이전과 똑같이 setResult 메서드로 결과 코드와 intent값 전달
            finish(); // 액티비티 종료
        });
    }

    @Override
    public void onBackPressed() {
        Intent finish_intent = new Intent(getApplicationContext(), MainActivity.class); // resultCode와 데이터 값 전달을 위한 intent 생성
        setResult(9002, finish_intent); // 이전과 똑같이 setResult 메서드로 결과 코드와 intent값 전달
        finish(); // 액티비티 종료
    }
}
