package com.nexmo.sid.verify3test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nexmo.sdk.NexmoClient;
import com.nexmo.sdk.core.client.ClientBuilderException;
import com.nexmo.sdk.verify.client.VerifyClient;
import com.nexmo.sdk.verify.event.UserObject;
import com.nexmo.sdk.verify.event.VerifyClientListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.nexmo.sid.verify3test.MESSAGE";
    private VerifyClient verifyClient;
    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        try {
            NexmoClient nexmoClient = new NexmoClient.NexmoClientBuilder()
                    .context(context)
                    .applicationId("APP_ID") //your App key
                    .sharedSecretKey("SHARED_SECRET") //your App secret
                    .build();
            verifyClient = new VerifyClient(nexmoClient);
            verifyClient.addVerifyListener(new VerifyClientListener() {
                @Override
                public void onVerifyInProgress(final VerifyClient verifyClient, UserObject user) {
                    Log.d(TAG, "onVerifyInProgress for number: " + user.getPhoneNumber());

                }

                @Override
                public void onUserVerified(final VerifyClient verifyClient, UserObject user) {
                     Log.d(TAG, "onUserVerified for number: " + user.getPhoneNumber());
                     checkPinCode();

                }

                @Override
                public void onError(final VerifyClient verifyClient, final com.nexmo.sdk.verify.event.VerifyError errorCode, UserObject user) {
                    Log.d(TAG, "onError: " + errorCode + " for number: " + user.getPhoneNumber());
                    Context context = getApplicationContext();
                    CharSequence text = "Error code is " + errorCode;
                    int duration = Toast.LENGTH_LONG;
                    Toast.makeText(context,text,duration).show();

                }

                @Override
                public void onException(final IOException exception) {
                }
            });

            verifyClient.getVerifiedUser("US", "DESTINATION_PHONE_NUMBER");


        } catch (ClientBuilderException e) {
            e.printStackTrace();
        }
    }
    public void checkPin(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String pin = editText.getText().toString();
        verifyClient.checkPinCode(pin);
    }

    public void checkPinCode() {
        Intent intent = new Intent(this, VerifyPin.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String pin = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, pin);
        startActivity(intent);
    }
}
