package com.codemountain.shoppadi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codemountain.shoppadi.R;
import com.codemountain.shoppadi.helper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.codemountain.shoppadi.utils.Constants.LOGIN_URL;

public class LoginActivity extends AppCompatActivity {

    private EditText passwordEdit, emailEdit;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        passwordEdit = findViewById(R.id.password);
        emailEdit = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            String password, email;
            password = passwordEdit.getText().toString().trim();
            email = emailEdit.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else {
                loginCustomer(password, email);
            }
        });
    }

    private void loginCustomer(String password, String email) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                JSONArray jsonArray = jsonObject.getJSONArray("login");

                if (success.equals("1")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String customer_name = object.getString("customer_name").trim();
                        String customer_email = object.getString("customer_email").trim();

                        Toast.makeText(LoginActivity.this, "" + customer_email + "logged in successfully", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(LoginActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }, error -> {
            Toast.makeText(LoginActivity.this, "Error occurred " + error, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer_email", email);
                params.put("customer_password", password);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}