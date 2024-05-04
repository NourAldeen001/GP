package com.mastercoding.gp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mastercoding.gp.auth.AuthActivity;
import com.mastercoding.gp.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity {

//    ViewPager2 viewPager2;
    SlideAdapter slideAdapter;
//    LinearLayout dotsLayout;
//    Button getStartedBtn, nextBtn;
    int currentPos;

    private ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding);

//        getStartedBtn = findViewById(R.id.lets_started_btn);
//        nextBtn = findViewById(R.id.next_btn);
//        viewPager2 = findViewById(R.id.slider);
//        dotsLayout = findViewById(R.id.dots_layout);

        slideAdapter = new SlideAdapter();
        binding.slider.setAdapter(slideAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        binding.slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
                currentPos = position;

                if(currentPos == 0){
                    binding.letsStartedBtn.setVisibility(View.INVISIBLE);
                    binding.nextBtn.setVisibility(View.VISIBLE);
                }
                else if(currentPos == 1){
                    binding.letsStartedBtn.setVisibility(View.INVISIBLE);
                    binding.nextBtn.setVisibility(View.VISIBLE);
                }
                else {
                    binding.letsStartedBtn.setVisibility(View.VISIBLE);
                    binding.nextBtn.setVisibility(View.INVISIBLE);
                }

            }
        });


        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.slider.setCurrentItem(currentPos+1);
            }
        });

        binding.letsStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[slideAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.indicators_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            binding.dotsLayout.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index){
        int childCount = binding.dotsLayout.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) binding.dotsLayout.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.indicators_active
                ));
            }
            else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.indicators_inactive
                ));
            }
        }
    }
}
