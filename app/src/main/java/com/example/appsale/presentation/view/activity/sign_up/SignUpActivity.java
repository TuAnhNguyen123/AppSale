package com.example.appsale.presentation.view.activity.sign_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsale.R;
import com.example.appsale.common.SpannedCommon;
import com.example.appsale.common.StringCommon;
import com.example.appsale.data.model.User;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.presentation.view.activity.sign_in.SignInActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    SignUpViewModel signUpViewModel;
    LinearLayout layoutLoading, btnSignUp;
    TextInputEditText
            txtInputEditEmail,
            txtInputEditName,
            txtInputEditPassword,
            txtInputEditPhone,
            txtInputEditLocation;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initial();
        setTextRegister();
        observerData();
        event();

    }

    private void setTextRegister() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Already have an account? ");
        builder.append(SpannedCommon.setClickColorLink("Login", this, () -> {
            finish();
            overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
        }));
        tvRegister.setText(builder);
        tvRegister.setHighlightColor(Color.TRANSPARENT);
        tvRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void observerData() {
        signUpViewModel.getResourceUser().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userAppResource) {
                switch (userAppResource.status) {
                    case SUCCESS:
                        Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
                        break;
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        Toast.makeText(SignUpActivity.this, userAppResource.message, Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void event() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtInputEditEmail.getText().toString();
                String name = txtInputEditName.getText().toString();
                String password = txtInputEditPassword.getText().toString();
                String phone = txtInputEditPhone.getText().toString();
                String location = txtInputEditLocation.getText().toString();

                signUpViewModel.signUp(name, location, email, phone, password);
            }
        });
    }

    private void initial() {
        layoutLoading = findViewById(R.id.layout_loading);
        txtInputEditEmail = findViewById(R.id.textEditEmail);
        txtInputEditName =findViewById(R.id.textEditName);
        txtInputEditPassword = findViewById(R.id.textEditPassword);
        txtInputEditPhone =findViewById(R.id.textEditPhone);
        txtInputEditLocation = findViewById(R.id.textEditLocation);
        btnSignUp = findViewById(R.id.sign_up);
        tvRegister = findViewById(R.id.text_view_login);

        signUpViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignUpViewModel(SignUpActivity.this);
            }
        }).get(SignUpViewModel.class);
    }
}
