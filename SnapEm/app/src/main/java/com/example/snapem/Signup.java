//package com.example.snapem;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Signup extends AppCompatActivity {
//
//    private GoogleSignInClient client;
//
//    Button btn_create;
//    ImageButton gsign;
//    EditText txt_username;
//    EditText txt_pass1;
//    EditText txt_pass2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        btn_create = (Button) findViewById(R.id.btn_create);
//        gsign = (ImageButton) findViewById(R.id.gsign);
//
//        txt_username = (EditText) findViewById(R.id.txt_username);
//        txt_pass1 = (EditText) findViewById(R.id.txt_pass);
//        txt_pass2 = (EditText) findViewById(R.id.txt_pass1);
//
//        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        client = GoogleSignIn.getClient(this, options);
//
//        gsign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                    Intent i = client.getSignInIntent();
//                    startActivityForResult(i, 1234);
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//        btn_create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                save_auth();
//            }
//        });
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1234) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//
//                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//                FirebaseAuth.getInstance().signInWithCredential(credential)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
//                                    startActivity(intent);
//
//                                } else {
//                                    Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            Intent intent = new Intent(Signup.this, SecondActivity.class);
//            startActivity(intent);
//        }
//    }
//
//    private void save_auth() {
//
//        new Thread() {
//            public void run() {
//                try {
//
//                    if (isEmailValid(txt_username.getText().toString())) {
//                        String result = "";
//                        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//
//                        URL url = new URL("https://iba.lk/snapem/app/save_auth.php");
//                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                        httpURLConnection.setRequestMethod("POST");
//                        httpURLConnection.setDoOutput(true);
//                        httpURLConnection.setDoInput(true);
//                        OutputStream outputStream = httpURLConnection.getOutputStream();
//                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                        String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(txt_username.getText().toString(), "UTF-8") + "&"
//                                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(txt_pass2.getText().toString(), "UTF-8") + "&"
//                                + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode("USER", "UTF-8") + "&"
//                                + URLEncoder.encode("did", "UTF-8") + "=" + URLEncoder.encode(android_id, "UTF-8");
//
//                        bufferedWriter.write(post_data);
//                        bufferedWriter.flush();
//                        bufferedWriter.close();
//                        outputStream.close();
//
//                        InputStream inputStream = httpURLConnection.getInputStream();
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//
//                        String line = "";
//                        while ((line = bufferedReader.readLine()) != null) {
//                            result += line;
//                        }
//
//                        bufferedReader.close();
//                        inputStream.close();
//                        httpURLConnection.disconnect();
//
//                        finish();
//                        Intent i = new Intent(Signup.this,Login.class);
//                        startActivity(i);
//
//                    }
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }.start();
//
//    }
//
//    public static boolean isEmailValid(String email) {
//        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
//}