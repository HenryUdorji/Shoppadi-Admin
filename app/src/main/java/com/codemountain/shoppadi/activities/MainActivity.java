package com.codemountain.shoppadi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codemountain.shoppadi.R;
import com.codemountain.shoppadi.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.codemountain.shoppadi.utils.Constants.REGISTER_URL;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEdit, passwordEdit, emailEdit, mobileEdit;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        emailEdit = findViewById(R.id.email);
        mobileEdit = findViewById(R.id.mobile);
        TextView loginText = findViewById(R.id.loginText);

        loginText.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(v -> {
            String username, password, email, mobile;
            username = usernameEdit.getText().toString().trim();
            password = passwordEdit.getText().toString().trim();
            email = emailEdit.getText().toString().trim();
            mobile = mobileEdit.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(mobile)) {
                Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else {
                registerCustomer(username, password, email, mobile);
            }
        });
    }

    private void registerCustomer(final String customer_name, final String customer_password, final String customer_email, final String customer_mobile) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");

                if (success.equals("true")) {
                    Toast.makeText(MainActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                }
                else if (success.equals("email already exist")){
                    Toast.makeText(MainActivity.this, "Email already exist", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show())
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer_name", customer_name);
                params.put("customer_email", customer_email);
                params.put("customer_password", customer_password);
                params.put("customer_mobile", customer_mobile);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}