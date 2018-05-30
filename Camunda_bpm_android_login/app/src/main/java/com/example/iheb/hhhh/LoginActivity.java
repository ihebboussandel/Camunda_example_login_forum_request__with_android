package com.example.iheb.hhhh;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button sign_in_register;
    private RequestQueue requestQueue;
    private static final String URL = "http://isiforge.tn:8080/engine-rest/authorization";
    private StringRequest request;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        sign_in_register = (Button) findViewById(R.id.sign_in_register);

        requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jb1 = jsonArray.getJSONObject(1);
                            if(jb1.getString("id")!="NULL"){//********set on timeout


                            String id = jb1.getString("id");
                            String type = jb1.getString("type");
                            String permissions = jb1.getString("permissions");
                            String userId = jb1.getString("userId");
                            String groupId = jb1.getString("groupId");
                            String resourceType = jb1.getString("resourceType");
                            String resourceId = jb1.getString("resourceId");

                                /*Toast.makeText(getApplicationContext(),"ID: "+id+" Type:"+type+":"+permissions+"User ID:"+
                                        userId+" GroupID:"+groupId+" resource Type:"+resourceType+" Resource Id:"+resourceId,Toast.LENGTH_LONG).show();*/
                                 Toast.makeText(getApplicationContext(),"User ID:"+
                            id,Toast.LENGTH_LONG).show();
                            TextView tv = (TextView)findViewById(R.id.textView);
                            tv.setText("ID: "+id+" Type:"+type+":"+permissions+"User ID:"+
                                    userId+" GroupID:"+groupId+" resource Type:"+resourceType+" Resource Id:"+resourceId);
                                //start new activity
                                String value="Hello worldhhh";
                                Intent i = new Intent(LoginActivity.this, welcome.class);
                                i.putExtra("key",value);
                                startActivity(i);
                                //startActivity(new Intent(getApplicationContext(),welcome.class));
                            }else {
                               Toast.makeText(getApplicationContext(), "Error Autontification " +jb1.getString(""), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        String username =email.getText().toString();
                        String passw = password.getText().toString();
                        HashMap<String, String> params = new HashMap<String, String>();
                        String creds = String.format("%s:%s",username,passw);
                        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                        params.put("Authorization", auth);
                        return params;
                    }
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("userIdIn",email.getText().toString());

                        //hashMap.put("password",password.getText().toString());
                        return params;
                    }
                };

                requestQueue.add(request);

            }
        });
    }
}



