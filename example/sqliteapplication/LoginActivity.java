package com.example.sqliteapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class LoginActivity extends BaseActivity {

    protected final String TAG = "LoginActivity";
    private SharedPreferences.Editor editor;

    public static void actionStart(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        CheckBox rem = findViewById(R.id.checkbox_remember);
        boolean isRemember = pref.getBoolean("remember",false);
        Button login = findViewById(R.id.login);
        EditText editText_acc = findViewById(R.id.Account);
        EditText editText_pas = findViewById(R.id.Password);

        if(isRemember){
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            editText_acc.setText(account);
            editText_pas.setText(password);
            rem.setChecked(true);
            if(check(account,password)){
                MainActivity.actionStart(LoginActivity.this);
            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editText_acc.getText().toString();
                String password = editText_pas.getText().toString();
                if (check(account,password)){
                    Log.e(TAG, "login successful");
                    if(rem.isChecked()){
                        editor = pref.edit();
                        editor.putBoolean("remember",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                        editor.apply();
                    }
                    else {
                        if (editor != null) {
                            editor.clear();
                        }
                    }
                    MainActivity.actionStart(LoginActivity.this);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"默认的用户名和密码都是root",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean check(String account,String password){
        return account.equals("root") && password.equals("root");
    }
}