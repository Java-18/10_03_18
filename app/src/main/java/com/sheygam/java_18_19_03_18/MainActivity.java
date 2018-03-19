package com.sheygam.java_18_19_03_18;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.input_email) EditText inputEmail;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.reg_btn) Button regBtn;
    @BindView(R.id.login_btn) Button loginBtn;
    @BindView(R.id.progress_wrapper) FrameLayout progressWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.reg_btn)
    protected void registrartion(){
        new AuthTask().execute();
    }

    @OnClick(R.id.login_btn)
    protected void login(){
        //Todo start Task for login
    }

    class AuthTask extends AsyncTask<Void,Void,String>{
        private String email, password;
        private boolean isAuth = true;

        @Override
        protected void onPreExecute() {
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            progressWrapper.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String status = "Registration OK!";
            try {
                AuthToken token = HttpProvider.getInstance().registration(email,password);
                //Todo save token to Spref
                Log.d("MY_TAG", "doInBackground: " + token.getToken());
            }catch (IOException e){
                e.printStackTrace();
                status = "Connection error!Check your internet!";
                isAuth = false;
            }catch (Exception e) {
                e.printStackTrace();
                isAuth = false;
                status = e.getMessage();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            progressWrapper.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            if (isAuth){
                //Todo start next activity
            }
        }
    }
}
